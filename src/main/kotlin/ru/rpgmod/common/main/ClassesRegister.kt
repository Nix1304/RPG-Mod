package ru.rpgmod.common.main

import ru.rpgmod.api.classes.ClassesList
import ru.rpgmod.common.classes.Archer
import ru.rpgmod.common.classes.Mage

object ClassesRegister {
    fun register() {
        ClassesList.classesList["Mage"] = Mage()
        ClassesList.classesList["Archer"] = Archer()
    }
}