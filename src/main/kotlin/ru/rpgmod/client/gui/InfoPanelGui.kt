package ru.rpgmod.client.gui

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.resources.I18n
import ru.rpgmod.common.capabilities.PlayerInfoProvider
import ru.rpgmod.common.capabilities.PlayerLevelProvider
import ru.rpgmod.utils.DrawUtils

class InfoPanelGui: GuiScreen() {
    private lateinit var conInt: GuiButton
    private lateinit var addInt: GuiButton
    private lateinit var conStr: GuiButton
    private lateinit var addStr: GuiButton
    private lateinit var conSpd: GuiButton
    private lateinit var addSpd: GuiButton
    private lateinit var conAbl: GuiButton
    private lateinit var addAbl: GuiButton
    private lateinit var submit: GuiButton

    var int = 0
    var str = 0
    var spd = 0
    var abl = 0
    var tp = 0

    override fun initGui() {
        val info = mc.player.getCapability(PlayerInfoProvider.playerCap, null)!!
        val level = mc.player.getCapability(PlayerLevelProvider.playerCap, null)!!

        int = info.int
        str = info.str
        spd = info.spd
        abl = info.abl

        tp = level.upgradePoints

        buttonList.clear()

        val int = "${I18n.format("rpg.gui.intellect")}: $int"
        val str = "${I18n.format("rpg.gui.strength")}: $str"
        val spd = "${I18n.format("rpg.gui.speed")}: $spd"
        val abl = "${I18n.format("rpg.gui.ability")}: $abl"

        conInt = MinusButton(0, this.width / 2 - (43-fontRenderer.getStringWidth(int)), this.height/2-97+55)
        addInt = PlusButton(1, this.width / 2 - (55-fontRenderer.getStringWidth(int)), this.height/2-97+55)
        conInt.enabled = false

        conStr = MinusButton(2, this.width / 2 - (43-fontRenderer.getStringWidth(str)), this.height/2-97+68)
        addStr = PlusButton(3, this.width / 2 - (55-fontRenderer.getStringWidth(str)), this.height/2-97+68)
        conStr.enabled = false

        conSpd = MinusButton(4, this.width / 2 - (43-fontRenderer.getStringWidth(spd)), this.height/2-97+83)
        addSpd = PlusButton(5, this.width / 2 - (55-fontRenderer.getStringWidth(spd)), this.height/2-97+83)
        conSpd.enabled = false

        conAbl = MinusButton(6, this.width / 2 - (43-fontRenderer.getStringWidth(abl)), this.height/2+1)
        addAbl = PlusButton(7, this.width / 2 - (55-fontRenderer.getStringWidth(abl)), this.height/2+1)
        conAbl.enabled = false

        submit = GuiButton(8, this.width/2-30, this.height/2+16, 60, 20, I18n.format("rpg.gui.submit"))
        submit.enabled = false

        buttonList.add(conInt)
        buttonList.add(addInt)
        buttonList.add(conStr)
        buttonList.add(addStr)
        buttonList.add(conSpd)
        buttonList.add(addSpd)
        buttonList.add(conAbl)
        buttonList.add(addAbl)
        buttonList.add(submit)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val sr = ScaledResolution(mc)
        super.drawDefaultBackground()

        mc.renderEngine.bindTexture(mc.player.locationSkin)
        DrawUtils.drawTexturedModalRect(sr.scaledWidth/2-57, sr.scaledHeight/2-95+19, 32, 32, 32, 32)
        mc.fontRenderer.drawString(mc.player.name, sr.scaledWidth/2-20, sr.scaledHeight/2-95+19, 0xffffff)

        val exp = 250 * (77F / 250)
        DrawUtils.drawColoredRect(sr.scaledWidth/2-20, sr.scaledHeight/2-95+28, exp.toInt(), 10, 0xa1ff00)

        val level = mc.player.getCapability(PlayerLevelProvider.playerCap, null)!!
        val totalPoints = "${I18n.format("rpg.gui.total_points")}: ${level.upgradePoints}"
        val totalPointsWidth = mc.fontRenderer.getStringWidth(totalPoints)
        mc.fontRenderer.drawString(totalPoints, sr.scaledWidth/2-totalPointsWidth/2, sr.scaledHeight/2-90, 0xffffff)

        fontRenderer.drawString("${I18n.format("rpg.gui.intellect")}: $int", this.width/2-57, this.height/2-95+55, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.strength")}: $str", this.width/2-57, this.height/2-95+68, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.speed")}: $spd", this.width/2-57, this.height/2-95+83, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.ability")}: $abl", this.width/2-57, this.height/2-95+98, 0xffffff)

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun updateScreen() {
        val info = mc.player.getCapability(PlayerInfoProvider.playerCap, null)!!

        if(int == info.int)
            conInt.enabled = false
        if(str == info.str)
            conStr.enabled = false
        if(spd == info.spd)
            conSpd.enabled = false
        if(abl == info.abl)
            conAbl.enabled = false
        
        if(tp == 0) {
            addInt.enabled = false
            addStr.enabled = false
            addSpd.enabled = false
            addAbl.enabled = false
        }
    }
}