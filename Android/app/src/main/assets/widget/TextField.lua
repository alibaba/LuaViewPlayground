--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/3/30
--

local meta = object:new()

function meta:onCreate(args)
    self.titles = pica:getInstance():render("title.xml")
    Navigation:title(self.titles["container"])
    self.titles["left"]:text("TextField.lua")

    if (sys.android) then
        pica:getInstance():render("widget/textfield_android.xml")
    else
        pica:getInstance():render("widget/textfield_ios.xml")
    end
end

return meta