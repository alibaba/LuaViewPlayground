package com.taobao.android.luaview.common.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.taobao.android.luaview.common.bridge.LVRouterBridge;
import com.taobao.android.luaview.common.interfaces.ILVMainEntry;
import com.taobao.android.luaview.common.provider.GlideImageProvider;
import com.taobao.android.luaview.common.script.LVExtScriptTask;
import com.taobao.android.luaview.extend.binder.NavigationBinder;
import com.taobao.android.luaview.extend.binder.UIRefreshScollerBinder;
import com.taobao.luaview.global.Constants;
import com.taobao.luaview.global.LuaScriptLoader;
import com.taobao.luaview.global.LuaView;
import com.taobao.luaview.global.LuaViewConfig;
import com.taobao.luaview.scriptbundle.ScriptBundle;

import java.util.HashMap;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/3/31.
 */

public class LVBasicActivity extends AppCompatActivity implements ILVMainEntry {

    public String mUri;
    private LuaView mLuaView;

    public LuaView getLuaView() {
        return mLuaView;
    }

    /**
     * 开发者可以在这里更改Lua代码的主入口
     * 默认使用kit包下的main.lua
     * 该包提供了一套界面描述和业务逻辑分离的机制
     * @return
     */
    @Override
    public String getLuaViewEntry() {
        return "kit/main.lua";
    }

    /**
     * 开发者可以在这里更改入口主页面, 默认是App.lua
     * @return
     */
    @Override
    public String getMainPage() {
        return "App";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);        // 禁止显示默认标题,由业务代码自行设定标题

        init();

        LuaView.createAsync(this, new LuaView.CreatedCallback() {
            @Override
            public void onCreated(LuaView luaView) {
                mLuaView = luaView;
                if (mLuaView != null) {
                    setContentView(mLuaView);
                    mLuaView.setUseStandardSyntax(true);     // 历史遗留原因,调用这行代码,才可以使用标准语法
                    register(luaView);
                    if (hasUrlParam()) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) LVBasicActivity.this.getIntent().getSerializableExtra(Constants.PAGE_PARAMS);
                        new LVExtScriptTask(LVBasicActivity.this, new LuaScriptLoader.ScriptLoaderCallback() {
                            @Override
                            public void onScriptLoaded(ScriptBundle bundle) {
                                // 配置完,注册完lib,下载解压完远程脚本,一切准备就绪以后,才可以调用load函数
                                mLuaView.load(getLuaViewEntry(), null, null);
                            }
                        }).load(hashMap.get("uri"));
                    } else {
                        // 配置完,注册完lib,下载解压完远程脚本,一切准备就绪以后,才可以调用load函数
                        mLuaView.load(getLuaViewEntry(), null, null);
                    }
                }
            }
        });
    }

    private boolean hasUrlParam() {
        if (this.getIntent() != null && this.getIntent().hasExtra(Constants.PAGE_PARAMS)) {
            HashMap<String, String> hashMap = (HashMap<String, String>) this.getIntent().getSerializableExtra(Constants.PAGE_PARAMS);
            if (hashMap.containsKey("needDownload") && hashMap.get("needDownload").equals("YES")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected void register(LuaView luaView) {
//        luaView.register("Bridge", new LVRouterBridge(this));
        luaView.register("Router", new LVRouterBridge(this));
        luaView.registerLibs(new UIRefreshScollerBinder());
        luaView.registerLibs(new NavigationBinder());
    }

    private void init() {
        LuaViewConfig.setDebug(true);
        LuaViewConfig.setOpenDebugger(false);
        LuaViewConfig.setLibsLazyLoad(true);
        LuaViewConfig.setAutoSetupClickEffects(true);

        LuaView.registerImageProvider(GlideImageProvider.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLuaView != null) {
            mLuaView.onDestroy();
        }
    }
}
