package ru.rpgmod.api.classes

object ClassesList {
    var classesList = HashMap<String, PlayerClass>()
    var classes = ArrayList<String>()

    fun addClass(name: String, class_: PlayerClass) {
        classesList[name] = class_
        classes.add(class_.name)
    }
}