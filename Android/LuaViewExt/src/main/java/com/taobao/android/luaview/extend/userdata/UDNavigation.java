package com.taobao.android.luaview.extend.userdata;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.taobao.luaview.fun.mapper.LuaViewLib;
import com.taobao.luaview.global.Constants;
import com.taobao.luaview.userdata.base.BaseLuaTable;
import com.taobao.luaview.userdata.kit.UDBitmap;
import com.taobao.luaview.userdata.ui.UDImageView;
import com.taobao.luaview.userdata.ui.UDSpannableString;
import com.taobao.luaview.userdata.ui.UDView;
import com.taobao.luaview.util.ImageUtil;
import com.taobao.luaview.util.LuaViewUtil;
import com.taobao.luaview.view.imageview.BaseImageView;
import com.taobao.luaview.view.imageview.DrawableLoadCallback;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Copyright 2017 Alibaba Group
 * License: MIT
 * https://alibaba.github.io/LuaViewSDK
 * Created by tuoli on 17/6/13.
 */

@LuaViewLib
public class UDNavigation extends BaseLuaTable {

    public UDNavigation(Globals globals, LuaValue metatable) {
        super(globals, metatable);
        init();
    }

    private void init() {
        set("title", new UDNavigation.title());
        set("setTitle", new UDNavigation.setTitle());
        set("getTitle", new UDNavigation.getTitle());
        set("background", new UDNavigation.background());
        set("setBackground", new UDNavigation.setBackground());
        set("getBackground", new UDNavigation.getBackground());
        set("left", new UDNavigation.left());
        set("leftBarButton", new UDNavigation.left());//@Deprecated
        set("right", new UDNavigation.right());
        set("rightBarButton", new UDNavigation.right());//@Deprecated
    }

    /**
     * 系统中间View
     */
    class title extends VarArgFunction {

        @Override
        public Varargs invoke(Varargs args) {
            if (args.narg() > 1) {
                return new UDNavigation.setTitle().invoke(args);
            } else {
                return new UDNavigation.getTitle().invoke(args);
            }
        }
    }

    @Deprecated
    class setTitle extends VarArgFunction {

        @Override
        public Varargs invoke(Varargs args) {
            if (args.isstring(2) || args.optvalue(2, NIL) instanceof UDSpannableString) {//title
                final CharSequence title = LuaViewUtil.getText(args.optvalue(2, NIL));
                if (title != null) {
                    final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
                    if (actionBar != null) {
                        actionBar.setTitle(title);
                    } else {
                        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar(getGlobals());
                        if (supportActionBar != null) {
                            supportActionBar.setTitle(title);
                        }
                    }
                }
            } else if (args.isuserdata(2)) {//view
                final LuaValue titleViewValue = args.optvalue(2, null);
                if (titleViewValue instanceof UDView) {
                    final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
                    if (actionBar != null) {
                        final View view = ((UDView) titleViewValue).getView();
                        if (view != null) {
                            view.setTag(Constants.RES_LV_TAG, titleViewValue);
                        }
                        actionBar.setDisplayShowCustomEnabled(true);
                        actionBar.setCustomView(LuaViewUtil.removeFromParent(view));
                    } else {
                        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar(getGlobals());
                        if (supportActionBar != null) {
                            final View view = ((UDView) titleViewValue).getView();
                            if (view != null) {
                                view.setTag(Constants.RES_LV_TAG, titleViewValue);
                            }
                            supportActionBar.setDisplayShowCustomEnabled(true);
                            supportActionBar.setCustomView(LuaViewUtil.removeFromParent(view));
                            ((Toolbar)supportActionBar.getCustomView().getParent()).setPadding(0,0,0,0);
                            ((Toolbar)supportActionBar.getCustomView().getParent()).setContentInsetsAbsolute(0,0);
                        }
                    }
                }
            }
            return UDNavigation.this;
        }
    }

    @Deprecated
    class getTitle extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
            if (actionBar != null) {
                final CharSequence title = actionBar.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    return valueOf(title.toString());
                } else {
                    final View view = actionBar.getCustomView();
                    if (view != null) {
                        final Object tag = view.getTag(Constants.RES_LV_TAG);
                        return tag instanceof LuaValue ? (LuaValue) tag : NIL;
                    }
                }
            } else {
                final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar(getGlobals());
                if (supportActionBar != null) {
                    final CharSequence title = supportActionBar.getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        return valueOf(title.toString());
                    } else {
                        final View view = supportActionBar.getCustomView();
                        if (view != null) {
                            final Object tag = view.getTag(Constants.RES_LV_TAG);
                            return tag instanceof LuaValue ? (LuaValue) tag : NIL;
                        }
                    }
                }
            }
            return NIL;
        }
    }


    /**
     * 背景
     */
    class background extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            if (args.narg() > 1) {
                return new UDNavigation.setBackground().invoke(args);
            } else {
                return new UDNavigation.getBackground().invoke(args);
            }
        }
    }

    @Deprecated
    class setBackground extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            if (args.isstring(2)) {
                ImageUtil.fetch(getContext(), getLuaResourceFinder(), args.optjstring(2, null), new DrawableLoadCallback() {
                    @Override
                    public void onLoadResult(Drawable drawable) {
                        setupActionBarDrawable(drawable);
                    }
                });
            } else if (args.isuserdata(2)) {//view
                final LuaValue data = args.optvalue(2, null);
                if (data instanceof UDImageView) {
                    final ImageView imageView = (ImageView) LuaViewUtil.removeFromParent(((UDImageView) data).getView());
                    if (imageView instanceof BaseImageView) {//TODO ActionBar支持gif
                        ImageUtil.fetch(getContext(), getLuaResourceFinder(), ((BaseImageView) imageView).getUrl(), new DrawableLoadCallback() {
                            @Override
                            public void onLoadResult(Drawable drawable) {
                                setupActionBarDrawable(drawable);
                            }
                        });
                    }
                } else if (data instanceof UDBitmap) {
                    setupActionBarDrawable(((UDBitmap) data).createDrawable());
                }
            }
            return UDNavigation.this;
        }

        private void setupActionBarDrawable(Drawable drawable) {
            if (drawable != null) {
                final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
                if (actionBar != null) {
                    actionBar.setBackgroundDrawable(drawable);
                }
            }
        }
    }

    @Deprecated
    class getBackground extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            //TODO
            return UDNavigation.this;
        }
    }

    /**
     * 左按钮
     */
    class left extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            if (args.narg() > 1) {
                return new UDNavigation.setLeft().invoke(args);
            } else {
                return new UDNavigation.getLeft().invoke(args);
            }
        }
    }

    class setLeft extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
            if (actionBar != null) {
                final boolean showBack = args.optboolean(2, true);
                actionBar.setDisplayHomeAsUpEnabled(showBack);
            }
            return UDNavigation.this;
        }
    }

    class getLeft extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            return UDNavigation.this;
        }
    }

    /**
     * 右按钮
     */
    class right extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            if (args.narg() > 1) {
                return new UDNavigation.setRight().invoke(args);
            } else {
                return new UDNavigation.getRight().invoke(args);
            }
        }
    }

    class setRight extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            final ActionBar actionBar = LuaViewUtil.getActionBar(getGlobals());
            if (actionBar != null) {
            }
            return UDNavigation.this;
        }
    }

    class getRight extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs args) {
            return UDNavigation.this;
        }
    }

    public static android.support.v7.app.ActionBar getSupportActionBar(Globals globals) {
        if (globals != null && globals.getContext() instanceof AppCompatActivity) {
            return ((AppCompatActivity) (globals.getContext())).getSupportActionBar();
        }
        return null;
    }
}

