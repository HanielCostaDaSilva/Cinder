package com.haniel.cinder.service

import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import kotlinx.coroutines.coroutineScope

class UserService {
    private val userDAO: UserDAO = UserDAO()

    suspend fun getById(id: String): User? {
        return userDAO.findById(id)
    }

    fun getByName(name: String, callback: (User?) -> Unit) {
        userDAO.findByName(name, callback)
    }

    suspend fun getByIds(ids: List<String>): List<User> {
        val userList = mutableListOf<User>()

        coroutineScope {
            ids.forEach { id ->
                val user = userDAO.findById(id)
                user?.let {
                    userList.add(it)
                }
            }
        }
        return userList
    }

    fun sendMatch(userPrincipal: User, userToMatch: User): Boolean {
        return userPrincipal.addMatchSend(userToMatch) && userToMatch.addMatchReceived(userPrincipal)
    }
}
