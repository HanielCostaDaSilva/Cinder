package com.haniel.cinder.service

import com.haniel.cinder.model.User
import com.haniel.cinder.repository.UserDAO
import kotlinx.coroutines.coroutineScope

class UserService {
    val userDAO: UserDAO = UserDAO();

    suspend fun getById(id: String, callback: (User?) -> Unit){
        return this.userDAO.findById(id,callback);
    }

    fun getByName(name: String, callback: (User?) -> Unit) {
        return this.userDAO.findByName(name, callback);
    }

    suspend fun getById(ids: List<String>, callback: (List<User>?) -> Unit) {
        val userList = mutableListOf<User>()
        coroutineScope {
            ids.forEach { id ->
                // Chama o método do DAO para buscar o usuário pelo ID
                userDAO.findById(id){ user:User? ->
                    user.let {
                        if (it != null) {
                            userList.add(it)
                        }
                    }
                    if (userList.size == ids.size) {
                        callback(userList) // Chama o callback quando todos forem adicionados
                    }
                }
            }
        }
    }
    
    fun checkIfAlreadySendMatch(userPrincipal:User, other:User): Boolean {
        return userPrincipal.findMatchSend(other);
    }

    fun sendMatch(userPrincipal:User, userToMatch:User): Number {
        /**
         * O usuario atual envia um pedido de Match para uma outra pessoa.
         * Caso o usuario Já tenha recebido um pedido de Match da Outra pessoa, então rolou  um Match
         * Se o usuário já tiver enviado Match para essa pessoa, retorna -1
         * Se apenas enviou o pedido de Match 0
         * Se rolar Match retorna 1
         * */

        if(checkIfAlreadySendMatch(userPrincipal, userToMatch)){
            return -1;
        }
        this.userDAO.updateUser(userPrincipal){};
        userToMatch.addMatchReceived(userPrincipal);

        this.userDAO.updateUser(userToMatch){};
        val rolouMatch = userPrincipal.checkMutualMatch(userToMatch);
        return if (rolouMatch)  1 else 0
    }

}