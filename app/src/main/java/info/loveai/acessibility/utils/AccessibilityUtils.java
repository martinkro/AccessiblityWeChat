package info.loveai.acessibility.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public final class AccessibilityUtils {
    /**
     * 返回桌面
     */
    public static void back2Home(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }

    /**
     * 判断是否处于亮屏状态
     *
     * @return true-亮屏，false-暗屏
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.e("isScreenOn", isScreenOn + "");
        return isScreenOn;
    }

    /**
     * 解锁屏幕
     */
    public static void wakeUpScreen(Context context) {

//        //获取电源管理器对象
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        //后面的参数|表示同时传入两个值，最后的是调试用的Tag
//        PowerManager.WakeLock wakeLock =
//                pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "bright");
//
//        //点亮屏幕
//        wakeLock.acquire();
//
//        //得到键盘锁管理器
//        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("unlock");
//
//        //解锁
//        keyguardLock.disableKeyguard();
    }

}
