package ru.rpgmod.common.capabilities

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

interface IPlayerClass {
    var name: String
}

class PlayerClass: IPlayerClass {
    override var name = ""
}

class PlayerClassProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(IPlayerClass::class)
        @JvmStatic
        lateinit var playerCap: Capability<IPlayerClass>
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

class PlayerClassStorage: Capability.IStorage<IPlayerClass> {
    override fun readNBT(capability: Capability<IPlayerClass>, instance: IPlayerClass, side: EnumFacing?, nbt: NBTBase) {
        nbt as NBTTagCompound
        instance.name = nbt.getString("name")
    }

    override fun writeNBT(capability: Capability<IPlayerClass>, instance: IPlayerClass, side: EnumFacing?): NBTBase {
        val nbt = NBTTagCompound()
        nbt.setString("name", instance.name)
        return nbt
    }
}