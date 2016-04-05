package qiu.statusbarcompat.compat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Compat with content fitsSystemWindows
 * Created by qiu on 3/20/16.
 */
public class StatusBarCompat2 {

    public static final String TAG = StatusBarCompat2.class.getName();

    public static final int COLOR_DEFAULT_PINK = Color.parseColor("#FFEF4968");
    public static final int COLOR_DEFAULT_WHITE = Color.parseColor("#FFFFFFFF");

    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //After LOLLIPOP not translucent status bar
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //Then call setStatusBarColor.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusColor);

                if (mContentView.getChildAt(0) != null) {
                    ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), true);
                }
            } else {
                int statusBarHeight = getStatusBarHeight(activity);

                //Before LOLLIPOP create a fake status bar View.
                View mTopView = mContentView.getChildAt(0);
                if (mTopView != null && mTopView.getLayoutParams() != null && mTopView.getLayoutParams().height == statusBarHeight) {
                    //if fake status bar view exist, we can setBackgroundColor and return.
                    mTopView.setBackgroundColor(statusColor);
                    return;
                }
                //now topView is layout content
                if (mTopView != null) {
                    ViewCompat.setFitsSystemWindows(mTopView, true);
                }

                mTopView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                mTopView.setBackgroundColor(statusColor);
                mContentView.addView(mTopView, 0, lp);
            }
        }
    }

    public static void translucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //After LOLLIPOP just set LayoutParams.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (mContentView.getChildAt(0) != null) {
                    ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
                }
            } else {
                //如果添加了StatusBar, 需要移除这个View
                View statusBarView = mContentView.getChildAt(0);
                if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == getStatusBarHeight(activity)) {
                    mContentView.removeView(statusBarView);
                }
                if (mContentView.getChildAt(0) != null) {
                    ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
                }
            }
        }
    }

    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity, COLOR_DEFAULT_PINK);
    }

    //获取状态栏的高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
