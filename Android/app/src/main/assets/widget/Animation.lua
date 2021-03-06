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
    self.titles["left"]:text("Animation.lua")

    self.views = pica:getInstance():render("widget/animation.xml")
    self.spirit = self.views["img"]
    self.translateBtn = self.views["translateBtn"]
    self.scaleBtn = self.views["scaleBtn"]
    self.alphaBtn = self.views["alphaBtn"]
    self.resetBtn = self.views["resetBtn"]
    self.allBtn = self.views["allBtn"]

    self:handle()
end

function meta:handle()
    self.translateBtn:onClick(function()
        local translate = Animation():translation(sys.contW/2, sys.contW/2):duration(1)
        translate:with(self.spirit):start()
    end)
    self.scaleBtn:onClick(function()
        local scale = Animation():scale(0.5, 0.5):duration(1)
        scale:with(self.spirit):start()
    end)
    self.alphaBtn:onClick(function()
        local alpha = Animation():alpha(0.3):duration(1)
        alpha:with(self.spirit):start()
    end)
    self.allBtn:onClick(function()
        local translate = Animation():translation(sys.contW/2, sys.contW/2):duration(1)
        local scale = Animation():scale(0.5, 0.5):duration(1)
        local alpha = Animation():alpha(0.3):duration(1)
        alpha:with(self.spirit):start()
        scale:with(self.spirit):start()
        translate:with(self.spirit):start()
    end)
    self.resetBtn:onClick(function()
        self.spirit:translation(0, 0)
        self.spirit:scale(1, 1)
        self.spirit:alpha(1)
    end)
end

return meta
