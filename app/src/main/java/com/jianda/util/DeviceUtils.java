package com.jianda.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jianda.beauty.R;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

/**
 * Created by LongHai on 16-9-12.
 */
public class DeviceUtils {

  /*  public static void checkBluetoothIsOpen(Context context , ImageView imageView) {
        if (DeviceUtils.Bluetooth.isSupported()) {
            if (DeviceUtils.Bluetooth.isOpen()) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_bluetooth));
                return;
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_bluetooth_no));
            return;
        }
        Utils.showToast(context, "当前设备不支持蓝牙");
    }

    public static void checkGPSIsOpen(Context context, ImageView imageView) {
        if (DeviceUtils.Gps.hasGPSDevice(context)) {
            if (DeviceUtils.Gps.isOPen(context)) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_gps));
                return;
            }
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_gps_no));
            return;
        }
        imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.iv_gps_no));
        Utils.showToast(context, "当前设备不支持GPS");
    }

    public static void checkSimIsExist(Context context, IVoice.ITTSListener ittsListener){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    VoiceSDK.getInstance().startTTS("未检测到SIM卡", ittsListener);
                    break;
            }
        }
    }

    public static void checkSDCardIsExist(IVoice.ITTSListener ittsListener){
        List<String> storageList = SDCardHelper.getExtSDCardPathList();
        if (storageList == null || storageList.size() < 2) {
            VoiceSDK.getInstance().startTTS("未检测到TF卡", ittsListener);
        }
    }

    public static void setBrightness (android.view.Window window, int bright) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = bright * (1f / 255.0f);
        window.setAttributes(lp);
        SPUtils.putValue(Constants.SP.BRIGHT_VALUE, bright);
    }

    public static void setVolume(AudioManager audioManager, int volume) {
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
        SPUtils.putValue(Constants.SP.VOLUME_VALUE, volume);
    }*/

    public static class Window {
        /**
         * 获取状态栏的高
         *
         * @param context
         * @return
         */
        public static int getStatusBarHeight(Context context) {
            int statusBarHeight = -1;
            //获取status_bar_height资源的ID
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
            return statusBarHeight;
        }

        /**
         * 设置强制满屏显示
         *
         * @param window
         */
        public static void setScreenFull(android.view.Window window) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            dimStatusBar(window, true);
        }

        /**
         * 设置沉浸式满屏显示
         * @param window
         */
        public static void setScreenFullImmerse(android.view.Window window) {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = window.getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }

        /**
         * 设置隐藏导航栏
         *
         * @param window
         */
        public static void setHideNaivBar(android.view.Window window) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
            dimStatusBar(window, true);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public static void dimStatusBar(android.view.Window window, boolean dim) {
            if (!DeviceUtils.isHoneycombOrLater)
                return;
            int visibility = 0;
            int navbar = 0;

            if (DeviceUtils.isJellyBeanOrLater) {
                visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                navbar = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
            if (dim) {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if (DeviceUtils.isICSOrLater)
                    navbar |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                else
                    visibility |= View.STATUS_BAR_HIDDEN;
                if (!DeviceUtils.hasCombBar()) {
                    navbar |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    if (DeviceUtils.isKitKatOrLater)
                        visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE;
                    if (DeviceUtils.isJellyBeanOrLater)
                        visibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
                }
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if (DeviceUtils.isICSOrLater)
                    visibility |= View.SYSTEM_UI_FLAG_VISIBLE;
                else
                    visibility |= View.STATUS_BAR_VISIBLE;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }

            if (DeviceUtils.hasNavBar())
                visibility |= navbar;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    private static TelephonyManager mTelephonyManager;

    public static TelephonyManager getTelephonyManager(Context context) {
        // 获取telephony系统服务，用于取得SIM卡和网络相关信息
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        return mTelephonyManager;
    }

    /**
     * 获取手机IMEI号
     */
    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return "";
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();

        return imei;
    }


    public static String getModelName() {
        return Build.PRODUCT.replaceAll(" ", "_").toLowerCase();
    }

    public static String getSDCardCachePath() {
        return new File(Environment.getExternalStorageDirectory(), "/Daisy/").getAbsolutePath();
    }

    public static Point getDisplayPixelSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static String getScreenInch(Context context) {
        Point point = getDisplayPixelSize(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double x1 = Math.pow(point.x / dm.xdpi, 2);
        double y1 = Math.pow(point.y / dm.ydpi, 2);
        BigDecimal bg = new BigDecimal(Math.sqrt(x1 + y1));
        double inch = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(inch);
    }

    public static int getDisplayPixelWidth(Context context) {
        Point size = getDisplayPixelSize(context);
        return (size.x);
    }

    public static int getDisplayPixelHeight(Context context) {
        Point size = getDisplayPixelSize(context);
        return (size.y);
    }

    public static int getDensity(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;
    }

    public static int dpToPx(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return (int) px;
    }

    public static String getLocalMacAddress(Context context) {
        String strMacAddr = null;
        try {
            InetAddress ip = getLocalInetAddress();

            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append('-');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            strMacAddr = "";
        }

        return strMacAddr;
    }

    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String getLocalHardwareAddress() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
//            if (networkInterface == null) {
//                networkInterface = NetworkInterface.getByName("wlan0");
//            }
            if (networkInterface == null) {
                return "null";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
        return macAddress;
    }

    public static String getLocalwlanAddress() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            //   networkInterface = NetworkInterface.getByName("eth1");
            //   if (networkInterface == null) {
            networkInterface = NetworkInterface.getByName("wlan0");
            //   }
            if (networkInterface == null) {
                return "null";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
        return macAddress;
    }

    final static boolean hasNavBar;
    public static final boolean isMarshMallowOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    public static final boolean isLolliPopOrLater = isMarshMallowOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    public static final boolean isKitKatOrLater = isLolliPopOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    public static final boolean isJellyBeanMR2OrLater = isKitKatOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    public static final boolean isJellyBeanMR1OrLater = isJellyBeanMR2OrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    public static final boolean isJellyBeanOrLater = isJellyBeanMR1OrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    public static final boolean isICSOrLater = isJellyBeanOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    public static final boolean isHoneycombMr2OrLater = isICSOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    public static final boolean isHoneycombMr1OrLater = isHoneycombMr2OrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    public static final boolean isHoneycombOrLater = isHoneycombMr1OrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    static {
        HashSet<String> devicesWithoutNavBar = new HashSet<>();
        devicesWithoutNavBar.add("HTC One V");
        devicesWithoutNavBar.add("HTC One S");
        devicesWithoutNavBar.add("HTC One X");
        devicesWithoutNavBar.add("HTC One XL");
        hasNavBar = isICSOrLater
                && !devicesWithoutNavBar.contains(Build.MODEL);
    }


    public static boolean hasNavBar() {
        return hasNavBar;
    }

    /**
     * hasCombBar测试如果设备有组合杆:只适用于蜂窝或ICS的平板
     */
    public static boolean hasCombBar() {
        return (((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) &&
                (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN)));
    }

}