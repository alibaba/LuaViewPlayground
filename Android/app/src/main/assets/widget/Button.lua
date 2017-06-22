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
    self.titles["left"]:text("Button.lua")

    self.views = pica:getInstance():render("widget/button.xml")
    self.button1 = self.views["button1"]
    self.button2 = self.views["button2"]
    self.button3 = self.views["button3"]
    self.button4 = self.views["button4"]
    self.button5 = self.views["button5"]
    self.button6 = self.views["button6"]

    self:handle()
end

function meta:handle()
    self.button1:onClick(function()
        local content = self.button1:text()
        Toast(content .. " Clicked")
    end)
    self.button2:onClick(function()
        local content = self.button2:text()
        Toast(content .. " Clicked")
    end)
    self.button3:onClick(function()
        local content = self.button3:text()
        Toast(content .. " Clicked")
    end)
    self.button4:onClick(function()
        local content = self.button4:text()
        Toast(content .. " Clicked")
    end)
    self.button5:onClick(function()
        local content = self.button5:text()
        Toast(content .. " Clicked")
    end)
    self.button6:onClick(function()
        local content = self.button6:text()
        Toast(content .. " Clicked")
    end)
end

return meta
