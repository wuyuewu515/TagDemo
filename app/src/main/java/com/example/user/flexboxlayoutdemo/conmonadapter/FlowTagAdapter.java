package com.example.user.flexboxlayoutdemo.conmonadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;

import com.example.user.flexboxlayoutdemo.R;
import com.example.user.flexboxlayoutdemo.TagInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import java.util.List;

/**
 * Created by user on 2018/7/12.
 * 为了实现长按事件，而重写tagAdapter的getView方法
 */

public class FlowTagAdapter extends TagAdapter<TagInfo> {
    private Activity mContext;
    private TagFlowLayout mFlowLayout;

    public FlowTagAdapter(Activity context, TagFlowLayout flowLayout, List<TagInfo> datas) {
        super(datas);
        this.mContext = context;
        this.mFlowLayout = flowLayout;

    }

    @Override
    public View getView(FlowLayout parent, final int position, final TagInfo info) {

        final CheckBox checkBox = (CheckBox) LayoutInflater.from(mContext)
                .inflate(R.layout.item_recyle_layout, mFlowLayout, false);
        checkBox.setText(info.getTitle());
        //通过获取父布局，来设置长点击事件
        checkBox.post(new Runnable() {
            @Override
            public void run() { //避免创建事件监听的时候，控件还未实例化

                ViewParent parent1 = checkBox.getParent();
                if (null == parent1) return;
                TagView tagView = (TagView) parent1;
                tagView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        checkBox.setTag(R.id.id_view_tag, position);
                        if (null != onLongClick) {
                            onLongClick.diyOnLongClick(position, info);
                        }
                        return true; //返回未true，onclick事件与onlongclick事件不同时发生
                    }
                });
            }
        });
        return checkBox;
    }

    /**
     * 自定义的长按事件
     */
    public interface DiyLongOnClickListener {
        void diyOnLongClick(int position, TagInfo info);
    }

    private DiyLongOnClickListener onLongClick;

    public FlowTagAdapter setOnLongClick(DiyLongOnClickListener onLongClick) {
        this.onLongClick = onLongClick;
        return this;
    }
}
