package com.taobao.android.luaview.extend.binder;

import com.taobao.android.luaview.extend.userdata.UDNavigation;
import com.taobao.luaview.fun.base.BaseFunctionBinder;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.LibFunction;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/6/13.
 * Note: 因为SDK里面为了包体足够小,没有依赖support.v7包,但是第三方工程用Material Design的可能性极高,所以在这里覆盖"Navigation"组件。
 */

public class NavigationBinder extends BaseFunctionBinder {

    public NavigationBinder() {
        super("Navigation");
    }

    @Override
    public Class<? extends LibFunction> getMapperClass() {
        return null;
    }

    @Override
    public LuaValue createCreator(LuaValue env, final LuaValue metaTable) {
        return new UDNavigation(env.checkglobals(), metaTable);
    }
}

