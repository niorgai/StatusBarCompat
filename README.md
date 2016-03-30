# StatusBarCompat
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-StatusBarCompat-green.svg?style=true)](https://android-arsenal.com/details/1/3349)

**Only work for SDK >= 19.**

[Demo Download](http://fir.im/StatusBarCompat)

# Usage

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
	 
Add the dependency

	dependencies {
	        compile 'com.github.niorgai:StatusBarCompat:1.0.0'
	}

SDK|KITKAT(19)|LOLLIPOP(21)
---|---|---
 |<img src="http://7sbqys.com1.z0.glb.clouddn.com/status_compat_19.gif" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/status_compat_21.gif" width="320x">

TranslucentStatusBar|SetStatusBarColor
---|---
<img src="http://7sbqys.com1.z0.glb.clouddn.com/fullScreen.png" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/Toolbar.png" width="320x">

 
---

This is a utils for status bar, And you **do not need to** change your `style.xml` file.

	//set color for status bar
	StatusBarCompat.setStatusBarColor(Activity activity)
	StatusBarCompat.setStatusBarColor(Activity activity, int color)
	
	//translucent status bar
	StatusBarCompat.translucentStatusBar(activity);
	
# How to use

1. Use it after call `setContentView` 
2. If use FullScreen Activity, must call `StatusBarCompat.translucentStatusBar(activity);`
	
# Demo Gif:
1. Both FloatingActionButton can toggle between `setStatusBarColor` and `translucentStatusBar`.
1. First Button will jump to an Activity which is use `StatusBarCompat`, It work perfect.
2. Second Button will jump to an Activity which is use `StatusBarCompat1`, It has a black line below statusBar when you call `setStatusBarColor` In KitKat.
3. Third Button will jump to an Activity which is use `StatusBarCompat2` and call `setStatusBarColor`. But it will not work if you call `translucentStatusBar` after In KitKat.
4. Fourth Button will jump to an Activity which is use `StatusBarCompat2` and call `translucentStatusBar`. But it will not work if you call `setStatusBarColor` after In KitKat.

# Link:
1. I had show three type to set statusBar, only the class `StatusBarCompat` is suggested, But you can see their different in my blog [Android-transulcent-status-bar](http://niorgai.github.io/2016/03/20/Android-transulcent-status-bar/).

中文版
---

透明状态栏只有在 SDK >= 19 (Android 4.4) 才会生效.

透明状态栏|设置状态栏的颜色
---|---
<img src="http://7sbqys.com1.z0.glb.clouddn.com/fullScreen.png" width="320x">|<img src="http://7sbqys.com1.z0.glb.clouddn.com/Toolbar.png" width="320x">)
 
这是一个为了兼容处理状态栏的工具类,可以不需要设置不同的`style.xml`文件,提供以下 API :

	//设置状态栏的颜色
	StatusBarCompat.setStatusBarColor(Activity activity)
	StatusBarCompat.setStatusBarColor(Activity activity, int color)
	
	//透明状态栏
	StatusBarCompat.translucentStatusBar(activity);
	
# 怎么使用
1. 在 `setContentView` 方法调用后再设置.
2. 如果使用了全屏 Activity ,记得调用`StatusBarCompat.translucentStatusBar(activity);`

# Demo 说明:
1. 每一个 FloatingActionButton 可以在 `setStatusBarColor` 和 `translucentStatusBar` 中切换.
1. 第一个按钮跳转的 Activity 使用 `StatusBarCompat`, 效果完美.
2. 第二个按钮跳转的 Activity 使用 `StatusBarCompat1`,在 Android 4.4-5.0 ,如果调用了 `setStatusBarColor`. 状态栏下方会有一条黑线. 
3. 第三个按钮跳转的 Activity 使用  `StatusBarCompat2` 并且调用 `setStatusBarColor`. 但是在 Android 4.4-5.0 , 如果接下来调用 `translucentStatusBar` 会无效.
4. 第四个按钮跳转的 Activity 使用  `StatusBarCompat2` 并且调用 `translucentStatusBar`. 但是在 Android 4.4-5.0 , 如果接下来调用 `setStatusBarColor` 会无效.
5. 这里提供了 `StatusBarCompat` , `StatusBarCompat1` , `StatusBarCompat2` 类,推荐使用`StatusBarCompat`类, 其他两个类的实现方式都有一些小问题.

# 更多
在我的博客[Android-transulcent-status-bar](http://niorgai.github.io/2016/03/20/Android-transulcent-status-bar/)中可以查看更多细节.

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
