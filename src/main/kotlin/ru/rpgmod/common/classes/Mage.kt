package ru.rpgmod.common.classes

import ru.rpgmod.api.classes.PlayerClass
import ru.rpgmod.api.skills.Skill
import ru.rpgmod.api.spells.Spell
import ru.rpgmod.common.skills.any.PassiveArmor
import ru.rpgmod.common.spells.HealingSpell

class Mage: PlayerClass() {
    override val name: String = "Mage"

    override val baseInt: Int = 8
    override val baseStr: Int = 5
    override val baseSpd: Int = 6
    override val baseAbl: Int = 6

    override val baseMana: Int = 170
    override val baseHealth: Int = 70

    override val allowSpells: ArrayList<Spell> = arrayListOf(HealingSpell())
    override val allowedSkills: Array<Skill> = arrayOf(PassiveArmor())
}