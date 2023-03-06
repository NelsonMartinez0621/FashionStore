package com.cyberwalker.fashionstore.data.authorization

import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email:String, password:String): Flow<Resource<AuthResult>>

    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>

    fun googleLogin(credential: AuthCredential): Flow<Resource<AuthResult>>
}