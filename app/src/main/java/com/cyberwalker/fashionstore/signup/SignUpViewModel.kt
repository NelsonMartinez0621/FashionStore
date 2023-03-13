package com.cyberwalker.fashionstore.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.authorization.AuthRepository
import com.cyberwalker.fashionstore.login.GoogleSignInState
import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun googleSignup(credential: AuthCredential) = viewModelScope.launch {
        repository.googleLogin(credential).collect { result ->
            when(result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }
        }
    }

    fun registerUser(email:String, password:String) = viewModelScope.launch {
        //when logging in, we need to collect all the states
        repository.registerUser(email, password).collect { result ->
            when(result) {
                is Resource.Success -> {
                    _signUpState.send(SignUpState(isSuccess = "Sign Up Success"))
                }
                is Resource.Loading -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(SignUpState(isError = result.message))
                }

            }
        }
    }
}