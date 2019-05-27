package ru.rpgmod.common.hooks

import gloomyfolken.hooklib.asm.Hook
import gloomyfolken.hooklib.asm.ReturnCondition
import net.minecraft.entity.EntityLivingBase

object Entity {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @JvmStatic fun onDeathUpdate(living: EntityLivingBase) {
        ++living.deathTime
        if (living.deathTime == 20)
            living.setDead()
        return
    }
}