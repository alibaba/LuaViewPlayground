package com.taobao.android.luaview.extend.binder;

import com.taobao.android.luaview.extend.mapper.UIRefreshScrollerMethodMapper;
import com.taobao.android.luaview.extend.view.LVRefreshScroller;
import com.taobao.luaview.fun.base.BaseFunctionBinder;
import com.taobao.luaview.fun.base.BaseVarArgUICreator;
import com.taobao.luaview.view.interfaces.ILVView;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/5/18.
 */

public class UIRefreshScollerBinder extends BaseFunctionBinder {

    public UIRefreshScollerBinder() {
        super("RefreshScroller");
    }

    @Override
    public Class<? extends LibFunction> getMapperClass() {
        return UIRefreshScrollerMethodMapper.class;
    }

    @Override
    public LuaValue createCreator(LuaValue env, LuaValue metaTable) {
        return new BaseVarArgUICreator(env.checkglobals(), metaTable, getMapperClass()) {
            @Override
            public ILVView createView(Globals globals, LuaValue metaTable, Varargs varargs) {
                return new LVRefreshScroller(globals, metaTable, varargs);
            }
        };
    }
}
