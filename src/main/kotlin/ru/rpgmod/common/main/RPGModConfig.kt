package ru.rpgmod.common.main

import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL
import net.minecraftforge.common.config.Configuration.CATEGORY_SPLITTER
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import ru.rpgmod.common.main.Constants.modid
import java.io.File

object RPGModConfig {
    private lateinit var config: Configuration

    private const val categoryBase = CATEGORY_GENERAL + CATEGORY_SPLITTER + "base"

    //Other
    var deathManaSave = true

    fun loadConfig(suggestedConfigurationFile: File) {
        config = Configuration(suggestedConfigurationFile)

        config.load()
        config.addCustomCategoryComment(categoryBase, "RPG Mod: Base settings.")

        syncConfig()
        FMLCommonHandler.instance().bus().register(RPGChangeListener())
    }

    fun syncConfig() {
        deathManaSave = loadProp(categoryBase, "death_mana_save", deathManaSave, false, "If false, after death mana will be set to max. Def. false")

        if(config.hasChanged())
            config.save()
    }

    private fun loadProp(category: String, propName: String, default_: Int, restart: Boolean, desc: String): Int {
        val prop = config.get(category, propName, default_)
        prop.comment = desc
        prop.setRequiresMcRestart(restart)
        return prop.getInt(default_)
    }

    private fun loadProp(category: String, propName: String, default_: Double, restart: Boolean, desc: String): Double {
        val prop = config.get(category, propName, default_)
        prop.comment = desc
        prop.setRequiresMcRestart(restart)
        return prop.getDouble(default_)
    }

    private fun loadProp(category: String, propName: String, default_: Boolean, restart: Boolean, desc: String): Boolean {
        val prop = config.get(category, propName, default_)
        prop.comment = desc
        prop.setRequiresMcRestart(restart)
        return prop.getBoolean(default_)
    }

    private class RPGChangeListener {
        @SubscribeEvent
        fun onConfigChanged(e: ConfigChangedEvent.OnConfigChangedEvent) {
            if(e.modID == modid) syncConfig()
        }
    }
}