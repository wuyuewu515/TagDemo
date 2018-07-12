package com.example.user.flexboxlayoutdemo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

/**
 * Created by user on 2018/7/6.
 * 标签数据实体类
 */

public class TagInfo implements Comparable<TagInfo> {
    @Expose   //注意该注解不能缺少。否则在使用jsonUtils时候不能转换
    private String title;
    @Expose
    private boolean isCheck = false;

    public TagInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public TagInfo setTitle(String title) {
        this.title = title;
        return this;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public TagInfo setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int compareTo(@NonNull TagInfo tagInfo) {
        if (this.title.equalsIgnoreCase(tagInfo.getTitle())) {  //只要标签内容相同就认为是一样的
            return 0;
        }
        return 1;
    }
}
