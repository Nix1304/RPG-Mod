package ru.rpgmod.common.networking

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.rpgmod.common.capabilities.PlayerManaProvider

class PlayerManaMessage(var mana: Float, var maxMana: Float): IMessage {
    constructor(): this(0F, 0F)

    override fun fromBytes(buf: ByteBuf) {
        mana = buf.readFloat()
        maxMana = buf.readFloat()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeFloat(mana)
        buf.writeFloat(maxMana)
    }

    class ClientHandler: IMessageHandler<PlayerManaMessage, IMessage> {
        override fun onMessage(message: PlayerManaMessage, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player ?: return null
            if(player.hasCapability(PlayerManaProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerManaProvider.playerCap, null)!!
                cap.mana = message.mana
                cap.maxMana = message.maxMana
            }
            return null
        }
    }

    class ServerHandler: IMessageHandler<PlayerManaMessage, IMessage> {
        override fun onMessage(message: PlayerManaMessage, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player
            if(player.hasCapability(PlayerManaProvider.playerCap, null)) {
                val cap = player.getCapability(PlayerManaProvider.playerCap, null)!!
                cap.mana = message.mana
                cap.maxMana = message.maxMana
            }
            return null
        }
    }
}