package info.loveai.acessibility.wechat;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.Arrays;
import java.util.List;

import info.loveai.acessibility.utils.AccessibilityEventHelper;
import info.loveai.acessibility.utils.AccessibilityNodeInfoHelper;
import info.loveai.acessibility.utils.AccessibilityUtils;

public class AccessibilityWeChat extends AccessibilityService {
    private static final String TAG = "WeChatHelper";
    boolean mIsOpenRP = false;
    boolean mIsOpenDetail = false;

    private Handler mHandler = new Handler();

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        return super.getRootInActiveWindow();
    }

    private List<String> ids = Arrays.asList("bjj", "bi3", "brt");
    public void onAccessibilityEvent(AccessibilityEvent event){
        int eventType = event.getEventType();

        Log.d(TAG, "[onAccessibilityEvent] event:" + event.toString());
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:

                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:

                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                handleWindowContentChanged(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                handleWindowStateChanged(event);
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:

                handleNotificationStateChanged(event);
                break;
            default:
                break;
        }


    }

    private void handleNotificationStateChanged(AccessibilityEvent event){
        boolean isRedPackage = false;
        List<CharSequence> texts = event.getText();
        // Log.d(TAG,"Notification text num:" + texts.size());
        for (CharSequence text:texts){
            Log.d(TAG,"Notification text:" + text);
            if (text != null && text.toString().contains("[微信红包]")){
                isRedPackage = true;
            }
        }

        //android.os.Build.VERSION_CODES.LOLLIPOP;
        //android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

        if (!isRedPackage) return;

        mIsOpenRP = false;
        Notification notification = (Notification)event.getParcelableData();
        if (notification != null && notification.contentIntent != null){
            try{
                notification.contentIntent.send();
            }catch (PendingIntent.CanceledException e){
                e.printStackTrace();
            }
        }
    }

    private void handleWindowStateChanged(AccessibilityEvent event){
        AccessibilityNodeInfo ni = getRootInActiveWindow();
        if (ni == null) return;
        // CharSequence clsName = ni.getClassName();
        String clsName = event.getClassName().toString();
        CharSequence text = ni.getText();
        Log.d(TAG, clsName + "|" + text);
        if (clsName == null) return;
        if (clsName.equals("com.tencent.mm.ui.LauncherUI")){
            AccessibilityNodeInfo root = getRootInActiveWindow();
            findRedPackage(root);
        }
        else if (clsName.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyNotHookReceiveUI")){
//            try{
//                for(int i = 0; i < 1; i++){
//                    AccessibilityNodeInfo root = getRootInActiveWindow();
//                    if (root != null && !mIsOpenDetail){
//                        openRedPacket(root);
//                    }
//
//                    if (mIsOpenDetail){
//                        break;
//                    }
//                    Thread.sleep(100);
//                }
//
//            }catch (InterruptedException e){
//
//            }

//            List<AccessibilityWindowInfo> nodeInfos = getWindows();
//            for (AccessibilityWindowInfo window : nodeInfos) {
//                AccessibilityNodeInfo nodeInfo = window.getRoot();
//                if (nodeInfo == null) {
//                    break;
//                }
//                if (!mIsOpenDetail){
//                    openRedPacket(nodeInfo);
//                }
//            }

//            mIsOpenDetail = false;
//            AccessibilityNodeInfo root = getRootInActiveWindow();
//            openRedPacket(root);

            if (!mIsOpenDetail){
                mHandler.postDelayed(mRunnableOpenRedPacket,100);
            }

//            if (!mIsOpenDetail){
//                performGlobalAction(GLOBAL_ACTION_HOME);
//                performGlobalAction(GLOBAL_ACTION_RECENTS);
//            }
//            int count = 5;
//            while(count-- != 0){
//                if (!mIsOpenDetail){
//
//                }
//                else{
//                    break;
//                }
//
//            }


        }
        else if (clsName.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")){
            if (mIsOpenDetail){
                mIsOpenDetail = false;
                AccessibilityUtils.back2Home(this);
            }
        }

        // AccessibilityNodeInfoHelper.recursiveTraversal(ni);

        //ni.findAccessibilityNodeInfosByViewId("xxx");
    }

    private void findRedPackage(AccessibilityNodeInfo root){
        if (mIsOpenRP) return;
        if (root != null){
            for(int i = root.getChildCount() - 1; i >= 0; i--){
                AccessibilityNodeInfo node = root.getChild(i);
                if (node == null) continue;
                CharSequence text = node.getText();
                if (text != null && text.toString().contains("微信红包")){
                    AccessibilityNodeInfo parent = node.getParent();
                    while(parent != null){
                        if (parent.isClickable()){
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mIsOpenRP = true;
                            return;
                        }
                        parent = parent.getParent();
                    }
                }
                findRedPackage(node);
            }
        }
    }

    private Runnable mRunnableOpenRedPacket = new Runnable() {
        @Override
        public void run() {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            openRedPacket(root);
        }
    };

    private void openRedPacket(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) return;

        //List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText("開");
        //if

        //AccessibilityNodeInfoHelper.recursiveTraversal(rootNode);
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            AccessibilityNodeInfo node = rootNode.getChild(i);
            if (node != null){
                Log.d(TAG,"class name:" + node.getClassName());
            }

//            if (node.isClickable()){
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                Log.d(TAG,"click class name:" + node.getClassName());
//            }
            if ("android.widget.Button".equals(node.getClassName())) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                mIsOpenDetail = true;
                Log.d(TAG,"found");
                return;
            }
//            if ("android.widget.ProgressBar".equals(node.getClassName())) {
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//                mIsOpenDetail = true;
//            }
            openRedPacket(node);
        }
    }



    private void handleWindowContentChanged(AccessibilityEvent event){
        int contentChangeType = event.getContentChangeTypes();
        switch (contentChangeType){
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE:
                break;
            default:
                break;
        }
        Log.d(TAG,"content change type:" + contentChangeType);
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;
        if (root != null){
            CharSequence pkgName = root.getPackageName();
            CharSequence clsName = root.getClassName();
            CharSequence text = root.getText();
            //Log.d(TAG,text + "|" + clsName + "|" + pkgName);
        }

        //AccessibilityNodeInfoHelper.recursiveTraversal(root);

        CharSequence clsName = event.getClassName();
        if (clsName != null)
        {
            Log.d(TAG, "content changed:" + clsName);
        }

//        if (clsName.equals("android.widget.LinearLayout")){
//            if (mIsOpenRP){
//                for (int i = 0; i < root.getChildCount(); i++) {
//                    AccessibilityNodeInfo node = root.getChild(i);
//                    if (node != null) {
//                        Log.d(TAG, "class name:" + node.getClassName());
//                    }
//                    if ("android.widget.Button".equals(node.getClassName())) {
//                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//                        mIsOpenDetail = true;
//                    }
//                }
//            }
//        }
    }

    public void onInterrupt(){
        if (mIsOpenRP){

        }
    }
}
