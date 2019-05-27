package ru.rpgmod.common

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ru.rpgmod.common.capabilities.CapabilitiesHandler
import ru.rpgmod.common.main.ClassesRegister
import ru.rpgmod.common.main.ItemRegister
import ru.rpgmod.common.main.SpellsRegister

open class CommonProxy {
    open fun preInit(e: FMLPreInitializationEvent) {
        ItemRegister.register()
        ClassesRegister.register()
        SpellsRegister.register()
    }

    open fun init(e: FMLInitializationEvent) {
        CapabilitiesHandler.register()
    }

    open fun postInit(e: FMLPostInitializationEvent) {

    }
}