package ru.rpgmod.api.skills

abstract class Skill(val isPassive: Boolean) {
    abstract fun work()
}
