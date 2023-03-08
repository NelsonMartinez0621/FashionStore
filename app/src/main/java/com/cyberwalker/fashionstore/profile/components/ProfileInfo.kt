package com.cyberwalker.fashionstore.profile.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileInfo(
    onContinueClicked: () -> Unit,
    info: String,
    label: String,
) {

    TextField(
        label = { Text(text = label) },
        value = info,
        onValueChange = {
            onContinueClicked
        }
    )

}

@Preview(showBackground = true, widthDp = 200, heightDp = 20)
@Composable
fun ProfileInfoPreview() {
   ProfileInfo(onContinueClicked = {}, info = "Test", label = "Test Value")
}