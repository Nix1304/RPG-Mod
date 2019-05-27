package ru.rpgmod.common

import gloomyfolken.hooklib.minecraft.HookLoader
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer

class RPGHookLoader: HookLoader() {
    override fun getASMTransformerClass(): Array<String> {
        return arrayOf(PrimaryClassTransformer::class.java.name)
    }

    override fun registerHooks() {
        registerHookContainer("ru.rpgmod.common.hooks.Entity")
    }
}