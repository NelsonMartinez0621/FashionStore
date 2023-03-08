package com.cyberwalker.fashionstore.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cyberwalker.fashionstore.R


@Composable
fun ImageBox(
    onContinueClicked: () -> Unit,
    imageUrl: String?

) {

    val painter = R.drawable.default_image

    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
    ) {
        Image(
            painter = painterResource(id = painter),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .clickable { onContinueClicked }
        )
    }


}


@Preview(showBackground = true, widthDp = 100, heightDp = 10)
@Composable
fun ImageBoxPreview() {

    ImageBox(onContinueClicked = {}, imageUrl = "")
}