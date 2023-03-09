package com.cyberwalker.fashionstore.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cyberwalker.fashionstore.profile.ProfileScreenActions
import com.cyberwalker.fashionstore.profile.ProfileViewModel


@Composable
fun LogoutButton(
    viewModel: ProfileViewModel
) {
    Button(
        modifier = Modifier
            .padding(vertical = 24.dp),
        onClick = { viewModel.logoutUser() }
    ) {
        if (viewModel.isAuthorized() != null) {
            Text(text = "Logout")
        } else {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true, widthDp = 100, heightDp = 10)
@Composable
fun LogoutButtonPreview() {
    //LogoutButton(onContinueClicked = {})
}