package ru.rpgmod.api.classes

import ru.rpgmod.api.skills.Skill
import ru.rpgmod.api.spells.Spell

abstract class PlayerClass {
    abstract val name: String // Class name translate key

    abstract val baseInt: Int // Base intellect
    abstract val baseStr: Int // Base strength
    abstract val baseSpd: Int // Base speed
    abstract val baseAbl: Int // Base agility

    abstract val baseMana: Int // MP
    abstract val baseHealth: Int // HP

    abstract val allowSpells: ArrayList<Spell> // Spells that can be casted
    abstract val allowedSkills: Array<Skill> // Skills than can be learned

    fun canCastSpell(spell: Spell): Boolean = allowSpells.contains(spell)
    fun canUseSkill(skill: Skill): Boolean = allowedSkills.contains(skill)
}