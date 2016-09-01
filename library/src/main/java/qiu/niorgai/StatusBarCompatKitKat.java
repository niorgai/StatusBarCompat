package qiu.niorgai;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * After kitkat add fake status bar
 * Created by qiu on 8/27/16.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class StatusBarCompatKitKat {

    private static final int TYPE_SET_STATUS_BAR = 0;
    private static final int TYPE_SET_STATUS_BAR_WITH_COLLAPSING = 1;
    private static final int TYPE_TRANSLUCENT_STATUS_BAR = 2;

    //Get status bar height
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Integer) {
            View mStatusBarView = mDecorView.getChildAt(0);
            if (mStatusBarView != null) {
                mStatusBarView.setBackgroundColor(statusColor);
            }
            if (mContentChild != null) {
                ViewCompat.setFitsSystemWindows(mContentChild, false);
            }
            int type = (int) mDecorView.getTag();
            if (type != TYPE_SET_STATUS_BAR) {
                addMarginTopToContentChild(mContentChild, getStatusBarHeight(activity));
                mDecorView.setTag(TYPE_SET_STATUS_BAR);
            }
        } else {
            //第一次添加View并加上margin
            int statusBarHeight = getStatusBarHeight(activity);
            if (mContentChild != null) {
                ViewCompat.setFitsSystemWindows(mContentChild, false);
                addMarginTopToContentChild(mContentChild, statusBarHeight);
            }

            //add fake status bar view
            View mStatusBarView = new View(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            layoutParams.gravity = Gravity.TOP;
            mStatusBarView.setLayoutParams(layoutParams);
            mStatusBarView.setBackgroundColor(statusColor);
            mDecorView.addView(mStatusBarView, 0);
            mDecorView.setTag(TYPE_SET_STATUS_BAR);
        }
    }

    private static void addMarginTopToContentChild(View mContentChild, int margin) {
        if (mContentChild == null) {
            return;
        }
        //margin not exist
        if (mContentChild.getTag() == null || mContentChild.getTag() instanceof Integer && (int)mContentChild.getTag() != TYPE_SET_STATUS_BAR) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += margin;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TYPE_SET_STATUS_BAR);
        }
    }

    private static void removeMarginTopOfContentChild(View mContentChild, int margin, int tag) {
        if (mContentChild == null) {
            return;
        }
        //margin not exist
        if (mContentChild.getTag() != null && mContentChild.getTag() instanceof Integer && (int)mContentChild.getTag() == TYPE_SET_STATUS_BAR) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= margin;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(tag);
        }
    }

    public static void setStatusBarColorWithCollapsingToolbar(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

        int statusBarHeight = getStatusBarHeight(activity);
        View mContentChild = mContentView.getChildAt(0);

        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
        if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Integer) {
            View mStatusBarView = mDecorView.getChildAt(0);
            if (mStatusBarView != null) {
                mStatusBarView.setBackgroundColor(statusColor);
            }
            int type = (int) mDecorView.getTag();
            if (type != TYPE_SET_STATUS_BAR_WITH_COLLAPSING) {
                removeMarginTopOfContentChild(mContentChild, statusBarHeight, TYPE_SET_STATUS_BAR_WITH_COLLAPSING);
                mDecorView.setTag(TYPE_SET_STATUS_BAR_WITH_COLLAPSING);
            }
        } else {
            //add fake status bar view
            View mStatusBarView = new View(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            layoutParams.gravity = Gravity.TOP;
            mStatusBarView.setLayoutParams(layoutParams);
            mStatusBarView.setBackgroundColor(statusColor);
            mDecorView.addView(mStatusBarView, 0);
            mDecorView.setTag(TYPE_SET_STATUS_BAR_WITH_COLLAPSING);
        }

    }

    public static void translucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        int statusBarHeight = getStatusBarHeight(activity);
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        //set child View not fill the system window
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }

        if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Integer) {
            int type = (int) mDecorView.getTag();
            if (type != TYPE_TRANSLUCENT_STATUS_BAR) {
                removeMarginTopOfContentChild(mContentChild, statusBarHeight, TYPE_TRANSLUCENT_STATUS_BAR);
                mContentChild = mContentView.getChildAt(0);
                if (mContentChild != null) {
                    ViewCompat.setFitsSystemWindows(mContentChild, false);
                }
                mDecorView.setTag(TYPE_TRANSLUCENT_STATUS_BAR);
            }
        }

    }
}
