package ru.rpgmod.common.networking

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.rpgmod.common.capabilities.PlayerInfoProvider

class PlayerInfoMessage(var int: Int, var str: Int, var abl: Int, var spd: Int): IMessage {
    constructor(): this(0, 0, 0, 0)

    override fun fromBytes(buf: ByteBuf) {
        int = buf.readInt()
        str = buf.readInt()
        abl = buf.readInt()
        spd = buf.readInt()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(int)
        buf.writeInt(str)
        buf.writeInt(abl)
        buf.writeInt(spd)
    }

    class ClientHandler: IMessageHandler<PlayerInfoMessage, IMessage> {
        override fun onMessage(message: PlayerInfoMessage, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player ?: return null
            if(player.hasCapability(PlayerInfoProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerInfoProvider.playerCap, null)!!
                cap.int = message.int
                cap.str = message.str
                cap.abl = message.abl
                cap.spd = message.spd
            }
            return null
        }
    }

    class ServerHandler: IMessageHandler<PlayerInfoMessage, IMessage> {
        override fun onMessage(message: PlayerInfoMessage, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player
            if(player.hasCapability(PlayerInfoProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerInfoProvider.playerCap, null)!!
                cap.int = message.int
                cap.str = message.str
                cap.abl = message.abl
                cap.spd = message.spd
            }
            return null
        }
    }
}