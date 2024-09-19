package com.haniel.cinder.service

import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import kotlinx.coroutines.coroutineScope

class UserService {
    val userDAO: UserDAO = UserDAO();

    fun getById(id: String, callback: (User?) -> Unit) {
        return this.userDAO.findbyId(id, callback);
    }

    fun getByName(name: String, callback: (User?) -> Unit) {
        return this.userDAO.findByName(name, callback);
    }

    suspend fun getById(ids: List<String>, callback: (List<User>?) -> Unit) {
        val userList = mutableListOf<User>()

        coroutineScope {
            ids.forEach { id ->
                // Chama o método do DAO para buscar o usuário pelo ID
                userDAO.findbyId(id) { user ->
                    user?.let {
                        userList.add(it)
                    }
                    if (userList.size == ids.size) {
                        callback(userList) // Chama o callback quando todos forem adicionados
                    }
                }
            }
        }
    }


}