package com.senon.passwordedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private PasswordEditText editText, editText2, editText3;
    private Button clear_text, clear, set, get_password;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        clear_text = findViewById(R.id.clear_text);
        clear = findViewById(R.id.clear);
        set = findViewById(R.id.set);
        get_password = findViewById(R.id.get_password);
        tv = findViewById(R.id.tv);

        initView();
        initListener();
    }

    private void initListener() {
        //清空输入框
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearText();
                editText2.clearText();
                editText3.clearText();
            }
        });

        //是否明文显示
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setShowPwd(!editText.getShowPwd());
                editText2.setShowPwd(!editText2.getShowPwd());
                editText3.setShowPwd(!editText3.getShowPwd());
            }
        });

        //设置数字
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setPwdText("1234");
                editText2.setPwdText("1234");
                editText3.setPwdText("123456");
            }
        });

        //设置数字
        get_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("editText:" + editText.getPwdText() +
                        "\neditText2:" + editText2.getPwdText() +
                        "\neditText3:" + editText3.getPwdText());
            }
        });
    }

    private void initView() {
        //设置密码框style
        editText.initStyle(
                R.drawable.background_box_one,//输入框背景
                4,//密码长度
                15,//分割宽度
                0x00ffffff,//分割线颜色
                0xff333333,//字体颜色
                20,//字体大小
                R.drawable.backgroud_password_one//字体背景
        );
        editText.setOnTextFinishListener(new PasswordEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Log.e(TAG, "onFinish  str= " + str);
                editText2.setFocus();
            }
        });


        //设置密码框style
        editText2.initStyle(R.drawable.background_box_two, 4, 15,
                0x00ffffff, 0xff333333, 20,
                R.drawable.backgroud_password_two);
        editText2.setOnTextFinishListener(new PasswordEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Log.e(TAG, "onFinish  str= " + str);
                if (str.equals(editText.getPwdText())) {
                    Toast.makeText(MainActivity.this, "输入密码相同，恭喜啊！" + str, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "两次密码不同，走开啊！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //设置密码框style
        editText3.initStyle(R.drawable.background_box_three, 6, 1,
                0xff999999, 0xffDD2424, 20,
                R.drawable.backgroud_password_three);
        editText3.setOnTextFinishListener(new PasswordEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Log.e(TAG, "onFinish  str= " + str);
                Toast.makeText(MainActivity.this, "editText3 content= " + str, Toast.LENGTH_SHORT).show();
            }
        });


    }


}
