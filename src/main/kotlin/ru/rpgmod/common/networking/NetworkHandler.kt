package ru.rpgmod.common.networking

import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side
import ru.rpgmod.common.main.Constants.modid

object NetworkHandler {
    lateinit var network: SimpleNetworkWrapper
    private var id = 0

    fun init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(modid)

        network.registerMessage(PlayerManaMessage.ClientHandler::class.java, PlayerManaMessage::class.java, id++, Side.CLIENT)
        network.registerMessage(PlayerManaMessage.ServerHandler::class.java, PlayerManaMessage::class.java, id++, Side.SERVER)

        network.registerMessage(PlayerHealthMessage.ClientHandler::class.java, PlayerHealthMessage::class.java, id++, Side.CLIENT)
        network.registerMessage(PlayerHealthMessage.ServerHandler::class.java, PlayerHealthMessage::class.java, id++, Side.SERVER)

        network.registerMessage(PlayerClassMessage.ClientHandler::class.java, PlayerClassMessage::class.java, id++, Side.CLIENT)
        network.registerMessage(PlayerClassMessage.ServerHandler::class.java, PlayerClassMessage::class.java, id++, Side.SERVER)

        network.registerMessage(PlayerInfoMessage.ClientHandler::class.java, PlayerInfoMessage::class.java, id++, Side.CLIENT)
        network.registerMessage(PlayerInfoMessage.ServerHandler::class.java, PlayerInfoMessage::class.java, id++, Side.SERVER)

        network.registerMessage(PlayerLevelMessage.ClientHandler::class.java, PlayerLevelMessage::class.java, id++, Side.CLIENT)
        network.registerMessage(PlayerLevelMessage.ServerHandler::class.java, PlayerLevelMessage::class.java, id++, Side.SERVER)
    }
}