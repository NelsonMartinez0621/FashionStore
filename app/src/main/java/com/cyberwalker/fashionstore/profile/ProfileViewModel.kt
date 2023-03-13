package com.cyberwalker.fashionstore.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.authorization.AuthRepository
import com.cyberwalker.fashionstore.profile.dataModel.User
import com.cyberwalker.fashionstore.util.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _logoutState = Channel<LogoutState>()
    val logoutState = _logoutState.receiveAsFlow()
    private val firebaseData: FirebaseFirestore = Firebase.firestore
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {

        initData()
    }


    private fun initData() = viewModelScope.launch {

        val data = firebaseData.collection("user")
            .document("DGPwMHbsFxUIgOgZbdoJcI2l0Iw1")

        data.get().addOnSuccessListener { document ->
            if (document != null) {
                _user.value = User(
                    name = document.data?.get("name").toString(),
                    lastName = document.data?.get("lastName").toString(),
                    email = document.data?.get("email").toString(),
                    imageUrl = document.data?.get("imageUrl").toString(),
                )
            }
        }
    }


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