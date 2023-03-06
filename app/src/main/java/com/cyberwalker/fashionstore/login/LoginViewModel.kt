package com.cyberwalker.fashionstore.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.authorization.AuthRepository
import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _loginState = Channel<LoginState>()
    val loginState = _loginState.receiveAsFlow()

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun googleLogin(credential: AuthCredential) = viewModelScope.launch {
        repository.googleLogin(credential).collect() { result ->
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

    fun loginUser(email:String, password:String) = viewModelScope.launch {
        //when logging in, we need to collect all the states
        repository.loginUser(email, password).collect() { result ->
            when(result) {
                is Resource.Success -> {
                    _loginState.send(LoginState(isSuccess = "Login Success"))
                }
                is Resource.Loading -> {
                    _loginState.send(LoginState(isLoading = true))
                }
                is Resource.Error -> {
                    _loginState.send(LoginState(isError = result.message))
                }

            }
        }
    }

//    private val _loginResultState: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    val loginResultState: StateFlow<Boolean> = _loginResultState
//
//    fun onLogin(email: String, password: String) {
//        //send it to repository and get result from server and return to UI
//        _loginResultState.value = true
//    }
}