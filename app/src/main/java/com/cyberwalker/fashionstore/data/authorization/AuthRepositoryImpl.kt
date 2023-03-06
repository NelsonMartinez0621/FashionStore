package com.cyberwalker.fashionstore.data.authorization

import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
       return flow {
           emit(Resource.Loading())

           val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
           //await means we will wait to define this variable until signInWithEmailAndPassword is done

           emit(Resource.Success(result))
       }.catch {
           //Catch the exceptions
           emit(Resource.Error(it.message.toString()))
       }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            //pretty much the same as loginUser, just a different result method; createUser instead
            //login
            emit(Resource.Loading())

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            //Catch the exceptions
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun googleLogin(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            //pretty much the same as loginUser, just a different result method; createUser instead
            //login
            emit(Resource.Loading())
            //This one takes a credential rather than email and password
            val result = firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(result))
        }.catch {
            //Catch the exceptions
            emit(Resource.Error(it.message.toString()))
        }
    }
}