package ru.rpgmod.common.spells

import com.mojang.realmsclient.gui.ChatFormatting
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import ru.rpgmod.api.spells.Spell
import ru.rpgmod.common.capabilities.PlayerHealthProvider
import ru.rpgmod.common.capabilities.PlayerManaProvider
import ru.rpgmod.common.networking.NetworkHandler
import ru.rpgmod.common.networking.PlayerHealthMessage
import ru.rpgmod.common.networking.PlayerManaMessage

class HealingSpell: Spell("healing_spell") {
    init {
        maxDamage = 101
    }

    override fun onUpdate(stack: ItemStack, world: World, entity: Entity, itemSlot: Int, isSelected: Boolean) {
        if(stack.itemDamage > 0) stack.itemDamage = stack.itemDamage - 1
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if(world.isRemote) return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand))
        val mana = player.getCapability(PlayerManaProvider.playerCap, null)!!
        val health = player.getCapability(PlayerHealthProvider.playerCap, null)!!
        return if(!player.getHeldItem(hand).isItemDamaged && mana.mana >= 20 && health.health < health.maxHealth) {
            if(health.health + 15 >= health.maxHealth) health.health = health.maxHealth else health.health += 15
            mana.mana -= 20

            NetworkHandler.network.sendTo(PlayerManaMessage(mana.mana, mana.maxMana), player as EntityPlayerMP)
            NetworkHandler.network.sendTo(PlayerHealthMessage(health.health, health.maxHealth), player)

            val stack = player.getHeldItem(hand)
            stack.itemDamage = 100
            ActionResult.newResult(EnumActionResult.SUCCESS, stack)
        } else {
            ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand))
        }
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        tooltip.add("Mana cost: ${ChatFormatting.AQUA}20")
        tooltip.add("HP heal: ${ChatFormatting.RED}15")
    }
}