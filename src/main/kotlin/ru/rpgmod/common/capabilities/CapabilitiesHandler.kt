package ru.rpgmod.common.capabilities

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import ru.rpgmod.common.main.Constants.modid

@Mod.EventBusSubscriber(modid = modid)
object CapabilitiesHandler {
    @SubscribeEvent
    @JvmStatic fun attach(event: AttachCapabilitiesEvent<Entity>) {
        if(event.`object` !is EntityPlayer) return
        event.addCapability(ResourceLocation(modid, "cap_mana"), PlayerManaProvider())
        event.addCapability(ResourceLocation(modid, "cap_health"), PlayerHealthProvider())
        event.addCapability(ResourceLocation(modid, "cap_class"), PlayerClassProvider())
        event.addCapability(ResourceLocation(modid, "cap_info"), PlayerInfoProvider())
        event.addCapability(ResourceLocation(modid, "cap_level"), PlayerLevelProvider())
    }

    fun register() {
        CapabilityManager.INSTANCE.register(IPlayerMana::class.java, PlayerManaStorage(), PlayerMana::class.java)
        CapabilityManager.INSTANCE.register(IPlayerHealth::class.java, PlayerHealthStorage(), PlayerHealth::class.java)
        CapabilityManager.INSTANCE.register(IPlayerClass::class.java, PlayerClassStorage(), PlayerClass::class.java)
        CapabilityManager.INSTANCE.register(IPlayerInfo::class.java, PlayerInfoStorage(), PlayerInfo::class.java)
        CapabilityManager.INSTANCE.register(IPlayerLevel::class.java, PlayerLevelStorage(), PlayerLevel::class.java)
    }
}