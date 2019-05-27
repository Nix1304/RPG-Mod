package ru.rpgmod.api.skills

/**
 * @author nix13
 * @project RPG Mod
 * @package ru.rpgmod.api.skills
 * @date 2019-01-19
 */
object SkillsList {
    var skillsList = HashMap<String, Skill>()
    var skills = ArrayList<Skill>()

    fun addSpell(name: String, skill: Skill) {
        skillsList[name] = skill
        skills.add(skill)
    }
}