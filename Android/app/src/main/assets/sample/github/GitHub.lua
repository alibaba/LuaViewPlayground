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
    self.titles["left"]:text("GitHub.lua")

    if (sys.android) then
        self.views = pica:getInstance():render("sample/github/github_android.xml")
    else
        self.views = pica:getInstance():render("sample/github/github_ios.xml")
    end

    self.search = self.views["go"]
    self.input = self.views["input"]
    self.list = self.views["tableView"]
    self.loading = self.views["loading"]

    self:handle()
end

function meta:handle()
    local isSearching = false
    self.search:onClick(function()
        if (not sys.android) then
            self.input:cancelFocus()
        end

        if (isSearching == true) then
            return
        end

        local content = self.input:text()
        local baseUrl = "https://api.github.com/search/repositories?sort=stars&q="

        self.loading:show()
        isSearching = true

        print("tuoli", "http request start")
        Http():get(baseUrl .. content, function(response)
            isSearching = false
            print("tuoli", "http request end")

            self.loading:hide()

            if (tostring(response:code()) == "200") then
                local jsonData = Json:toTable(tostring(response:data()))

                if (table.getn(jsonData["items"]) == 0) then
                    Toast("No Result")
                else
                    self.list:initParams({
                        Section = {
                            SectionCount = function()
                                return 1
                            end,
                            RowCount = function(section)
                                return table.getn(jsonData["items"])
                            end
                        },
                        Cell = {
                            Id = function(section, row)
                                return "ItemCell"
                            end,
                            ItemCell = {
                                Size = function(section, row)
                                    return sys.contW, sys.contH/3
                                end,
                                Init = function(cell, section, row)
                                    cell.objs = pica:getInstance():render("sample/github/github_cell.xml")
                                end,
                                Layout = function(cell, section, row)
                                    cell.objs["name"]:text(jsonData["items"][row]["full_name"])
                                    if (jsonData["items"][row]["description"]) then
                                        cell.objs["description"]:text(jsonData["items"][row]["description"])
                                    else
                                        cell.objs["description"]:text("no descriptions")
                                    end
                                    cell.objs["profile"]:image(jsonData["items"][row]["owner"]["avatar_url"])
                                    cell.objs["profile"]:scaleType(ScaleType.FIT_CENTER)
                                    cell.objs["stars"]:text("Stars: " .. jsonData["items"][row]["stargazers_count"])

                                    cell.objs["item"]:onClick(function()
                                        Router:require({page="sample.github.GitHub_detail", url=jsonData["items"][row]["html_url"]})
                                    end)
                                end
                            }
                        }
                    })
                end
            else
                Toast("Request Error")
            end
        end)
    end)
end

return meta