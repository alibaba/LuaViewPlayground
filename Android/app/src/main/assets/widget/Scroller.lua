--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/5/18
--

local meta = object:new()

function meta:onCreate(args)
    self.titles = pica:getInstance():render("title.xml")
    Navigation:title(self.titles["container"])
    self.titles["left"]:text("Scroller.lua")

    self.scroller = RefreshScroller()
    self.scroller:frame(0, 0, sys.contW, sys.contH)

    self:handle()
end

function meta:handle()
    self.header = View()
    self.header:backgroundColor(0x00ff00)
    self.header:removeFromSuper()
    self.scroller:addHeaderView(self.header,sys.contW,sys.contH*0.3)

    self.btns = {}
    self.pinned = HScrollView()
    self.pinned:backgroundColor(0xffff00)
    for i = 0, 9, 1 do
        local btn = Button()
        btn:frame(sys.contW/4*i, 0, sys.contW/4, sys.contH*0.1)
        btn:title("btn " .. i+1)
        if (i == 0) then
            btn:backgroundColor(0xEB3131)
        else
            btn:backgroundColor(0x004B97)
        end
        self.pinned:addView(btn)
        self.btns[i] = btn
    end

    self.pinned:removeFromSuper()
    self.scroller:addPinnedView(self.pinned,sys.contW,sys.contH*0.1)

    self.pager = PagerView({
        PageCount = 10,
        Pages = {
            Init = function(page, pos)
                page.list = CollectionView({
                    Section = {
                        SectionCount = function()
                            return 1
                        end,
                        RowCount = function(section)
                            return 20
                        end
                    },
                    Cell = {
                        Id = function(section, row)
                            return "RowCell"
                        end,
                        RowCell = {
                            Size = function(section, row)
                                return sys.contW, sys.contH/10
                            end,
                            Init = function(cell, section, row)
                                cell.window:frame(0, 0, sys.contW, sys.contH/10)
                                cell.window:flexCss("flex-direction: row")
                                cell.window:backgroundColor(0xffffff)

                                local label = Label()
                                label:flexCss("margin: 15, flex: 1")

                                cell.label = label
                                cell.window:flexChildren(cell.label)
                                cell.window:flxLayout(true) -- iOS
                            end,
                            Layout = function(cell, section, row)
                                local style = StyledString(pos .. " row " .. row, { fontSize = 16*sys.scale, fontColor = 0x000000})
                                cell.label:text(style)
                            end
                        }
                    }
                })
                page.list:frame(0,0,sys.contW,sys.contH*0.9)
            end,
            Layout = function(page, pos)
            end
        },
        Callback = {
            Scrolling = function(pageIndex, percent, offset)
            end,
            ScrollEnd = function(pageIndex)
                self.btns[pageIndex-1]:backgroundColor(0xEB3131)
                for _k, _v in pairs(self.btns) do
                    if (_v ~= self.btns[pageIndex-1]) then
                        _v:backgroundColor(0x004B97)
                    end
                end
                local x, y, w, h = self.btns[pageIndex-1]:frame()
                local dx = (sys.contW - w)/2
                self.pinned:offset(x-dx, 0, true)
            end
        }
    })
    self.pager:removeFromSuper()
    self.scroller:addContentView(self.pager,sys.contW, sys.contH*0.9)

    for j = 0, 9, 1 do
        self.btns[j]:onClick(function()
            self.btns[j]:backgroundColor(0xEB3131)
            for _k, _v in pairs(self.btns) do
                if (_v ~= self.btns[j]) then
                    _v:backgroundColor(0x004B97)
                end
            end
            local x, y, w, h = self.btns[j]:frame()
            local dx = (sys.contW - w)/2
            self.pinned:offset(x-dx, 0, true)

            self.pager:currentPage(j+1)
        end)
    end
end

return meta

