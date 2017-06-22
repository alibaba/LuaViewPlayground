--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/4/5
--

local meta = object:new()

function meta:onCreate(args)
    self.titles = pica:getInstance():render("title.xml")
    Navigation:title(self.titles["container"])
    self.titles["left"]:text("GitHub详情")

    self.web = WebView()
    self.web:frame(0, 0, sys.contW, sys.contH)
    self.web:loadUrl(args.url)
end

return meta

