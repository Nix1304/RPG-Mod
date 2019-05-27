package ru.rpgmod.common.capabilities

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

interface IPlayerMana {
    var mana: Float
    var maxMana: Float
}

class PlayerMana : IPlayerMana {
    override var mana = 0F
    override var maxMana = 0F
}

class PlayerManaProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(IPlayerMana::class)
        @JvmStatic lateinit var playerCap: Capability<IPlayerMana>
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

class PlayerManaStorage: Capability.IStorage<IPlayerMana> {
    override fun readNBT(capability: Capability<IPlayerMana>, instance: IPlayerMana, side: EnumFacing?, nbt: NBTBase) {
        nbt as NBTTagCompound
        instance.mana = nbt.getFloat("mana")
        instance.maxMana = nbt.getFloat("max_mana")
    }

    override fun writeNBT(capability: Capability<IPlayerMana>, instance: IPlayerMana, side: EnumFacing?): NBTBase {
        val nbt = NBTTagCompound()
        nbt.setFloat("mana", instance.mana)
        nbt.setFloat("max_mana", instance.maxMana)
        return nbt
    }
}
