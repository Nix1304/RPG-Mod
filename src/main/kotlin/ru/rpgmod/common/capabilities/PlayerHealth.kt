package ru.rpgmod.common.capabilities

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

interface IPlayerHealth {
    var health: Float
    var maxHealth: Float
}

class PlayerHealth: IPlayerHealth {
    override var health = 0F
    override var maxHealth = 0F
}

class PlayerHealthProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(IPlayerHealth::class)
        @JvmStatic
        lateinit var playerCap: Capability<IPlayerHealth>
    }

    private val instance = playerCap.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean = capability == playerCap

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
            if(capability == playerCap) playerCap.cast<T>(this.instance) else null

    override fun serializeNBT(): NBTBase? = playerCap.storage.writeNBT(playerCap, this.instance, null)

    override fun deserializeNBT(nbt: NBTBase) {
        playerCap.storage.readNBT(playerCap, this.instance, null, nbt)
    }
}

class PlayerHealthStorage: Capability.IStorage<IPlayerHealth> {
    override fun readNBT(capability: Capability<IPlayerHealth>, instance: IPlayerHealth, side: EnumFacing?, nbt: NBTBase) {
        nbt as NBTTagCompound
        instance.health = nbt.getFloat("health")
        instance.maxHealth = nbt.getFloat("max_health")
    }

    override fun writeNBT(capability: Capability<IPlayerHealth>, instance: IPlayerHealth, side: EnumFacing?): NBTBase {
        val nbt = NBTTagCompound()
        nbt.setFloat("health", instance.health)
        nbt.setFloat("max_health", instance.maxHealth)
        return nbt
    }
}