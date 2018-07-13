package com.example.user.flexboxlayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.user.flexboxlayoutdemo.conmonadapter.FlowTagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 2018/7/12.
 * 鸿洋标签框架
 */

public class FlowActivity extends AppCompatActivity {

    private Activity mContext;
    private TagFlowLayout id_flowlayout;
    private FlowTagAdapter flowTagAdapter;
    //全部的数据
    private List<TagInfo> datas = new ArrayList<>();
    //选中的数据
    private List<String> checkList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_activity);
        mContext = this;
        id_flowlayout = findViewById(R.id.id_flowlayout);
        initData();

    }

    private void initData() {
        //服务器端的标签---固定标签
        datas.add(new TagInfo("穿越火线"));
        datas.add(new TagInfo("绝地求生"));
        datas.add(new TagInfo("阴阳师"));
        datas.add(new TagInfo("开心消消乐"));
        datas.add(new TagInfo("刺激战场"));
        datas.add(new TagInfo("全军出击"));
        datas.add(new TagInfo("王者荣耀"));
        datas.add(new TagInfo("荒野求生"));
        datas.add(new TagInfo("终结者2"));
        datas.add(new TagInfo("第五人格"));


        flowTagAdapter = new FlowTagAdapter(this, id_flowlayout, datas);
        flowTagAdapter.setOnLongClick(new FlowTagAdapter.DiyLongOnClickListener() {
            @Override
            public void diyOnLongClick(int position, TagInfo info) {
                Toast.makeText(mContext, "长按的是" + info.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        id_flowlayout.setAdapter(flowTagAdapter);


        //去重操作
        String jsonStr = getIntent().getStringExtra("jsonStr");
        if (!TextUtils.isEmpty(jsonStr)) {
            checkList.addAll(JsonUtils.<String>json2arr(jsonStr));
            for (String title : checkList) {
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getTitle().equalsIgnoreCase(title)) {//总的标签里面含有这个标签
                        datas.get(i);
                        set.add(i);
                        break;
                    } else {
                        if (i == datas.size() - 1) {
                            datas.add(new TagInfo(title));//不存在 ----这种情况是属于自己能够创建新标签，这个功能没做

                        }
                    }
                }
            }
        }
        //设置之前选中的数据
        flowTagAdapter.setSelectedList(set);

    }

    private Set<Integer> set = new HashSet<>();

    @Override
    public void onBackPressed() {
        Set<Integer> selectedList = id_flowlayout.getSelectedList();
        Log.i("TAG", "选中的条目是" + selectedList);
        checkList.clear();
        for (Integer integer : selectedList) {
            String title = datas.get(integer).getTitle();
            checkList.add(title);
        }
        String jsonStr = "";
        if (checkList.size() > 0) {
            //固定添加“小米超神”标签
            if (!checkList.contains("小米超神"))
                checkList.add("小米超神");
            if (!checkList.contains("三国杀"))
                checkList.add("三国杀");
            jsonStr = JsonUtils.toJson(checkList);
        }
        Intent intent = new Intent();
        intent.putExtra("jsonStr", jsonStr);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
