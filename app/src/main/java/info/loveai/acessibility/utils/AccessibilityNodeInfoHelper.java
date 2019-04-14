package info.loveai.acessibility.utils;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

public final class AccessibilityNodeInfoHelper {
    private static final String TAG = "WeChatHelper";

    public static void recursiveTraversal(AccessibilityNodeInfo ni){
        if (ni == null) return;
        dump(ni);
        for (int i = 0; i < ni.getChildCount(); i++){
            recursiveTraversal(ni.getChild(i));
        }
    }

    public static void dump(AccessibilityNodeInfo ni){

//        Log.d(TAG,"getClassName:" + ni.getClassName());
//        Log.d(TAG,"getChildCount:" + ni.getChildCount());
//        Log.d(TAG,"getWindowId:" + ni.getWindowId());
//        //Log.d(TAG,"getContentDescription:" + ni.getContentDescription());
//        Log.d(TAG,"getPackageName:" + ni.getPackageName());
//        Log.d(TAG,"getText:" + ni.getText());

        if (ni == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("[" + ni.getClassName() + "]");
        sb.append(ni.getWindowId());
        sb.append("|" + ni.getChildCount());
        sb.append("|" + ni.getContentDescription());
        sb.append("|" + ni.getText());
        sb.append("|" + ni.getViewIdResourceName());
        sb.append("|" + ni.getPackageName());

        Log.d(TAG,sb.toString());
    }
}
