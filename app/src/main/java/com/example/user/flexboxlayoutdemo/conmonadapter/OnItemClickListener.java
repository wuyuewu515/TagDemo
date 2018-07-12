package com.example.user.flexboxlayoutdemo.conmonadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2018/7/4.
 * 通用的RecyclerView 的ItemClickListener，包含点击和长按
 */

public interface OnItemClickListener<T> {
    void onItemClick(ViewGroup parent, View view, T t, int position);

    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}