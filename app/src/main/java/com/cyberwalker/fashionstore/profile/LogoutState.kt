package com.cyberwalker.fashionstore.profile

data class LogoutState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
