package ru.rpgmod.common.main

import ru.rpgmod.api.spells.SpellsList
import ru.rpgmod.common.spells.HealingSpell

/**
 * @author nix13
 * @project RPG Mod
 * @package ru.rpgmod.common.main
 * @date 2019-01-19
 */
object SpellsRegister {
    fun register() {
        SpellsList.addSpell("healing_spell", HealingSpell())
    }
}