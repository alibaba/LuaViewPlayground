--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/6/16
--

local meta = object:new()

function meta:onCreate(args)
    self.titles = pica:getInstance():render("title.xml")
    Navigation:title(self.titles["container"])
    self.titles["left"]:text("Login.lua")

    self.views = pica:getInstance():render("widget/Login.xml")
    self.login = self.views["login"]
    self.username = self.views["username"]
    self.password = self.views["password"]

    self:handle()
end

function meta:handle()
    self.login:onClick(function()
        Toast("UserName: " .. self.username:text() .. " Password: " .. self.password:text())
    end)
end

return meta

