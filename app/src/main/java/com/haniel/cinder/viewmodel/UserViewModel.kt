package com.haniel.cinder.viewmodel

import androidx.lifecycle.ViewModel
import com.haniel.cinder.model.User

class UserViewModel : ViewModel() {
    var selectedUser: User? = null
}