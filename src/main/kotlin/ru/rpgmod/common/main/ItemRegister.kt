package ru.rpgmod.common.main

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import ru.rpgmod.common.spells.HealingSpell


object ItemRegister {
    val healingSpell: Item = HealingSpell()

    fun register() {
        setRegister(healingSpell)
    }

    @SideOnly(Side.CLIENT)
    fun registerRender() {
        setRender(healingSpell)
    }

    private fun setRegister(item: Item) {
        ForgeRegistries.ITEMS.register(item)
    }

    @SideOnly(Side.CLIENT)
    private fun setRender(item: Item) {
        Minecraft.getMinecraft().renderItem.itemModelMesher.register(item, 0, ModelResourceLocation(item.registryName!!, "inventory"))
    }
}