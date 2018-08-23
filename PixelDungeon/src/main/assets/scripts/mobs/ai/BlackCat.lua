---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by mike.
--- DateTime: 23.08.18 22:51
---

local RPD = require "scripts/lib/commonClasses"

local ai = require "scripts/lib/ai"

local edible = {
    RawFish = true
}

return ai.init{

    act       = function(self, ai, me)
        local level = RPD.Dungeon.level
        local heaps = level:allHeaps()

        me:spend(1.)

        local iterator = heaps:iterator()

        while iterator:hasNext() do
            local heap = iterator:next()
            local itemPos = heap.pos

            RPD.glog("%s at %d", heap:peek():getClassName(), itemPos)
            if level.fieldOfView[itemPos] then --visible heap
                RPD.glog("visible", heap:peek():getClassName(), itemPos)
                local item = heap:peek()
                if edible[item:getClassName()] then
                    RPD.glog("Fish")
                    me:doStepTo(itemPos)
                    return
                end
            end
        end
    end,

    gotDamage = function(self, ai, me, src, dmg)

    end
}