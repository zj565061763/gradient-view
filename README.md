# About
Android gradient progress view

# Gradle
[![](https://jitpack.io/v/zj565061763/gradient-view.svg)](https://jitpack.io/#zj565061763/gradient-view)

# Example
![](https://thumbsnap.com/i/KwVCQybG.gif?1106)

```java
// 设置正常状态颜色
view_gradient.setColorNormal(getResources().getColor(R.color.colorNormalStart), getResources().getColor(R.color.colorNormalEnd));
// 设置进度状态颜色
view_gradient.setColorProgress(getResources().getColor(R.color.colorProgressStart), getResources().getColor(R.color.colorProgressEnd));
// 设置进度[0-1]
view_gradient.setProgress(0.5f);
```