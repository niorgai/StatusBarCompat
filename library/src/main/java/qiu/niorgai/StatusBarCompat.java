package qiu.niorgai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {

    public static final String TAG = StatusBarCompat.class.getName();

    public static final int COLOR_DEFAULT_WHITE = Color.parseColor("#FFFFFFFF");
    public static final int COLOR_DEFAULT_PINK = Color.parseColor("#FFEF4968");

    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity, COLOR_DEFAULT_PINK);
    }

    /**
     * set statusBarColor
     * @param statusColor color
     * @param alpha 0 - 255
     */
    public static void setStatusBarColor(Activity activity, int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

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
                //set child View not fill the system window
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                }
            } else {
                int statusBarHeight = getStatusBarHeight(activity);

                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                    //if margin top has already set, just skip.
                    if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                        //do not use fitsSystemWindows
                        ViewCompat.setFitsSystemWindows(mChildView, false);
                        //add margin to content
                        lp.topMargin += statusBarHeight;
                        mChildView.setLayoutParams(lp);
                    }
                }

                //Before LOLLIPOP create a fake status bar View.
                View statusBarView = mContentView.getChildAt(0);
                if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                    //if fake status bar view exist, we can setBackgroundColor and return.
                    statusBarView.setBackgroundColor(statusColor);
                    return;
                }
                statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                statusBarView.setBackgroundColor(statusColor);
                mContentView.addView(statusBarView, 0, lp);
            }
        }
    }

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * change to full screen mode
     * @param hideStatusBarBackground hide status bar Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //set child View not fill the system window
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(activity);

            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //After LOLLIPOP just set LayoutParams.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (hideStatusBarBackground) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.parseColor("#00000000"));
                } else {
                    window.setStatusBarColor(Color.parseColor("#55000000"));
                }
                //must call requestLayout, otherwise it will have space in screen bottom
                if (mChildView != null) {
                    final View finalMChildView = mChildView;
                    window.getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            finalMChildView.requestLayout();

                        }
                    });
                }
            } else {
                if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
                    //Before LOLLIPOP need remove fake status bar view.
                    mContentView.removeView(mChildView);
                    mChildView = mContentView.getChildAt(0);
                }
                if (mChildView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                    //cancel the margin top
                    if (lp != null && lp.topMargin >= statusBarHeight) {
                        lp.topMargin -= statusBarHeight;
                        mChildView.setLayoutParams(lp);
                    }
                }
            }
        }
    }

    //Get status bar height
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    //Get alpha color
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}