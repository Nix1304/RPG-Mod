package ru.rpgmod.server.events

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import ru.rpgmod.common.capabilities.*
import ru.rpgmod.common.main.Constants
import ru.rpgmod.common.main.RPGModConfig
import ru.rpgmod.common.networking.*

@Mod.EventBusSubscriber(modid = Constants.modid)
object EventHandler {
    @SubscribeEvent
    @JvmStatic fun playerDamage(event: LivingHurtEvent) {
        if(event.entity !is EntityPlayer) return
        event.isCanceled = true
        val cap = event.entity.getCapability(PlayerHealthProvider.playerCap, null)!!
        if(cap.health - (event.amount * 3).toInt() <= 0) (event.entity as EntityPlayer).health = 0F
        cap.health = if(cap.health - (event.amount *3).toInt() > 0) cap.health - (event.amount * 3).toInt() else 0F
        NetworkHandler.network.sendTo(PlayerHealthMessage(cap.health, cap.maxHealth), event.entity as EntityPlayerMP)
    }

    @SubscribeEvent
    @JvmStatic fun copyPlayerInfo(event: PlayerEvent.Clone) {
        val oldMana = event.original.getCapability(PlayerManaProvider.playerCap, null)!!
        val newMana = event.entity.getCapability(PlayerManaProvider.playerCap, null)!!

        if(RPGModConfig.deathManaSave) {
            newMana.mana = oldMana.mana
            oldMana.maxMana = oldMana.maxMana
        } else {
            newMana.mana = oldMana.maxMana
            oldMana.maxMana = oldMana.maxMana
        }

        val oldHealth = event.original.getCapability(PlayerHealthProvider.playerCap, null)!!.maxHealth
        val newHealth = event.entity.getCapability(PlayerHealthProvider.playerCap, null)!!
        newHealth.maxHealth = oldHealth
        newHealth.health = oldHealth

        event.entity.getCapability(PlayerClassProvider.playerCap, null)!!.name = event.original.getCapability(PlayerClassProvider.playerCap, null)!!.name

        val oldPlayerInfo = event.original.getCapability(PlayerInfoProvider.playerCap, null)!!
        val newPlayerInfo = event.entity.getCapability(PlayerInfoProvider.playerCap, null)!!
        newPlayerInfo.int = oldPlayerInfo.int
        newPlayerInfo.spd = oldPlayerInfo.spd
        newPlayerInfo.abl = oldPlayerInfo.abl
        newPlayerInfo.spd = oldPlayerInfo.spd

        val oldLevel = event.original.getCapability(PlayerLevelProvider.playerCap, null)!!
        val newLevel = event.entity.getCapability(PlayerLevelProvider.playerCap, null)!!
        newLevel.upgradePoints = oldLevel.upgradePoints
        newLevel.level = oldLevel.level
        newLevel.exp = oldLevel.exp
    }

    @SubscribeEvent
    @JvmStatic fun onPlayerRespawn(event: net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent) {
        val manaCap = event.player.getCapability(PlayerManaProvider.playerCap, null)!!
        NetworkHandler.network.sendTo(PlayerManaMessage(manaCap.mana, manaCap.maxMana), event.player as EntityPlayerMP)
    }

    @SubscribeEvent
    @JvmStatic fun onPlayerJoinWorldEvent(event: net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent) {
        val manaCap = event.player.getCapability(PlayerManaProvider.playerCap, null)!!
        val healthCap = event.player.getCapability(PlayerHealthProvider.playerCap, null)!!
        val classCap = event.player.getCapability(PlayerClassProvider.playerCap, null)!!
        val infoCap = event.player.getCapability(PlayerInfoProvider.playerCap, null)!!
        val levelCap = event.player.getCapability(PlayerLevelProvider.playerCap, null)!!

        NetworkHandler.network.sendTo(PlayerManaMessage(manaCap.mana, manaCap.maxMana), event.player as EntityPlayerMP)
        NetworkHandler.network.sendTo(PlayerHealthMessage(healthCap.health, healthCap.maxHealth), event.player as EntityPlayerMP)
        NetworkHandler.network.sendTo(PlayerClassMessage(classCap.name), event.player as EntityPlayerMP)
        NetworkHandler.network.sendTo(PlayerInfoMessage(infoCap.int, infoCap.str, infoCap.abl, infoCap.spd), event.player as EntityPlayerMP)
        NetworkHandler.network.sendTo(PlayerLevelMessage(levelCap.exp, levelCap.level, levelCap.upgradePoints), event.player as EntityPlayerMP)
    }


    @SubscribeEvent
    @JvmStatic fun onEntityDeath(event: LivingDeathEvent) {
        if(event.entity is EntityPlayer || event.source.trueSource !is EntityPlayer) return
        val cap = Minecraft.getMinecraft().player.getCapability(PlayerLevelProvider.playerCap, null)!!
        cap.exp += 5
    }

    var playerTimer = 0

    @SubscribeEvent
    @JvmStatic fun onPlayerTick(event: TickEvent.PlayerTickEvent) {
        if(event.phase == TickEvent.Phase.START && event.side.isClient) return
        event.player.foodStats.foodLevel = 20
        if(playerTimer % 20 == 0 && event.player is EntityPlayerMP) {
            val mana = event.player.getCapability(PlayerManaProvider.playerCap, null)!!
            val info = event.player.getCapability(PlayerInfoProvider.playerCap, null)!!
            if(mana.mana >= mana.maxMana) mana.mana = mana.maxMana
            else {
                mana.mana += info.int / 20F
                NetworkHandler.network.sendTo(PlayerManaMessage(mana.mana, mana.maxMana), event.player as EntityPlayerMP)
            }
            playerTimer = 0
        }
        playerTimer++
    }
}