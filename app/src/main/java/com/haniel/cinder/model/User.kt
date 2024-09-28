package com.haniel.cinder.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var id: String = "",
    val name: String = "",
    val password: String = "",
    val age: Int = 0,
    val imageID: Int = 0,
    val biograpy: String = "",
    val favoritesList: MutableList<String> = mutableListOf()

) {
    constructor() : this("","", "", 0, 0, "", mutableListOf())

    open fun isEmpty(): Boolean {
        return name.isEmpty() && password.isEmpty() && age == 0 && imageID == 0 && biograpy.isEmpty()
    }

    fun addFavoriteList(u:User){
        if(u.id !in this.favoritesList)
            this.favoritesList.add(u.id)
    }
    fun removeFavoriteList(u:User):Boolean{
        /**
        * remove o usuário da lista de favoritos.
         * retorna se foi possível remover o usuário.
        * */
            return this.favoritesList.remove(u.id)
    }

    fun listFavoriteList():MutableList<String>{
        /**
         * Retorna uma cópia da lista de favoritos deste usuário
         * */
        return this.favoritesList.toMutableList();
    }
}
