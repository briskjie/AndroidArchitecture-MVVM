package com.example.xiangxuchen.architecture_mvvm_opt.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 作者：NeilLee on 2017/12/14 14:06.
 * <p>
 * 识别符来源：
 * 1，MAC地址；
 * 2，IMEI（imei）：
 * 3，SIM序列号（sim sn）：
 * 4，ANDROID_ID：设备首次启动时产生，WIPE重新生成；
 * 5，SN
 * 6，UUID：应用安装时产生
 */
public class DeviceUtils {
    private static final String IDs = "DeviceUtils";
    private static final String SP_KEY_DEVICE_ID = "sp_key_device_id";

    private static final String DEFAULT_MAC = "02:00:00:00:00:00";
    private static final String DEFAULT_ANDROID_ID = "9774d56d682e549c";

    private static Context sContext;

    public static void init(Context context){
        sContext=context;
    }


    public static String getDeviceId() {
        SharedPreferences sp = sContext.getSharedPreferences(IDs, Context.MODE_PRIVATE);
        String deviceId = sp.getString(SP_KEY_DEVICE_ID, "");
        LogUtils.i(deviceId);
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        String androidId = getAndroidID();
        String serial = getSerial();
        String mac = getMAC();

        LogUtils.i("androidId:"+androidId);
        LogUtils.i("serial:"+serial);
        LogUtils.i("mac:"+mac);

        //为空或者错误的id
        if (DEFAULT_ANDROID_ID.equals(androidId)) {
            androidId = UUID.randomUUID().toString();
        }
        if (TextUtils.isEmpty(serial)) {
            serial = UUID.randomUUID().toString();
        }

        if (DEFAULT_MAC.equals(mac)) {
            mac = UUID.randomUUID().toString();
        }
        String sand = androidId + serial + mac;
        deviceId = UUID.nameUUIDFromBytes(sand.getBytes()).toString();
        sp.edit().putString(SP_KEY_DEVICE_ID, deviceId).apply();
        return deviceId;
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) sContext.
                    getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    // 装有SIM卡的设备可获得，对于CDMA设备，返回的是一个空值！
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getSimSN() {
        String sn = "";
        try {
            TelephonyManager tm = (TelephonyManager) sContext.
                    getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                sn = tm.getSimSerialNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sn;
    }

    @SuppressLint("HardwareIds")
    public static String getMAC() {
        String wifiMAC = DEFAULT_MAC;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            WifiManager wifi = (WifiManager) sContext.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info;
            if (wifi != null && (info = wifi.getConnectionInfo()) != null) {
                wifiMAC = info.getMacAddress();
            }
        } else {
            wifiMAC = getNewMac();
        }
        return wifiMAC;
    }

    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    @SuppressLint("HardwareIds")
    public static String getSerial() {
        return android.os.Build.SERIAL;
    }


    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        String androidId = Settings.Secure.getString(
                sContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            androidId = DEFAULT_ANDROID_ID;
        }
        return androidId;
    }
}