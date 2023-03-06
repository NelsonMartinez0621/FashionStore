package com.cyberwalker.fashionstore.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.ui.theme.cardColorBlue
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onAction: (actions: SignUpScreenActions) -> Unit,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.signup_message),
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Color.Gray
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(id = R.string.generic_email))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.generic_email_placeholder),
                    color = MaterialTheme.colors.onPrimary
                )
            },
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = cardColorBlue,
                cursorColor = Color.Black,
                disabledLabelColor = cardColorBlue,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = stringResource(id = R.string.generic_password))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.generic_password_placeholder),
                    color = MaterialTheme.colors.onPrimary
                )
            },
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = cardColorBlue,
                cursorColor = Color.Black,
                disabledLabelColor = cardColorBlue,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            onClick = {
                // need to sent the event to the viewModel and get the response
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )
        ) {
            Text(
                text = stringResource(id = R.string.signup_title),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()
                }
        }
        Text(
            text = stringResource(id = R.string.existing_account_message),
            fontWeight = FontWeight.Bold
            )
        Text(
            text = stringResource(id = R.string.other_signin_login_options_message),
            fontWeight = FontWeight.Medium,
            color = Color.Gray
            )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(
                    id = R.drawable.google__g__logo),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(
                    id = R.drawable.facebook_f_logo),
                    contentDescription = "Facebook Icon",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }
            
            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context,"$success", Toast.LENGTH_LONG).show()
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
sealed class SignUpScreenActions {
    object Home : SignUpScreenActions()
}