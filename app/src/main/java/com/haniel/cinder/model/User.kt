package com.haniel.cinder.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var id: String = "",
    val name: String = "",
    val password: String = "",
    val age: Int = 0,
    val biograpy: String = "",
    val imageUrl: String = ""
) {

    constructor() : this("","", "", 0, "", "")

    open fun isEmpty(): Boolean {
        return name.isEmpty() && password.isEmpty() && age == 0 && biograpy.isEmpty() && imageUrl.isEmpty()
    }
}
