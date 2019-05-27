package ru.rpgmod.api

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import ru.rpgmod.common.main.Constants.modid

open class ManaHealer(name: String, private val manaHeal: Int): ItemFood(0, false) {
    init {
        this.setAlwaysEdible()
        registryName = ResourceLocation("$modid:$name")
        translationKey = name
    }

    override fun onItemUseFinish(stack: ItemStack, world: World, entityLiving: EntityLivingBase): ItemStack {
        if(entityLiving is EntityPlayer) {
            /*val cap = entityLiving.getCapability(PlayerInfoProvider.playerCap, null)!!
            if(cap.mana < cap.maxMana) {
                cap.mana = if(cap.mana + manaHeal >= cap.maxMana) cap.maxMana else cap.mana + manaHeal
                stack.shrink(1)
            }*/
        }
        return stack
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("Heal mana: ${TextFormatting.AQUA}$manaHeal")
    }
}