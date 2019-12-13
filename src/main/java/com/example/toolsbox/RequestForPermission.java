package com.example.toolsbox;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 使用方法
 * 在manifests中申请权限,
 * 调用RequestForPermission.RequestPermission(this,RequestForPermission.PHONE,RequestForPermission.CAMERA);
 * 重写Activity回调方法,并调用本方法中的onRequestPermissionResult();
 */
public class RequestForPermission {
    private final static String[] phone = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG
            , Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP};
    private final static String[] camera = new String[]{Manifest.permission.CAMERA};
    private final static String[] locaton = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final static String[] microphone = new String[]{Manifest.permission.RECORD_AUDIO};
    private final static String[] contacts = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    private final static String[] sensors = new String[]{Manifest.permission.BODY_SENSORS};
    private final static String[] sms = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS};
    private final static String[] storage = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final static String[] calendar = new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};


    public final static int PHONE = 1;
    public final static int CAMERA = 2;
    public final static int LOCATON = 3;
    public final static int MICROPHONE = 4;
    public final static int CONTACTS = 5;
    public final static int SENSORS = 6;
    public final static int SMS = 7;
    public final static int STORAGE = 8;
    public final static int CALENDAR = 9;

    private final static HashMap<Integer, String[]> map = new HashMap<Integer, String[]>() {
        {
            put(PHONE, phone);
            put(CAMERA, camera);
            put(LOCATON, locaton);
            put(MICROPHONE, microphone);
            put(CONTACTS, contacts);
            put(SENSORS, sensors);
            put(SMS, sms);
            put(STORAGE, storage);
            put(CALENDAR, calendar);
        }
    };


    private static ArrayList<Integer> permissionLists = new ArrayList<Integer>();

    public static void RequestPermission(Context context, int... permissions) {

        if (Build.VERSION.SDK_INT >= 23) { //如果系统版本大于6.0申请权限,否则什么也不做
            for (int permission : permissions) {
                switch (permission) {
                    case PHONE:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(PHONE);
                        }
                        break;
                    case CAMERA:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(CAMERA);
                        }
                        break;
                    case LOCATON:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(LOCATON);
                        }
                        break;
                    case MICROPHONE:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(MICROPHONE);
                        }
                        break;
                    case CONTACTS:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(CONTACTS);
                        }
                        break;
                    case SENSORS:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(SENSORS);
                        }
                        break;
                    case SMS:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(SMS);
                        }
                        break;
                    case STORAGE:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(STORAGE);
                        }
                        break;
                    case CALENDAR:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                            permissionLists.add(CALENDAR);
                        }
                        break;
                }
            }
        } else {
            return;
        }

        if (permissionLists.size() == 0) {
            return;
        } else {
            for (int permissionGroup : permissionLists) {
                ActivityCompat.requestPermissions((Activity) context, map.get(permissionGroup), permissionGroup);
            }
        }
    }

    /**@param context Activity的实例,用于Activity的中相应方法的回调*/
    public static void onRequestPermissionResult(Context context, int requestcode, String[] permssions, int[] grantResults) {

        if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (map.get(requestcode).length != 0) {
                Toast.makeText(context, "你拒绝了权限" + map.get(requestcode)[0], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "权限未授权", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
