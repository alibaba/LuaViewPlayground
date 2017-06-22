package com.taobao.android.luaview.extend.userdata;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.taobao.android.luaview.extend.view.LVRefreshScroller;
import com.taobao.luaview.userdata.ui.UDViewGroup;
import com.taobao.luaview.util.LuaUtil;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/5/18.
 */

public class UDRefreshScroller extends UDViewGroup<LVRefreshScroller> {

    public UDRefreshScroller(LVRefreshScroller view, Globals globals, LuaValue metaTable, LuaValue initParams) {
        super(view, globals, metaTable, initParams);
        init();
    }

    private void init() {
        LVRefreshScroller v = getView();
        if (v != null) {
            v.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (LuaUtil.isValid(mCallback)) {
                        LuaUtil.callFunction(LuaUtil.getFunction(mCallback, "Refreshing", "refreshing"));
                    }
                }
            });
        }
    }

    public void stopRefreshing() {
        getView().setRefreshing(false);
    }

    public void addHeaderView(View view, int width, int height) {
        getView().addHeaderView(view, width, height);
    }

    public void addPinnedView(View view, int width, int height) {
        getView().addPinnedView(view, width, height);
    }

    public void addContentView(View view, int width, int height) {
        getView().addContentView(view, width, height);
    }
}
