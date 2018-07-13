package com.example.user.flexboxlayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_flex;
    private Button btn_float;
    private TextView tv_result;

    private String jsonStr_flex = ""; //当前选中的标签json数据格式
    private String jsonStr_flow = ""; //当前选中的标签json数据格式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_flex = findViewById(R.id.btn_flex);
        btn_float = findViewById(R.id.btn_float);
        tv_result = findViewById(R.id.tv_result);

        btn_flex.setOnClickListener(this);
        btn_float.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_flex: { //flexbox
                intent = new Intent(this, FlexBoxActivity.class);
                intent.putExtra("jsonStr", jsonStr_flex);
                startActivityForResult(intent, 1001);
            }
            break;
            case R.id.btn_float: {//float
                intent = new Intent(this, FlowActivity.class);
                intent.putExtra("jsonStr", jsonStr_flow);
                startActivityForResult(intent, 1002);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            String dataStr = data.getStringExtra("jsonStr");
            switch (requestCode) {
                case 1001: { //flexbox布局实现
                    if (!TextUtils.isEmpty(dataStr)) {
                        jsonStr_flex = dataStr; //将上个界面返回json格式数据保存
                        tv_result.setText("flexBox方法选中的标签是：" + dataStr);
                    }
                }
                break;
                case 1002: { //flow布局实现
                    if (!TextUtils.isEmpty(dataStr)) {
                        jsonStr_flow = dataStr; //将上个界面返回数据保存
                        tv_result.setText("flexfLow方法选中的标签是：" + jsonStr_flow);
                    }
                }
                break;
            }
        }
    }
}
