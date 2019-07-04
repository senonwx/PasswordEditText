# PasswordEditText
自定义密码输入框
-------
<img src="https://github.com/senonwx/PasswordEditText/blob/master/screen.gif" width="50%"><img/><br/>
1.xml加入：   
```
<com.senon.passwordedittext.PasswordEditText
    android:id="@+id/editText"
    android:layout_width="280dp"
    android:layout_height="60dp"
    />
```
2.页面设置密码框style
```
//设置密码框style
editText.initStyle(
        R.drawable.background_box,//输入体背景
        4,//密码长度
        15,//分割宽度
        0x00ffffff,//分割线颜色
        0xff333333,//字体颜色
        20,//字体大小
        R.drawable.backgroud_password//字体背景
);
```
* 监听输入框输入完整监听
```
editText.setOnTextFinishListener(new PasswordEditText.OnTextFinishListener() {
    @Override
    public void onFinish(String str) {
        Log.e(TAG, "onFinish  str= " + str);
    }
});
```
* 是否文明显示 
```
editText.setShowPwd(false);//默认为false
```
* 设置文本 
```
editText.setPwdText("1234");
```
* 清空文本 
```
editText.clearText();
```
* 获取文本 
```
editText.getPwdText();
```
* 设置焦点并打开软键盘 
```
editText.setFocus();
```
## 可配置属性

#### PasswordEditText

属性名 | 属性说明
:---:|:---:
bgColor | 输入体背景(@DrawableRes int resid)
pwdLength | 密码长度(int length)
slpilineWidth | 分割宽度(float dipValue)
splilineColor | 分割线颜色(@ColorInt int color)
pwdColor | 字体颜色(@ColorInt int color)
pwdSize | 字体大小(float dp)
pwdBg | 字体背景(@DrawableRes int resid)

更多功能运行demo体验


