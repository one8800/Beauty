package com.jianda.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.jianda.beauty.constant.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh on 2017/10/28.
 */

public class FileUtils {

    public static List<String> getWakeupWords(Context context, int rawId) {
        List<String> words = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(rawId);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    public static String getFileByUrl(String httpUrl) {
        try {
            URL url = new URL(httpUrl);
            String file = url.getFile();
            File localFile = new File(file);
            String fileName = localFile.getName();
            if (fileName.contains("?")) {
                int index = fileName.indexOf("?");
                fileName = fileName.substring(0, index);
            }
            return fileName;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static File getFileByPath(String filePath) {
        return StringUtils.isEmptyText(filePath) ? null : new File(filePath);
    }

    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 复制文件（非目录）
     *
     * @param srcFile  要复制的源文件
     * @param destFile 复制到的目标文件
     * @return
     */
    private static boolean copyFile(String srcFile, String destFile) {
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 把文件拷贝到某一目录下
     *
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean copyFileToDir(String srcFile, String destDir, String newFileName) {
        File fileDir = new File(destDir);
        if (!fileDir.exists()) {
            if (!fileDir.getParentFile().exists()) {
                fileDir.mkdirs();
            } else {
                fileDir.mkdir();
            }

        }
        String destFile = destDir + "/" + new File(newFileName).getName();
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 移动文件目录到某一路径下
     *
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean moveFileToDir(String srcFile, String destDir, String newFileName) {
        //复制后删除原目录
        if (copyFileToDir(srcFile, destDir, newFileName)) {
            deleteFile(new File(srcFile));
            return true;
        }
        return false;
    }

    /**
     * 删除文件（包括目录）
     *
     * @param delFile
     */
    public static void deleteFile(File delFile) {
        //如果是目录递归删除
        if (delFile.isDirectory()) {
            File[] files = delFile.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        } else {
            delFile.delete();
        }
        //如果不执行下面这句，目录下所有文件都删除了，但是还剩下子目录空文件夹
        delFile.delete();
    }

    public static boolean close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 删除更新的config文件
     */
    public static void deleteConfig(){
        Log.e("zengkang","删除config");
        File file = new File(SDCardHelper.getTFCardBaseDir()+"/"+ Constants.Config.IME_UPDATE_DISK+"/"+"config.txt");
        if (file.exists()){
            FileUtils.deleteFile(file);
        }
    }

    /**
     * 群组帮助
     * @param e
     * @return
     */
    public static int filterException(Exception e) {
        int code = 200;
        if (e != null) {
            e.printStackTrace();
            try {
                JSONObject jsonObject = new JSONObject(e.getMessage());
                code = jsonObject.getInt("code");
                return code;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return code;
    }
}
