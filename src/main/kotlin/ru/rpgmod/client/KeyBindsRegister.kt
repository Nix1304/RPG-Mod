package ru.rpgmod.client

import net.minecraft.client.resources.I18n
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import org.lwjgl.input.Keyboard

object KeyBindsRegister {
    private const val category = "RPG Mod"

    val openInfoPaneKey = KeyBinding(I18n.format("rpg.open_info_pane"), Keyboard.KEY_I, category)

    fun register() {
        setRegister(openInfoPaneKey)
    }

    private fun setRegister(binding: KeyBinding) {
        ClientRegistry.registerKeyBinding(binding)
    }
}