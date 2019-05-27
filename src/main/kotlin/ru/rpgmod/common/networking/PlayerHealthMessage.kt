package ru.rpgmod.common.networking

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.rpgmod.common.capabilities.PlayerHealthProvider

class PlayerHealthMessage(var health: Float, var maxHealth: Float): IMessage {
    constructor(): this(0F, 0F)

    override fun fromBytes(buf: ByteBuf) {
        health = buf.readFloat()
        maxHealth = buf.readFloat()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeFloat(health)
        buf.writeFloat(maxHealth)
    }

    class ClientHandler: IMessageHandler<PlayerHealthMessage, IMessage> {
        override fun onMessage(message: PlayerHealthMessage, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player ?: return null
            if(player.hasCapability(PlayerHealthProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerHealthProvider.playerCap, null)!!
                cap.health = message.health
                cap.maxHealth = message.maxHealth
            }
            return null
        }
    }

    class ServerHandler: IMessageHandler<PlayerHealthMessage, IMessage> {
        override fun onMessage(message: PlayerHealthMessage, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player
            if(player.hasCapability(PlayerHealthProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerHealthProvider.playerCap, null)!!
                cap.health = message.health
                cap.maxHealth = message.maxHealth
            }
            return null
        }
    }
}