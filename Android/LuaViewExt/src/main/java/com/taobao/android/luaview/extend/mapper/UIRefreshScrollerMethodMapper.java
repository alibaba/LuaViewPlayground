package com.taobao.android.luaview.extend.mapper;


import com.taobao.android.luaview.extend.userdata.UDRefreshScroller;
import com.taobao.luaview.fun.mapper.LuaViewLib;
import com.taobao.luaview.fun.mapper.ui.UIViewGroupMethodMapper;
import com.taobao.luaview.userdata.ui.UDView;
import com.taobao.luaview.util.DimenUtil;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.List;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/5/18.
 */
@LuaViewLib
public class UIRefreshScrollerMethodMapper <U extends UDRefreshScroller> extends UIViewGroupMethodMapper<U> {

    private static final String TAG = "UIRefreshScrollerMethodMapper";

    private static final String[] sMethods = new String[] {
            "stopRefreshing",
            "addHeaderView",
            "addPinnedView",
            "addContentView"
    };

    @Override
    public List<String> getAllFunctionNames() {
        return mergeFunctionNames(TAG, super.getAllFunctionNames(), sMethods);
    }

    @Override
    public Varargs invoke(int code, U target, Varargs varargs) {
        final int optcode = code - super.getAllFunctionNames().size();
        switch (optcode) {
            case 0:
                return stopRefreshing(target, varargs);
            case 1:
                return addHeaderView(target, varargs);
            case 2:
                return addPinnedView(target, varargs);
            case 3:
                return addContentView(target, varargs);
        }
        return super.invoke(code, target, varargs);
    }

    public LuaValue stopRefreshing(U view, Varargs varargs) {
        view.stopRefreshing();
        return this;
    }

    public LuaValue addHeaderView(U view, Varargs varargs) {
        final LuaValue luaValue = varargs.optvalue(2, null);
        final int width = DimenUtil.dpiToPx(varargs.arg(3));
        final int height = DimenUtil.dpiToPx(varargs.arg(4));
        view.addHeaderView(((UDView)luaValue).getView(), width, height);
        return this;
    }

    public LuaValue addPinnedView(U view, Varargs varargs) {
        final LuaValue luaValue = varargs.optvalue(2, null);
        final int width = DimenUtil.dpiToPx(varargs.arg(3));
        final int height = DimenUtil.dpiToPx(varargs.arg(4));
        view.addPinnedView(((UDView)luaValue).getView(), width, height);
        return this;
    }

    public LuaValue addContentView(U view, Varargs varargs) {
        final LuaValue luaValue = varargs.optvalue(2, null);
        final int width = DimenUtil.dpiToPx(varargs.arg(3));
        final int height = DimenUtil.dpiToPx(varargs.arg(4));
        view.addContentView(((UDView)luaValue).getView(), width, height);
        return this;
    }
}
