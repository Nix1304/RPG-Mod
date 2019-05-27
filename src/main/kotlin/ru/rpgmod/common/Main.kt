package ru.rpgmod.common

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModMetadata
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ru.rpgmod.common.main.Constants.logger
import ru.rpgmod.common.main.Constants.modid
import ru.rpgmod.common.main.Constants.name
import ru.rpgmod.common.main.Constants.proxy
import ru.rpgmod.common.main.Constants.version
import ru.rpgmod.common.main.RPGModConfig
import ru.rpgmod.common.networking.NetworkHandler

@Mod(modid = modid, name = name, version = version)
class Main {
    companion object {
        @Mod.Instance
        lateinit var instance: Main
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        modMetadata(event.modMetadata)
        logger = event.modLog
        logger.info("Pre-init stage start")
        RPGModConfig.loadConfig(event.suggestedConfigurationFile)
        proxy.preInit(event)
        logger.info("Pre-init stage end")
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        NetworkHandler.init()
        proxy.init(event)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
    }

    private fun modMetadata(meta: ModMetadata) {
        meta.autogenerated = false
        meta.modId = modid
        meta.version = version
        meta.name = name
        val authorList = ArrayList<String>()
        authorList.add("Nix13")
        meta.authorList = authorList
    }
}