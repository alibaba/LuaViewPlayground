--
-- Copyright 2017 Alibaba Group
-- License: MIT
-- Website: https://alibaba.github.io/LuaViewSDK
-- User: tuoli
-- Date: 17/6/21
--

local meta = object:new()

function meta:onCreate(args)
    self.titles = pica:getInstance():render("title.xml")
    self.titles["left"]:text("TabScroller.lua")
    Navigation:title(self.titles["container"])

    self:handle()
end

function meta:handle()
    self.btns = {}
    self.pinned = HScrollView()
    self.pinned:frame(0, 0, sys.contW, sys.contH*0.1)
    for i = 0, 9, 1 do
        local btn = Button()
        btn:frame(sys.contW/4*i, 0, sys.contW/4, sys.contH*0.09)
        btn:title("btn " .. i+1)
        btn:backgroundColor(0x004B97)
        self.pinned:addView(btn)
        self.btns[i] = btn

        self.btns[i]:onClick(function()
            local x, y, w, h = self.btns[i]:frame()
            local dx = (sys.contW - w)/2
            self.pinned:offset(x-dx, 0, true)

            self.pager:currentPage(i+1)     -- 触发ScrollEnd

--            local translate = Animation():translation((i)*w, 0):duration(0.3)
--            translate:with(self.animator):start()
        end)
    end

    self.animator = View()
    self.animator:backgroundColor(0xEB3131)
    self.animator:frame(0, sys.contH*0.09, sys.contW/4, sys.contH*0.01)
    self.pinned:addView(self.animator)

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
                local x, y, w, h = self.btns[pageIndex-1]:frame()
                local dx = (sys.contW - w)/2
                self.pinned:offset(x-dx, 0, true)

                local translate = Animation():translation((pageIndex-1)*w, 0):duration(0.3)
                translate:with(self.animator):start()
            end
        }
    })
    self.pager:frame(0, sys.contH*0.1, sys.contW, sys.contH*0.9)

end

return meta

