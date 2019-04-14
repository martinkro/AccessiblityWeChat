package info.loveai.acessibility.utils;

import android.view.accessibility.AccessibilityEvent;

public final class AccessibilityEventHelper {

    public static String getEventString(AccessibilityEvent event){

        String result = null;
        int eventType = event.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                result = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                result = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                result = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                result = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                result = "TYPE_VIEW_SCROLLED";
                break;
            default:
                result = String.format("0x%08x",eventType);
                break;
        }
        return result;

    }
}
