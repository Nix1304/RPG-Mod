package ru.rpgmod.client.events

import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import org.lwjgl.opengl.GL11.*
import ru.rpgmod.common.capabilities.PlayerClassProvider
import ru.rpgmod.common.capabilities.PlayerHealthProvider
import ru.rpgmod.common.capabilities.PlayerLevelProvider
import ru.rpgmod.common.capabilities.PlayerManaProvider
import ru.rpgmod.common.main.Constants.modid
import ru.rpgmod.utils.DrawUtils

@Mod.EventBusSubscriber(Side.CLIENT, modid = modid)
object GuiEvents {
    @SubscribeEvent
    @JvmStatic
    fun removeElements(event: RenderGameOverlayEvent.Pre) {
        when(event.type) {
            RenderGameOverlayEvent.ElementType.HEALTH,
            RenderGameOverlayEvent.ElementType.FOOD,
            RenderGameOverlayEvent.ElementType.EXPERIENCE -> event.isCanceled = true
            else -> {}
        }
    }

    @SubscribeEvent
    @JvmStatic
    fun changeGameOverlay(event: RenderGameOverlayEvent.Pre) {
        val mc = Minecraft.getMinecraft()
        if(event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS && mc.currentScreen == null) {
            glPushMatrix()
            glEnable(GL_CULL_FACE)
            glEnable(GL_BLEND)
            mc.textureManager.bindTexture(ResourceLocation(modid, "textures/gui/stats.png"))
            DrawUtils.drawTexturedModalRect(0, 0, 0, 0, 96, 72)
            glDisable(GL_CULL_FACE)
            glDisable(GL_BLEND)
            glPopMatrix()

            val mana = mc.player.getCapability(PlayerManaProvider.playerCap, null)!!
            val health = mc.player.getCapability(PlayerHealthProvider.playerCap, null)!!
            val playerClass = mc.player.getCapability(PlayerClassProvider.playerCap, null)!!.name
            val level = mc.player.getCapability(PlayerLevelProvider.playerCap, null)!!

            val mp = mana.mana * (50 / if(mana.maxMana != 0F) mana.maxMana else 1F)
            val hp = health.health * (50 / if(health.maxHealth != 0F) health.maxHealth else 1F)
            val exp = level.exp * (30F / 250)

            DrawUtils.drawTexturedModalRect(7, 7, 103, 7, hp.toInt(), 10) // HP
            DrawUtils.drawTexturedModalRect(7, 22, 103, 22, mp.toInt(), 10) // MP
            DrawUtils.drawTexturedModalRect(7, 37, 103, 37, exp.toInt(), 10) // EXP

            mc.fontRenderer.drawString("${String.format("%.1f", health.health)}/${health.maxHealth}", 60, 9, 0xFFFFFF) // HP
            mc.fontRenderer.drawString("${String.format("%.1f", mana.mana)}/${mana.maxMana}", 60, 24, 0xFFFFFF) // MP
            mc.fontRenderer.drawString("${level.level} (${level.exp} exp)", 42, 39, 0xFFFFFF) // EXP
            mc.fontRenderer.drawString("${I18n.format("rpg.gui.class")}: $playerClass", 7, 50, 0xFFFFFF) // CLASS
        }
    }
}