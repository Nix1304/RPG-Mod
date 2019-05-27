package ru.rpgmod.common.classes

import ru.rpgmod.api.classes.PlayerClass
import ru.rpgmod.api.skills.Skill
import ru.rpgmod.api.spells.Spell
import ru.rpgmod.common.spells.HealingSpell

class Archer: PlayerClass() {
    override val name: String = "Archer"

    override val baseInt: Int = 6
    override val baseStr: Int = 3
    override val baseSpd: Int = 8
    override val baseAbl: Int = 7

    override val baseMana: Int = 50
    override val baseHealth: Int = 50

    override val allowSpells: ArrayList<Spell> = arrayListOf(HealingSpell())
    override val allowedSkills: Array<Skill> = arrayOf()
}