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
    self.titles["left"]:text("Sections.lua")

    self.list = CollectionView()

    self:handle()
end

function meta:handle()
    self.list:initParams({
        Section = {
            SectionCount = function()
                return 10
            end,
            RowCount = function(section)
                return 20
            end
        },
        Cell = {
            Id = function(section, row)
                if (row == 1) then
                    return "SectionCell", Pinned.YES
                else
                    return "RowCell"
                end
            end,
            SectionCell = {
                Size = function(section, row)
                    return sys.contW, sys.contH/10
                end,
                Init = function(cell, section, row)
                    cell.window:frame(0, 0, sys.contW, sys.contH/10)
                    cell.window:flexCss("flex-direction: row")
                    cell.window:backgroundColor(0x004B97)

                    local label = Label()
                    label:flexCss("margin: 15, flex: 1")

                    cell.label = label
                    cell.window:flexChildren(cell.label)
                    cell.window:flxLayout(true) -- iOS
                end,
                Layout = function(cell, section, row)
                    local style = StyledString("Section" .. section, { fontSize = 20*sys.scale, fontColor = 0xffffff})
                    cell.label:text(style)
                end
            },
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
                    local style = StyledString("section" .. section .. " row " .. row, { fontSize = 16*sys.scale, fontColor = 0x000000})
                    cell.label:text(style)
                end
            }
        }
    })
    self.list:miniSpacing(2*sys.scale)
end

return meta

