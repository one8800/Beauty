package com.jianda.network;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LongHai on 17-4-7.
 */

public class HttpManager {
    // 后台服务器地址
//    private static final String HOST_API = "http://139.224.42.154:38080/";
    private static final String HOST_API = "http://learn.chaoxing.com/";

    // 车辆管理平台地址
    private static final String HOST_VEHICLE_API = "http://139.224.42.154:8789/ssposs/yitopssp/";

    private static final int DEFAULT_CONNECT_TIMEOUT = 6;
    private static final int DEFAULT_READ_TIMEOUT = 15;
    private static HttpManager instance;
    private OkHttpClient okHttpClient;
    private OkHttpClient okHttpCacheClient;

    private Gson gson;

    private Object domainService;
    private Object cacheDomainService;
    private Object vehicleService;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public void initialize(Interceptor httpParamsInterceptor, Interceptor httpCacheInterceptor, File cacheDir) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        SSLSocketFactory ssf = null;
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());
            ssf = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(httpParamsInterceptor)
                .sslSocketFactory(ssf)
                .build();

        File cacheFile = new File(cacheDir, "okhttp_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        okHttpCacheClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpParamsInterceptor)
                .addInterceptor(httpCacheInterceptor)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(httpCacheInterceptor)
                .cache(cache)
                .build();
    }

    private static Retrofit getRetrofit(String baseUrl) {
        if (getInstance().okHttpClient == null) {
            throw new IllegalArgumentException(HttpManager.class.getSimpleName() + " > getRetrofit not initialize");
        }
        return new Retrofit.Builder()
                .baseUrl(appendProtocol(baseUrl))
                .addConverterFactory(GsonConverterFactory.create(getInstance().gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getInstance().okHttpClient)
                .build();
    }

    private static Retrofit getCacheRetrofit(String baseUrl) {
        if (getInstance().okHttpCacheClient == null) {
            throw new IllegalArgumentException(HttpManager.class.getSimpleName() + " > getCacheRetrofit not initialize");
        }
        return new Retrofit.Builder()
                .baseUrl(appendProtocol(baseUrl))
                .addConverterFactory(GsonConverterFactory.create(getInstance().gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getInstance().okHttpCacheClient)
                .build();
    }

    @SuppressWarnings("unchecked")
    public synchronized static <T> T getDomainService(Class<T> service) {
        if (getInstance().domainService == null) {
            getInstance().domainService = getRetrofit(HOST_API).create(service);
        }
        return (T) getInstance().domainService;
    }

    @SuppressWarnings("unchecked")
    public synchronized static <T> T getCacheDomainService(Class<T> service) {
        if (getInstance().cacheDomainService == null) {
            getInstance().cacheDomainService = getCacheRetrofit(HOST_API).create(service);
        }
        return (T) getInstance().cacheDomainService;
    }

    @SuppressWarnings("unchecked")
    public synchronized static <T> T getVehicleService(Class<T> service) {
        if (getInstance().vehicleService == null) {
            getInstance().vehicleService = getRetrofit(HOST_VEHICLE_API).create(service);
        }
        return (T) getInstance().vehicleService;
    }

    /**
     * @param context    Application Context
     * @param url        Url Address
     * @param requestTag Tag值，取消请求时用到
     * @param callback   异步回调
     */
    public static void asyncCacheRequest(Context context, String url, String requestTag, Callback callback) {
        File cacheFile = new File(context.getApplicationContext().getCacheDir(), "okhttp_disk_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        OkHttpClient cacheClient = getInstance().okHttpClient.newBuilder()
                .cache(cache)
                .addInterceptor(new HttpCacheInterceptor(context.getApplicationContext()))
                .addNetworkInterceptor(new HttpCacheInterceptor(context.getApplicationContext()))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .tag(requestTag)
                .build();
        Call call = cacheClient.newCall(request);
        call.enqueue(callback);
    }

    public static String syncGetRequest(String url) throws IOException {
        String resultString = null;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().okHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            resultString = response.body().string();
        }
        return resultString;
    }

    public static void asyncGetRequest(String url, String requestTag, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .tag(requestTag)
                .build();
        Call call = getInstance().okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void asyncPostRequest(String url, String params, String requestTag, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(requestTag)
                .build();
        Call call = getInstance().okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void asyncFormPostRequest(String url, Map<String, Object> params, String requestTag, Callback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .tag(requestTag)
                .build();
        Call call = getInstance().okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void asyncMultipartFormRequest(String url, String params, List<String> filePathList, String requestTag, Callback callback) {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        multipartBuilder.addFormDataPart("filedata", params);
        /**
         * multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
         * RequestBody.create(null, param.value));
         */

        FileNameMap fileNameMap = URLConnection.getFileNameMap();

        if (filePathList != null && filePathList.size() > 0) {
            RequestBody fileBody;
            for (int i = 0; i < filePathList.size(); i++) {
                File file = new File(filePathList.get(i));
                String contentTypeFile = fileNameMap.getContentTypeFor(filePathList.get(i));
                if (contentTypeFile == null) {
                    contentTypeFile = "application/octet-stream";
                }
                fileBody = RequestBody.create(MediaType.parse(contentTypeFile), file);
                multipartBuilder.addFormDataPart("evaluateReq", file.getName(), fileBody);

                /**
                 * // 根据文件名设置contentType
                 * multipartBuilder.addPart(Headers.of("Content-Disposition",
                 *                "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + file.getName() + "\""),
                 *       fileBody);
                 */
            }
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(requestTag)
                .build();
        Call call = getInstance().okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(callback);

    }

    private static String appendProtocol(String host) {
        Uri uri = Uri.parse(host);
        String url = uri.toString();
        if (!uri.toString().startsWith("http://") && !uri.toString().startsWith("https://")) {
            url = "http://" + host;
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }

    public static void cancelTag(Object tag) {
        for (Call call : getInstance().okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getInstance().okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static void cancelAll() {
        for (Call call : getInstance().okHttpClient.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : getInstance().okHttpClient.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
