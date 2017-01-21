package qiu.niorgai;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * After Lollipop use system method.
 * Created by qiu on 8/27/16.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarCompatLollipop {

    /**
     * return statusBar's Height in pixels
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * set StatusBarColor
     *
     * 1. set Flags to call setStatusBarColor
     * 2. call setSystemUiVisibility to clear translucentStatusBar's Flag.
     * 3. set FitsSystemWindows to false
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * translucentStatusBar(full-screen)
     *
     * 1. set Flags to full-screen
     * 2. set FitsSystemWindows to false
     *
     * @param hideStatusBarBackground hide statusBar's shadow
     */
    static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * compat for CollapsingToolbarLayout
     *
     * 1. change to full-screen mode(like translucentStatusBar).
     * 2. cancel CollapsingToolbarLayout's WindowInsets, let it layout as normal(now setStatusBarScrimColor is useless).
     * 3. set View's FitsSystemWindow to false.
     * 4. adjust toolbar's height to layout.
     * 5. change statusBarColor by AppBarLayout's offset.
     * 6. add Listener to change statusBarColor
     */
    static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
                                                      Toolbar toolbar, final int statusColor) {
        final Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return insets;
            }
        });

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }

        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);

        toolbar.setFitsSystemWindows(true);
        if (toolbar.getTag() == null) {
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.height += getStatusBarHeight(activity);
            toolbar.setLayoutParams(lp);
            toolbar.setTag(true);
        }

        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior != null && behavior instanceof AppBarLayout.Behavior) {
            int verticalOffset = ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
            if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                window.setStatusBarColor(statusColor);
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        } else {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        collapsingToolbarLayout.setFitsSystemWindows(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    if (window.getStatusBarColor() == Color.TRANSPARENT) {
                        ValueAnimator animator = ValueAnimator.ofArgb(Color.TRANSPARENT, statusColor)
                                .setDuration(collapsingToolbarLayout.getScrimAnimationDuration());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                window.setStatusBarColor((Integer) valueAnimator.getAnimatedValue());
                            }
                        });
                        animator.start();
                    }
                } else {
                    if (window.getStatusBarColor() == statusColor) {
                        ValueAnimator animator = ValueAnimator.ofArgb(statusColor, Color.TRANSPARENT)
                                .setDuration(collapsingToolbarLayout.getScrimAnimationDuration());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                window.setStatusBarColor((Integer) valueAnimator.getAnimatedValue());
                            }
                        });
                        animator.start();
                    }
                }
            }
        });
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
        collapsingToolbarLayout.setStatusBarScrimColor(statusColor);
    }
}
