package com.jianda.util;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class SystemSetting {
    //-------------------------电话----------------------------
    public static class Phone {

    }

    //-------------------------蓝牙----------------------------
    public static class Bluetooth {

        public static boolean isOpen() {
            BluetoothAdapter ba = getBluetoothAdapter();
            if (ba != null) {
                return ba.isEnabled();
            }
            return false;
        }
        //是否支持蓝牙
        public static boolean isSupported() {
            return getBluetoothAdapter() != null ? true : false;
        }

        private static BluetoothAdapter getBluetoothAdapter() {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            return bluetoothAdapter;
        }
    }

    //-------------------------亮度--------------------------
    public static class Brightness {
        //设置系统亮度
        public static void setBrightness(AppCompatActivity activity, int brightValue) {
            Settings.System.putInt(getContentResolver(activity), Settings.System.SCREEN_BRIGHTNESS, brightValue);
        }

        //获取系统亮度
        public static int getBrightness(AppCompatActivity activity) {
            int brightValue = 0;
            try {
                brightValue = Settings.System.getInt(getContentResolver(activity), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return brightValue;
        }

        //设置系统亮度模式
        public static void setBrightnessMode(AppCompatActivity activity, int brightMode) {
            Settings.System.putInt(getContentResolver(activity), Settings.System.SCREEN_BRIGHTNESS_MODE, brightMode);
        }

        private static ContentResolver getContentResolver(AppCompatActivity activity) {
            ContentResolver cr = activity.getContentResolver();
            return cr;
        }
    }

    //-------------------------音量--------------------------
    public static class Voice {
        //获取当前闹钟音量
        public static int getVoiceAlarm(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamVolume(AudioManager.STREAM_ALARM);
        }

        //获取闹钟最大音量
        public static int getVoiceMaxAlarm(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        }

        //设置当前音乐音量
        public static void setVoiceMusic(Context context, int voiceMusic) {
            AudioManager am = getAudioManager(context);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, voiceMusic, 0);
        }

        //获取当前音乐音量
        public static int getVoiceMusic(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamVolume(AudioManager.STREAM_MUSIC);
        }

        //获取音量最大音量
        public static int getVoiceMaxMusic(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }

        //获取当前铃声音量
        public static int getVoiceRing(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamVolume(AudioManager.STREAM_RING);
        }

        //获取铃声最大音量
        public static int getVoiceMaxRing(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamMaxVolume(AudioManager.STREAM_RING);
        }

        //获取当前系统音量
        public static int getVoiceSystem(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamVolume(AudioManager.STREAM_SYSTEM);
        }

        //获取系统最大音量
        public static int getVoiceMaxSystem(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        }

        //获取当前通话音量
        public static int getVoiceCall(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        }

        //获取通话最大音量
        public static int getVoiceMaxCall(Context context) {
            AudioManager am = getAudioManager(context);
            return am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        }

        //获取音频管理者
        private static AudioManager getAudioManager(Context context) {
            return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
    }

    //-------------------------GPS---------------------------------------
    public static class GPS {
        //设备是否支持GPS
        public static boolean isSupported(Context context) {
            LocationManager lm = getLocationManager(context);
            if (lm == null) {
                return false;
            }
            final List<String> providers = lm.getAllProviders();
            if (providers == null) {
                return false;
            }
            return providers.contains(LocationManager.GPS_PROVIDER);
        }

        //GPS定位是否打开
        public static boolean isOpenGPS(final Context context) {
            LocationManager lm = getLocationManager(context);
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean providerEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (providerEnabled) {
                return true;
            }
            return false;
        }

        //网络定位是否打开
        public static boolean isOpenAGPS(final Context context) {
            LocationManager lm = getLocationManager(context);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean providerEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (providerEnabled) {
                return true;
            }
            return false;
        }

        private static LocationManager getLocationManager(Context context) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm;
        }
    }
}
