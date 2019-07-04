package com.senon.passwordedittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 密码输入框控件
 */
public class PasswordEditText extends RelativeLayout {

    private EditText editText; //文本编辑框
    private Context context;

    private LinearLayout linearLayout; //文本密码的文本
    private TextView[] textViews; //文本数组

    private int pwdlength = 6; //密码长度， 默认6

    private OnTextFinishListener onTextFinishListener;


    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * @param bgDrawable    背景drawable
     * @param pwdLength     密码长度
     * @param splilineWidth 分割线宽度  (dp)
     * @param splilineColor 分割线颜色
     * @param pwdColor      密码字体颜色
     * @param pwdSize       密码字体大小 (sp)
     * @param pwdBg         密码背景
     */
    public void initStyle(int bgDrawable, int pwdLength, float splilineWidth, int splilineColor, int pwdColor, int pwdSize, int pwdBg) {
        this.pwdlength = pwdLength;
        initEdit(bgDrawable);
        initShowInput(bgDrawable, pwdlength, splilineWidth, splilineColor, pwdColor, pwdSize, pwdBg);
    }

    public int getPwdlength() {
        return pwdlength;
    }

    public void setPwdlength(int pwdlength) {
        this.pwdlength = pwdlength;
    }

    /**
     * 初始化编辑框
     *
     * @param bgcolor 输入框背景色
     */
    private void initEdit(int bgcolor) {
        editText = new EditText(context);
        editText.setBackgroundResource(bgcolor);
        editText.setCursorVisible(false);
        editText.setTextSize(0);
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdlength)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Editable etext = editText.getText();
                Selection.setSelection(etext, etext.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initDatas(s);
                if (s.length() == pwdlength) {
                    if (onTextFinishListener != null) {
                        onTextFinishListener.onFinish(s.toString().trim());
                    }
                }
            }
        });
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(editText, lp);

    }

    /**
     * @param bgColor       背景drawable
     * @param pwdLength     密码长度
     * @param slpilineWidth 分割线宽度
     * @param splilineColor 分割线颜色
     * @param pwdColor      密码字体颜色
     * @param pwdSize       密码字体大小
     * @param pwdBg         密码背景
     */
    public void initShowInput(int bgColor, int pwdLength, float slpilineWidth, int splilineColor, int pwdColor, int pwdSize, int pwdBg) {
        //添加密码框父布局
        linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(bgColor);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        //添加密码框
        textViews = new TextView[pwdLength];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(dip2px(context, slpilineWidth), LayoutParams.MATCH_PARENT);
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setTextSize(pwdSize);
            textViews[i].setTextColor(pwdColor);
            textViews[i].setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            textViews[i].setBackgroundResource(pwdBg);
            linearLayout.addView(textView, params);

            if (i < textViews.length - 1) {
                View view = new View(context);
                view.setBackgroundColor(splilineColor);
                linearLayout.addView(view, params2);
            }
        }
    }

    /**
     * 是否显示明文
     *
     * @param showPwd
     */
    public void setShowPwd(boolean showPwd) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            if (!showPwd) {
                textViews[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                textViews[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    public boolean getShowPwd() {
        return textViews[0].getTransformationMethod() == HideReturnsTransformationMethod.getInstance();
    }

    /**
     * 设置显示类型
     *
     * @param type
     */
    public void setInputType(int type) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            textViews[i].setInputType(type);
        }
    }

    /**
     * 清除文本框
     */
    public void clearText() {
        editText.setText("");
        for (int i = 0; i < pwdlength; i++) {
            textViews[i].setText("");
        }
    }

    public void setOnTextFinishListener(OnTextFinishListener onTextFinishListener) {
        this.onTextFinishListener = onTextFinishListener;
    }

    /**
     * 根据输入字符，显示密码个数
     *
     * @param s
     */
    public void initDatas(Editable s) {
        if (s.length() > 0) {
            int length = s.length();
            for (int i = 0; i < pwdlength; i++) {
                if (i < length) {
                    for (int j = 0; j < length; j++) {
                        char ch = s.charAt(j);
                        textViews[j].setText(String.valueOf(ch));
                    }
                } else {
                    textViews[i].setText("");
                }
            }
        } else {
            for (int i = 0; i < pwdlength; i++) {
                textViews[i].setText("");
            }
        }
    }

    public String getPwdText() {
        if (editText != null)
            return editText.getText().toString().trim();
        return "";
    }

    public void setPwdText(String text) {
        if (editText != null)
            editText.setText(text);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnTextFinishListener {
        void onFinish(String str);
    }

    public void setFocus() {
        editText.requestFocus();
        editText.setFocusable(true);
        showKeyBord(editText);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public void showKeyBord(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (null != editText)
            editText.setEnabled(enabled);
    }
}
