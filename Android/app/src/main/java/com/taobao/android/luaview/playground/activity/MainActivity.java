package com.taobao.android.luaview.playground.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.taobao.android.luaview.common.activity.LVBasicActivity;
import com.taobao.android.luaview.playground.bridge.ScanBridge;
import com.taobao.luaview.global.Constants;
import com.taobao.luaview.global.LuaView;
import com.taobao.luaview.scriptbundle.LuaScriptManager;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.HashMap;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/3/27.
 */

public class MainActivity extends LVBasicActivity {

    public final static int REQUEST_CAMERA = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("uri",result);
            hashMap.put("packagePath", LuaScriptManager.buildScriptBundleFolderPath(result));
            hashMap.put("needDownload","YES");

            Intent intent = new Intent(this, LVBasicActivity.class);
            intent.putExtra(Constants.PAGE_PARAMS, hashMap);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
    }

    @Override
    protected void register(LuaView luaView) {
        super.register(luaView);
        luaView.register("Scan", new ScanBridge(MainActivity.this));    // 主Activity增加扫码API的桥接
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.startActivityForResult(new Intent(this , CaptureActivity.class) , 0);
                } else {
                    Toast.makeText(this, "Camera Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}
