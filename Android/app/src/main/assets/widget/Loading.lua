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
    self.titles["left"]:text("Loading.lua")

    self.views = pica:getInstance():render("widget/loading.xml")
    self.loading = self.views["loading"]
    self.loading:start()
end

return meta

