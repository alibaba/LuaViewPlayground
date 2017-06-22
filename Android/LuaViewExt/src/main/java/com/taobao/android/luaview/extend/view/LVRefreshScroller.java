package com.taobao.android.luaview.extend.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.taobao.android.luaview.common.R;
import com.taobao.android.luaview.extend.userdata.UDRefreshScroller;
import com.taobao.luaview.userdata.ui.UDView;
import com.taobao.luaview.util.LuaViewUtil;
import com.taobao.luaview.view.interfaces.ILVViewGroup;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.ArrayList;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/5/18.
 */

public class LVRefreshScroller extends SwipeRefreshLayout implements ILVViewGroup {

    private UDRefreshScroller mLuaUserdata;

    private RelativeLayout mHeaderContainer;
    private RelativeLayout mPinnedContainer;
    private RelativeLayout mContentContainer;

    public LVRefreshScroller(Globals globals, LuaValue metaTable, Varargs varargs) {
        super(globals.getContext());

        mLuaUserdata = new UDRefreshScroller(this, globals, metaTable, varargs != null ? varargs.arg1() : null);

        LayoutInflater inflater = (LayoutInflater) globals.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.lv_scroller, null);
        this.addView(view, LuaViewUtil.createRelativeLayoutParamsMM());

        mHeaderContainer = (RelativeLayout) view.findViewById(R.id.header_container);
        mPinnedContainer = (RelativeLayout) view.findViewById(R.id.pinned_container);
        mContentContainer = (RelativeLayout) view.findViewById(R.id.content_container);

        // 测试
        this.setEnabled(false);
    }

    @Override
    public UDView getUserdata() {
        return mLuaUserdata;
    }

    @Override
    public void setChildNodeViews(ArrayList<UDView> childNodeViews) {
        return;
    }

    public void addHeaderView(View view, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        mHeaderContainer.addView(view, params);

        // flexbox needs know the style
        mLuaUserdata.getCssNode().setStyleWidth(width);
        mLuaUserdata.getCssNode().setStyleHeight(height);
    }

    public void addPinnedView(View view, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        mPinnedContainer.addView(view, params);

        // flexbox needs know the style
        mLuaUserdata.getCssNode().setStyleWidth(width);
        mLuaUserdata.getCssNode().setStyleHeight(height);
    }

    public void addContentView(View view, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        mContentContainer.addView(view, params);

        // flexbox needs know the style
        mLuaUserdata.getCssNode().setStyleWidth(width);
        mLuaUserdata.getCssNode().setStyleHeight(height);
    }
}
