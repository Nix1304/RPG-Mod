package ru.rpgmod.common.capabilities

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

interface IPlayerLevel {
    var exp: Int
    var level: Int
    var upgradePoints: Int
}

class PlayerLevel: IPlayerLevel {
    override var exp = 0
    override var level = 0
    override var upgradePoints = 0
}

class PlayerLevelProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(IPlayerLevel::class)
        @JvmStatic lateinit var playerCap: Capability<IPlayerLevel>
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

class PlayerLevelStorage: Capability.IStorage<IPlayerLevel> {
    override fun readNBT(capability: Capability<IPlayerLevel>, instance: IPlayerLevel, side: EnumFacing?, nbt: NBTBase) {
        nbt as NBTTagCompound
        instance.exp = nbt.getInteger("exp")
        instance.level = nbt.getInteger("level")
        instance.upgradePoints = nbt.getInteger("upgrade_points")
    }

    override fun writeNBT(capability: Capability<IPlayerLevel>, instance: IPlayerLevel, side: EnumFacing?): NBTBase {
        val nbt = NBTTagCompound()
        nbt.setInteger("exp", instance.exp)
        nbt.setInteger("level", instance.level)
        nbt.setInteger("upgrade_points", instance.upgradePoints)
        return nbt
    }
}