package ru.rpgmod.client

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ru.rpgmod.common.CommonProxy
import ru.rpgmod.common.main.ItemRegister

class ClientProxy: CommonProxy() {
    override fun preInit(e: FMLPreInitializationEvent) {
        KeyBindsRegister.register()
        super.preInit(e)
    }

    override fun init(e: FMLInitializationEvent) {
        ItemRegister.registerRender()
        super.init(e)
    }

    override fun postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
    }
}