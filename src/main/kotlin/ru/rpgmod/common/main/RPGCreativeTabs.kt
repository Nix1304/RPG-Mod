package ru.rpgmod.common.main

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object RPGCreativeTabs {
    val spellsTab = SpellsTab()
}

class SpellsTab: CreativeTabs("spells_tab") {
    override fun createIcon(): ItemStack = ItemStack(ItemRegister.healingSpell)
}