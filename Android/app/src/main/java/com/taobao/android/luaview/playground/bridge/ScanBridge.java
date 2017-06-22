package com.taobao.android.luaview.playground.bridge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.taobao.android.luaview.playground.activity.MainActivity;
import com.taobao.luaview.fun.mapper.LuaViewApi;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/6/14.
 */

public class ScanBridge {

    private MainActivity mActivity;

    public ScanBridge(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * 用于启动扫码的Activity,启动之前要做权限判断。
     */
    @LuaViewApi
    public void start() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {//还没有授予权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
                Toast.makeText(mActivity, "Please Allow the Camera permission.", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, MainActivity.REQUEST_CAMERA);
            }
        } else {
            mActivity.startActivityForResult(new Intent(mActivity , CaptureActivity.class) , 0);
        }
    }
}
