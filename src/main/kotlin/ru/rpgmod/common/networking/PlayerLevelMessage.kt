package ru.rpgmod.common.networking

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.rpgmod.common.capabilities.PlayerLevelProvider

class PlayerLevelMessage(private var exp: Int, private var level: Int, private var upgradePoints: Int): IMessage {
    constructor(): this(0, 0, 0)

    override fun fromBytes(buf: ByteBuf) {
        exp = buf.readInt()
        level = buf.readInt()
        upgradePoints = buf.readInt()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(exp)
        buf.writeInt(level)
        buf.writeInt(upgradePoints)
    }

    class ClientHandler: IMessageHandler<PlayerLevelMessage, IMessage> {
        override fun onMessage(message: PlayerLevelMessage, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player ?: return null
            if(player.hasCapability(PlayerLevelProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerLevelProvider.playerCap, null)!!
                cap.exp = message.exp
                cap.level = message.level
                cap.upgradePoints = message.upgradePoints
            }
            return null
        }
    }

    class ServerHandler: IMessageHandler<PlayerLevelMessage, IMessage> {
        override fun onMessage(message: PlayerLevelMessage, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player ?: return null
            if(player.hasCapability(PlayerLevelProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerLevelProvider.playerCap, null)!!
                cap.exp = message.exp
                cap.level = message.level
                cap.upgradePoints = message.upgradePoints
            }
            return null
        }
    }
}