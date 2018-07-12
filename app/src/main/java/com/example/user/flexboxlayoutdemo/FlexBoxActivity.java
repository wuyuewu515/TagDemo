package com.example.user.flexboxlayoutdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.flexboxlayoutdemo.conmonadapter.CommonAdapter;
import com.example.user.flexboxlayoutdemo.conmonadapter.OnItemClickListener;
import com.example.user.flexboxlayoutdemo.conmonadapter.ViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class FlexBoxActivity extends AppCompatActivity {
    private Activity mActivity;
    private RecyclerView recyclerView;
    private RecyAdapter adapter;

    //全部的数据
    private List<TagInfo> datas = new ArrayList<>();
    //选中的数据
    private List<String> checkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexbox_activity);
        recyclerView = findViewById(R.id.recyleview);
        mActivity = this;
        datas.clear();
        checkList.clear();


        //服务器端的标签
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


        //去重操作
        String jsonStr = getIntent().getStringExtra("jsonStr");
        if (!TextUtils.isEmpty(jsonStr)) {
            checkList.addAll(JsonUtils.<String>json2arr(jsonStr));
            for (String title : checkList) {

                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getTitle().equalsIgnoreCase(title)) {//总的标签里面含有这个标签
                        datas.get(i).setCheck(true);
                        break;
                    } else {
                        if (i == datas.size() - 1)
                            datas.add(new TagInfo(title).setCheck(true));//不存在 ----这种情况是属于自己能够创建新标签，这个功能没做
                    }
                }
            }
        }

        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        adapter = new RecyAdapter(this, R.layout.item_recyle_layout, datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Log.i("TAG", "这个是点击事件");
                String tag = datas.get(position).getTitle();

                if (checkList.contains(tag)) { //当前集合里面有该元素就删除
                    checkList.remove(tag);
                } else {
                    checkList.add(tag);
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                Toast.makeText(mActivity, "这个是长按事件", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        String jsonStr = "";
        if (checkList.size() > 0) {
            //固定添加“小米超神”标签
            if (!checkList.contains("小米超神"))
                checkList.add("小米超神");
            jsonStr = JsonUtils.toJson(checkList);
        }
        Intent intent = new Intent();
        intent.putExtra("jsonStr", jsonStr);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    class RecyAdapter extends CommonAdapter<TagInfo> {
        public RecyAdapter(Context context, int layoutId, List<TagInfo> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder holder, TagInfo item) {
            holder.setText(R.id.cb_item, item.getTitle());
            holder.setChecked(R.id.cb_item, item.isCheck());
        }
    }
}
