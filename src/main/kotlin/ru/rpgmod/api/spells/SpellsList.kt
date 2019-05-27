package ru.rpgmod.api.spells

/**
 * @author nix13
 * @project RPG Mod
 * @package ru.rpgmod.api.spells
 * @date 2019-01-19
 */
object SpellsList {
    var spellsList = HashMap<String, Spell>()
    var spells = ArrayList<Spell>()

    fun addSpell(name: String, spell: Spell) {
        spellsList[name] = spell
        spells.add(spell)
    }
}