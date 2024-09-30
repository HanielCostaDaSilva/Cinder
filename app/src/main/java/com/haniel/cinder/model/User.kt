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
    val matchReceivedList: MutableList<String> = mutableListOf(),
    val matchSendList: MutableList<String> = mutableListOf()

) {
    constructor() : this("","", "", 0, 0, "", mutableListOf())

    open fun isEmpty(): Boolean {
        return name.isEmpty() && password.isEmpty() && age == 0 && imageID == 0 && biograpy.isEmpty()
    }

    fun addMatchSend(u:User):Boolean{
        if(u.id !in this.matchSendList)
            return this.matchSendList.add(u.id);
        return false;
    }

    fun removeMatchSend(u:User):Boolean{
        /**
        * remove o usuário da lista de matches.
         * retorna se foi possível remover o usuário.
        * */
            return this.matchSendList.remove(u.id)
    }

    fun listMatchSend():MutableList<String>{
        /**
         * Retorna uma cópia da lista de matches deste usuário
         * */
        return this.matchSendList.toMutableList();
    }

    fun addMatchReceived(u:User):Boolean{
        if(u.id !in this.matchReceivedList)
            return this.matchReceivedList.add(u.id);
        return false;
    }
    fun removeMatchReceived(u:User):Boolean{
        /**
         * remove o usuário da lista de matches.
         * retorna se foi possível remover o usuário.
         * */
        return this.matchReceivedList.remove(u.id)
    }

    fun listMatchReceived():MutableList<String>{
        /**
         * Retorna uma cópia da lista de matches deste usuário
         * */
        return this.matchReceivedList.toMutableList();
    }

    fun findMatchSend(user: User): Boolean {
        return this.matchSendList.contains(user.id);
    }

    fun checkMutualMatch(user: User): Boolean {
        return this.matchSendList.contains(user.id) && user.matchSendList.contains(this.id)
    }

}
