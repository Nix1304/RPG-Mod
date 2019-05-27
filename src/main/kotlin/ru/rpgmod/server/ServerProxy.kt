package ru.rpgmod.proxy

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ru.rpgmod.common.CommonProxy

class ServerProxy: CommonProxy() {
    override fun preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
    }

    override fun init(e: FMLInitializationEvent) {
        super.init(e)
    }

    override fun postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
    }
}