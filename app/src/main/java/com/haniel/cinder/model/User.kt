package com.haniel.cinder.model

open class User(
    val name: String = "",
    val password: String = "",
    val age: Int = 0,
    val imageID: Int = 0,
    val biograpy: String = ""
) {
    var id: String = ""

    constructor() : this("", "", 0, 0, "")

    open fun isEmpty(): Boolean {
        return name.isEmpty() && password.isEmpty() && age == 0 && imageID == 0 && biograpy.isEmpty()
    }
}
