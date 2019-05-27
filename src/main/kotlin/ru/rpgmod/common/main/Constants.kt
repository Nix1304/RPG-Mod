package ru.rpgmod.common.main

import net.minecraftforge.fml.common.SidedProxy
import org.apache.logging.log4j.Logger
import ru.rpgmod.common.CommonProxy

object Constants {
    const val name = "RPG Mod"
    const val modid = "rpgmod"
    const val version = "0.0.1"

    @JvmStatic
    lateinit var logger: Logger

    @JvmStatic
    @SidedProxy(clientSide = "ru.rpgmod.client.ClientProxy", serverSide = "ru.rpgmod.proxy.ServerProxy")
    lateinit var proxy: CommonProxy
}