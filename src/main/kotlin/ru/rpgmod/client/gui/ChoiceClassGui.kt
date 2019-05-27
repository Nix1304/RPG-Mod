package ru.rpgmod.client.gui

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation
import ru.rpgmod.api.classes.ClassesList
import ru.rpgmod.common.capabilities.*
import ru.rpgmod.common.main.Constants.modid
import ru.rpgmod.common.networking.*

class ChoiceClassGui: GuiScreen() {
    private val background = ResourceLocation(modid, "textures/gui/choice_class_gui.png")

    private var totalPoints = 15
    private var intPoints = 1
    private var strPoints = 1
    private var spdPoints = 1
    private var ablPoints = 1
    private val guiWidth = 200
    private val guiHeight = 200

    private lateinit var conInt: GuiButton
    private lateinit var addInt: GuiButton
    private lateinit var conStr: GuiButton
    private lateinit var addStr: GuiButton
    private lateinit var conSpd: GuiButton
    private lateinit var addSpd: GuiButton
    private lateinit var conAbl: GuiButton
    private lateinit var addAbl: GuiButton
    private var playerClass: String? = null
    private var prevClassBut: GuiButton? = null
    private lateinit var submit: GuiButton

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        mc.textureManager.bindTexture(background)
        val x = this.width / 2 - guiWidth / 2
        val y = this.height / 2 - guiHeight / 2
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight)

        fontRenderer.drawString("${I18n.format("rpg.gui.intellect")}: $intPoints", this.width / 2 - 95, this.height / 2 - 93, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.strength")}: $strPoints", this.width / 2 - 95, this.height / 2 - 78, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.speed")}: $spdPoints", this.width / 2 - 95, this.height / 2 - 63, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.ability")}: $ablPoints", this.width / 2 - 95, this.height / 2 - 48, 0xffffff)
        fontRenderer.drawString("${I18n.format("rpg.gui.choose_class")}:", this.width / 2 - 95, this.height / 2 - 33, 0xffffff)

        val tpStr = "${I18n.format("rpg.gui.total_points")}: $totalPoints"
        fontRenderer.drawString(tpStr, this.width / 2 + (95 - fontRenderer.getStringWidth(tpStr)), this.height / 2 - 95, 0xffffff)
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun initGui() {
        val int = "${I18n.format("rpg.gui.intellect")}: $intPoints"
        conInt = MinusButton(0, this.width / 2 - (95 - fontRenderer.getStringWidth(int)-14), this.height / 2 - 95)
        addInt = PlusButton(1, this.width / 2 - (95 - fontRenderer.getStringWidth(int)-2), this.height / 2 - 95)
        conInt.enabled = false

        val str = "${I18n.format("rpg.gui.strength")}: $strPoints"
        conStr = MinusButton(2, this.width / 2 - (95 - fontRenderer.getStringWidth(str)-14), this.height / 2 - 80)
        addStr = PlusButton(3, this.width / 2 - (95 - fontRenderer.getStringWidth(str)-2), this.height / 2 - 80)
        conStr.enabled = false

        val spd = "${I18n.format("rpg.gui.speed")}: $spdPoints"
        conSpd = MinusButton(4, this.width / 2 - (95 - fontRenderer.getStringWidth(spd)-14), this.height / 2 - 65)
        addSpd = PlusButton(5, this.width / 2 - (95 - fontRenderer.getStringWidth(spd)-2), this.height / 2 - 65)
        conSpd.enabled = false

        val abl = "${I18n.format("rpg.gui.ability")}: $ablPoints"
        conAbl = MinusButton(6, this.width / 2 - (95 - fontRenderer.getStringWidth(abl)-14), this.height / 2 - 50)
        addAbl = PlusButton(7, this.width / 2 - (95 - fontRenderer.getStringWidth(abl)-2), this.height / 2 - 50)
        conAbl.enabled = false

        submit = GuiButton(8, this.width / 2 - 30, this.height / 2 + 75, 60, 20, I18n.format("rpg.gui.submit"))

        buttonList.clear()
        buttonList.add(conInt)
        buttonList.add(addInt)
        buttonList.add(conStr)
        buttonList.add(addStr)
        buttonList.add(conSpd)
        buttonList.add(addSpd)
        buttonList.add(conAbl)
        buttonList.add(addAbl)
        buttonList.add(submit)

        var id = 9
        for(cls in ClassesList.classesList.keys) {
            buttonList.add(GuiButton(
                    id,
                    this.width / 2 - (95 - 40 * (id - 9)) + (5 * (id - 9) - 175 * (id - 9).div(4)),
                    this.height / 2 - (20 - ((id - 9).div(4) * 25)),
                    40,
                    20,
                    cls))
            id++
        }
    }

    override fun actionPerformed(button: GuiButton) {
        super.actionPerformed(button)
        when(button.id) {
            0 -> {
                totalPoints++
                intPoints--
                val intStr = "${I18n.format("rpg.gui.intellect")}: $intPoints"
                conInt.x = this.width / 2 - (95 - fontRenderer.getStringWidth(intStr)-14)
                addInt.x = this.width / 2 - (95 - fontRenderer.getStringWidth(intStr)-2)
            }
            1 -> {
                totalPoints--
                intPoints++
                val intStr = "${I18n.format("rpg.gui.intellect")}: $intPoints"
                conInt.x = this.width / 2 - (95 - fontRenderer.getStringWidth(intStr)-14)
                addInt.x = this.width / 2 - (95 - fontRenderer.getStringWidth(intStr)-2)
            }
            2 -> {
                totalPoints++
                strPoints--
                val strStr = "${I18n.format("rpg.gui.strength")}: $strPoints"
                conStr.x = this.width / 2 - (95 - fontRenderer.getStringWidth(strStr)-14)
                addStr.x = this.width / 2 - (95 - fontRenderer.getStringWidth(strStr)-2)
            }
            3 -> {
                totalPoints--
                strPoints++
                val strStr = "${I18n.format("rpg.gui.strength")}: $strPoints"
                conStr.x = this.width / 2 - (95 - fontRenderer.getStringWidth(strStr)-14)
                addStr.x = this.width / 2 - (95 - fontRenderer.getStringWidth(strStr)-2)
            }
            4 -> {
                totalPoints++
                spdPoints--
                val spdStr = "${I18n.format("rpg.gui.speed")}: $spdPoints"
                conSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(spdStr)-14)
                addSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(spdStr)-2)
            }
            5 -> {
                totalPoints--
                spdPoints++
                val spdStr = "${I18n.format("rpg.gui.speed")}: $spdPoints"
                conSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(spdStr)-14)
                addSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(spdStr)-2)
            }
            6 -> {
                totalPoints++
                ablPoints--
                val ablStr = "${I18n.format("rpg.gui.ability")}: $ablPoints"
                conSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(ablStr)-14)
                addSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(ablStr)-2)
            }
            7 -> {
                totalPoints--
                ablPoints++
                val ablStr = "${I18n.format("rpg.gui.ability")}: $ablPoints"
                conSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(ablStr)-14)
                addSpd.x = this.width / 2 - (95 - fontRenderer.getStringWidth(ablStr)-2)
            }
            8 -> {
                if(playerClass == null) return
                val playerCls = ClassesList.classesList[playerClass!!]!!
                val h = playerCls.baseHealth + strPoints * 10F
                val m = playerCls.baseMana + intPoints * 10F
                val health = mc.player.getCapability(PlayerHealthProvider.playerCap, null)!!
                val mana = mc.player.getCapability(PlayerManaProvider.playerCap, null)!!
                val level = mc.player.getCapability(PlayerLevelProvider.playerCap, null)!!
                val info = mc.player.getCapability(PlayerInfoProvider.playerCap, null)!!

                info.int = intPoints
                info.str = strPoints
                info.spd = spdPoints
                info.abl = ablPoints

                mc.player.getCapability(PlayerClassProvider.playerCap, null)!!.name = playerClass!!

                health.health = h
                health.maxHealth = h

                mana.mana = m
                mana.maxMana = m

                level.level = 1
                level.upgradePoints = totalPoints

                NetworkHandler.network.sendToServer(PlayerHealthMessage(h, h))
                NetworkHandler.network.sendToServer(PlayerManaMessage(m, m))
                NetworkHandler.network.sendToServer(PlayerClassMessage(playerClass!!))
                NetworkHandler.network.sendToServer(PlayerInfoMessage(intPoints, strPoints, spdPoints, ablPoints))
                NetworkHandler.network.sendToServer(PlayerLevelMessage(0, 1, totalPoints))

                mc.displayGuiScreen(null)
            }
            else -> {
                prevClassBut?.enabled = true
                playerClass = button.displayString
                button.enabled = false
                prevClassBut = button
            }
        }
        val tpStr = "${I18n.format("rpg.gui.total_points")}: $totalPoints"
        fontRenderer.drawString(tpStr, this.width / 2 + (95 - fontRenderer.getStringWidth(tpStr)), this.height / 2 - 95, 0xffffff)
    }

    override fun updateScreen() {
        addInt.enabled = totalPoints != 0
        conInt.enabled = intPoints != 1

        addStr.enabled = totalPoints != 0
        conStr.enabled = strPoints != 1

        addSpd.enabled = totalPoints != 0
        conSpd.enabled = spdPoints != 1

        addAbl.enabled = totalPoints != 0
        conAbl.enabled = ablPoints != 1
    }

    override fun doesGuiPauseGame(): Boolean = true
}