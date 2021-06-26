package com.test.finalproject;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //用来判断textview中是否清空
    boolean clr_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将按钮放在数组里方便循环
        int[] ids = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_plus, R.id.btn_minus,
                R.id.btn_multiply, R.id.btn_divide, R.id.btn_point, R.id.btn_equal, R.id.btn_AC};
        //用for循环将每个按钮都设置监听事件，避免了多行重复代码
        for(int i = 0; i < ids.length; i++)
            findViewById(ids[i]).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView tv_input = (TextView) findViewById(R.id.text1);
        Button btn = (Button) v;
        //获取输入
        String str = tv_input.getText().toString();
        //点击按钮得到文本
        String strButton = btn.getText().toString();
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (str.equals("0"))
                    tv_input.setText(strButton);
                else
                    tv_input.setText(str + strButton);
                break;
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                tv_input.setText(str + strButton);
                break;
            case R.id.btn_point:
                if(clr_flag){
                    clr_flag=false;
                    str="";
                    tv_input.setText("");
                }
                tv_input.setText(str+((Button)v).getText());
                break;
            case R.id.btn_equal:
                MyCalc obj = new MyCalc(str);
                double ret = obj.Calc();
                int Ret = (int) ret;
                if (ret == Ret)
                    tv_input.setText(String.valueOf(Ret));
                else
                    tv_input.setText(String.valueOf(ret));
                break;
            case R.id.btn_AC:
                tv_input.setText("0");
                break;
        }
    }

    class MyCalc {
        private String input;

        public MyCalc(String input) {
            this.input = input;
        }

        public double Calc() {
            if (TextUtils.isEmpty(input))
                return 0;
            Pattern pattern = Pattern.compile("[+(×)(÷)/-]");
            String[] nums = pattern.split(input);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find() == false)
                return 0;
            String op = matcher.group(0);
            double first = Double.parseDouble(nums[0]);
            double second = Double.parseDouble(nums[1]);
            double sum = 0;
            try {
                switch (op) {
                    case "+":
                        sum = first + second;
                        break;
                    case "-":
                        sum = first - second;
                        break;
                    case "×":
                        sum = first * second;
                        break;
                    case "÷":
                        sum = first / second;
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "出错", Toast.LENGTH_LONG).show();
            }
            return sum;
        }
    }
}

