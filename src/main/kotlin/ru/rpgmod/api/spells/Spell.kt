package ru.rpgmod.api.spells

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import ru.rpgmod.common.main.Constants.modid
import ru.rpgmod.common.main.RPGCreativeTabs.spellsTab

open class Spell(name: String): Item() {
    var level = 0
    init {
        registryName = ResourceLocation("$modid:$name")
        maxStackSize = 1
        translationKey = name
        creativeTab = spellsTab
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        val nbt = if(stack.hasTagCompound()) stack.tagCompound!! else NBTTagCompound()
        if(nbt.hasKey("lvl")) level = nbt.getInteger("lvl")
        else nbt.setInteger("lvl", 1); level = 1
        tooltip.add("Spell level: $level")
    }
}