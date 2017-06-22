--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/3/30
--

require("kit.object")
require("kit.util")
require("kit.sys")
require("kit.pica")

function main(args)
    if (args and type(args) == "table") then

        if (args.packagePath) then
            package.path = package.path .. ";" .. args.packagePath .. "?.lua"
        end

        if (args.page) then
            g_page = require(args.page):new()
        else
            g_page = require("Main"):new()
        end

        if (g_page.onCreate == nil) then
            Toast("请实现?.lua页面的meta:onCreate()方法!")
        else
            g_page:onCreate(args)
        end
    else
        Toast("页面间透传参数错误!")
    end
end

main(Router:args())
