package com.cyberwalker.fashionstore.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.authorization.AuthRepository
import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){

    private val _logoutState = Channel<LogoutState>()
    val logoutState = _logoutState.receiveAsFlow()

    fun logoutUser() = viewModelScope.launch {
        repository.logoutUser().collect() { result ->
            when (result) {
                is Resource.Success -> {
                    _logoutState.send(LogoutState(isSuccess = "Logout Success"))
                }

                is Resource.Loading -> {
                    _logoutState.send(LogoutState(isLoading = true))
                }

                is Resource.Error -> {
                    _logoutState.send(LogoutState(isError = result.message))
                }
            }
        }
    }

    fun isAuthorized(): FirebaseUser? = repository.isAuthorized()
}