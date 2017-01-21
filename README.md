# StatusBarCompat
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-StatusBarCompat-green.svg?style=true)](https://android-arsenal.com/details/1/3349)

**Only work for SDK >= 19.**

[Demo Download](http://fir.im/StatusBarCompat)

# Usage

Add it in your root build.gradle at the end of repositories:

```groovy

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
	 
Add the dependency

```groovy

	dependencies {
	        compile ('com.github.niorgai:StatusBarCompat:2.1.2', {
                exclude group: 'com.android.support'
            })
	}
```

SDK|KITKAT(19)|LOLLIPOP(21)
---|---|---
 |<img src="http://7sbqys.com1.z0.glb.clouddn.com/status_compat_19.gif" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/status_compat_21.gif" width="320x">

TranslucentStatusBar|SetStatusBarColor
---|---
<img src="http://7sbqys.com1.z0.glb.clouddn.com/fullScreen.png" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/Toolbar.png" width="320x">

 
---

This is a utils for status bar, And you **do not need to** change your `style.xml` file.
The most feature is **you can toggle `setStatusBarColor` and `translucentStatusBar` without recreate activity**.

```java
	//set color for status bar
	StatusBarCompat.setStatusBarColor(Activity activity, int color)
	//add alpha to color
	StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)
	
	//translucent status bar
	StatusBarCompat.translucentStatusBar(activity);
	//should hide status bar background (default black background) when SDK >= 21
	StatusBarCompat.translucentStatusBar(Activity activity, boolean hideStatusBarBackground);

	//set color for CollapsingToolbarLayout
	setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                                         Toolbar toolbar, int statusColor)
	
```

# How to use

1. Use it after call `setContentView` 
2. If use FullScreen Activity, must call `StatusBarCompat.translucentStatusBar(activity);`
	
# About Demo:
Demo shows One Activity with four Fragments.

1. CommonFragment call `setStatusBarColor` method, SeekBar can change statusBar's alpha.
2. TranslucentFragment call `translucentStatusBar` method, when sdk >= 21, button can toggle hide/show statusBar's shadow.
3. DrawerFragment call `setStatusBarColor` method, show how to compat for DrawerLayout and CoordinatorLayout.
4. CollapsingFragment call `setStatusBarColorForCollapsingToolbar` method, compat for CollapsingToolbarLayout.

## Issues:
These problem only show in SDK between 19 and 21:

1. If work with `TabActivity`, It will show a black line in contentView, so you can call `StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)` and the suggest alpha is 112.
2. If first view is `DrawerLayout`, let its child view set `fitsSystemWindow` to false.


# Link:
1. Blog [Android-translucent-status-bar](http://niorgai.github.io/2016/03/20/Android-transulcent-status-bar/).

中文版
---

透明状态栏只有在 SDK >= 19 (Android 4.4) 才会生效.

透明状态栏|设置状态栏的颜色
---|---
<img src="http://7sbqys.com1.z0.glb.clouddn.com/fullScreen.png" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/Toolbar.png" width="320x">)
 
这是一个为了兼容处理状态栏的工具类,可以不需要设置不同的`style.xml`文件,最重要的特性就是可以在**不重启 Activity 的情况下切换 `setStatusBarColor` 和 `translucentStatusBar` **. 提供以下 API :

```java
	    //设置状态栏的颜色
    	StatusBarCompat.setStatusBarColor(Activity activity, int color)
    	//添加alpha值
    	StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

        //透明状态栏
    	StatusBarCompat.translucentStatusBar(activity);
    	//SDK >= 21时, 取消状态栏的阴影
    	StatusBarCompat.translucentStatusBar(Activity activity, boolean hideStatusBarBackground);

    	//为 CollapsingToolbarLayout 设置颜色
    	setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                                     Toolbar toolbar, int statusColor)
```

# 怎么使用
1. 在 `setContentView` 方法调用后再设置.
2. 如果使用了全屏 Activity ,记得调用`StatusBarCompat.translucentStatusBar(activity);`

# Demo 说明:
Demo 只有一个 Activity , 分四个 Tab.

1. CommonFragment 为正常布局, 可以通过 SeekBar 来调整状态栏颜色的 alpha 值. 调用方法为: `setStatusBarColor`.
2. TranslucentFragment 为图片布局, 展示透明状态栏效果, SDK >= 21时, 可以通过按钮显示\隐藏 statusBar 的 shadow, 调用方法为: `translucentStatusBar`.
3. DrawerFragment 展示 DrawerLayout + CoordinatorLayout, 调用方法为: `setStatusBarColor`.
4. CollapsingFragment 展示 CollapsingLayout 的适配效果, 调用方法为: `setStatusBarColorForCollapsingToolbar`.

## 已知问题
这些问题仅仅出现在 4.4 上

1. 如果用在 TabActivity 上, 会有一条黑线在状态栏下面, 推荐使用 `StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)` 方法, 推荐的透明值为 112.
2. 如果 layout 中第一个 View 为 DrawerLayout, 那么它的子 View 的 fitsSystemWindow 需要设置为 false.

# 更多
在我的博客[Android-translucent-status-bar](http://niorgai.github.io/2016/03/20/Android-transulcent-status-bar/)中可以查看更多细节.

# License
	
	The MIT License (MIT)
	
	Copyright (c) 2016 JQ
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
