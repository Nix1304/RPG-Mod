package ru.rpgmod.common.capabilities

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

interface IPlayerInfo {
    var int: Int
    var str: Int
    var abl: Int
    var spd: Int
}

class PlayerInfo: IPlayerInfo {
    override var int = 0
    override var str = 0
    override var abl = 0
    override var spd = 0
}

class PlayerInfoProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(IPlayerInfo::class)
        @JvmStatic
        lateinit var playerCap: Capability<IPlayerInfo>
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

class PlayerInfoStorage: Capability.IStorage<IPlayerInfo> {
    override fun readNBT(capability: Capability<IPlayerInfo>, instance: IPlayerInfo, side: EnumFacing?, nbt: NBTBase) {
        nbt as NBTTagCompound
        instance.int = nbt.getInteger("int")
        instance.str = nbt.getInteger("str")
        instance.abl = nbt.getInteger("abl")
        instance.spd = nbt.getInteger("spd")
    }

    override fun writeNBT(capability: Capability<IPlayerInfo>, instance: IPlayerInfo, side: EnumFacing?): NBTBase {
        val nbt = NBTTagCompound()
        nbt.setInteger("int", instance.int)
        nbt.setInteger("str", instance.str)
        nbt.setInteger("abl", instance.abl)
        nbt.setInteger("spd", instance.spd)
        return nbt
    }
}