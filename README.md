# Android-SDK
Android SDK 编辑包以及使用说明

# 安装Android-SDK

SDK依赖包在本app项目示例libs文件夹下

Android sdk 通过外部依赖包引入项目

在app项目下创建libs文件夹，将依赖包放入libs文件夹

在build.gradle中输入以下代码同步依赖

```

// Use local packages
implementation fileTree(dir: 'libs', include: ['*.aar','*.jar'], exclude:[])

    api "org.slf4j:slf4j-api:1.7.30"

implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
implementation "androidx.work:work-runtime:2.7.1"
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
```

# 示例用法

本SDK即是一个简易示例，可通过Android Studio打开本项目运行示例

# 问题以及解决

如遇到模拟器无报错无法显示页面的情况，可通过简易修改对应页面的xml文件后重新启动