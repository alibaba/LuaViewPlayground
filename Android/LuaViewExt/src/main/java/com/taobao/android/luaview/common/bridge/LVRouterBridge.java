package com.taobao.android.luaview.common.bridge;

import android.content.Intent;

import com.taobao.android.luaview.common.activity.LVBasicActivity;
import com.taobao.luaview.fun.mapper.LuaViewApi;
import com.taobao.luaview.global.Constants;
import com.taobao.luaview.scriptbundle.LuaScriptManager;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/3/31.
 */

public class LVRouterBridge {

    private LVBasicActivity mActivity;

    public LVRouterBridge(LVBasicActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Lua业务代码层可以使用该API请求跳转到一个新的Activity。
     * @param params
     */
    @LuaViewApi
    public void require(LuaTable params) {
        HashMap<String, String> hashMap = new HashMap<>();

        LuaValue[] k = params.keys();
        for (int i=0; i < k.length; i++) {
            hashMap.put(k[i].optjstring(null), params.get(k[i]).optjstring(null));
        }

        /**
         * 启动另一个Activity之前,把当前Activity的packagePath和uri参数透传给下一个Activity。
         */
        if (mActivity.mUri != null) {
            hashMap.put("uri", mActivity.mUri);
            hashMap.put("packagePath", LuaScriptManager.buildScriptBundleFolderPath(mActivity.mUri));
        }

        Intent intent = new Intent(mActivity, LVBasicActivity.class);
        intent.putExtra(Constants.PAGE_PARAMS, hashMap);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }

    /**
     * 使用该API获取上一个Activity透传过来的消息。
     * @return
     */
    @LuaViewApi
    public LuaTable args() {
        if (mActivity.getIntent() != null && mActivity.getIntent().hasExtra(Constants.PAGE_PARAMS)) {
            HashMap<String, String> hashMap = (HashMap<String, String>)mActivity.getIntent().getSerializableExtra(Constants.PAGE_PARAMS);
            LuaTable table = new LuaTable();
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                table.set(entry.getKey(), entry.getValue());

                /**
                 * 获取上一个Activity透传过来的uri参数,并设置该Activity对应的Lua VM的mBaseBundlePath。
                 */
                if (entry.getKey().equals("uri")) {
                    mActivity.mUri = entry.getValue();
                    mActivity.getLuaView().setUri(mActivity.mUri);
                }
            }
            return table;
        } else {
            LuaTable table = new LuaTable();
            table.set("page", mActivity.getMainPage());
            return table;
        }
    }
}
