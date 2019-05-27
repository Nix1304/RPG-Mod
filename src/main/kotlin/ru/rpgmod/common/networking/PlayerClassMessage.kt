package ru.rpgmod.common.networking

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.rpgmod.common.capabilities.PlayerClassProvider

class PlayerClassMessage(var name: String): IMessage {
    constructor(): this("")

    override fun fromBytes(buf: ByteBuf) {
        name = ByteBufUtils.readUTF8String(buf)
    }

    override fun toBytes(buf: ByteBuf) {
        ByteBufUtils.writeUTF8String(buf, name)
    }

    class ClientHandler: IMessageHandler<PlayerClassMessage, IMessage> {
        override fun onMessage(message: PlayerClassMessage, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player ?: return null
            if(player.hasCapability(PlayerClassProvider.playerCap, null)) {
                val cap =  player.getCapability(PlayerClassProvider.playerCap, null)!!
                cap.name = message.name
            }
            return null
        }
    }

    class ServerHandler: IMessageHandler<PlayerClassMessage, IMessage> {
        override fun onMessage(message: PlayerClassMessage, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player
            if(player.hasCapability(PlayerClassProvider.playerCap, null)) {
                val cap =  player.getCapability(PlayerClassProvider.playerCap, null)!!
                cap.name = message.name
            }
            return null
        }
    }
}