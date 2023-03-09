package com.cyberwalker.fashionstore.signup

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.data.Constant.serverClient
import com.cyberwalker.fashionstore.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    onAction: (actions: SignUpScreenActions) -> Unit,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()) {

    val googleSignInState = viewModel.googleState.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult() ) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken,null)
                viewModel.googleSignup(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        }

    var emailState by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Login title
            Text(
                text = stringResource(id = R.string.signup_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = colors.primary,
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(8.dp))

            //Email field
            OutlinedTextField(
                value = emailState,
                onValueChange = {
                    emailState = it
                },
                label = {
                    Text(text = stringResource(id = R.string.generic_email))
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.generic_email_placeholder),
                        color = colors.onPrimary
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
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = passwordState,
                onValueChange = {
                    passwordState = it
                },
                label = {
                    Text(text = stringResource(id = R.string.generic_password))
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.generic_password_placeholder),
                        color = colors.onPrimary
                    )
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            //login button
            Button(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
                onClick = {
                    // need to sent the event to the viewModel and get the response
                    if (emailState.isNotEmpty() && passwordState.isNotEmpty()) {
                        scope.launch {
                            viewModel.registerUser(emailState, passwordState)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.secondary,
                    contentColor = colors.onSecondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.signup_message),
                    style = MaterialTheme.typography.button,
                    color = colors.onSecondary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stringResource(id = R.string.existing_account_message),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.existing_account_message_link),
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier.clickable(onClick = { onAction(SignUpScreenActions.Login) })
                )
            }
            Text(
                text = stringResource(id = R.string.other_signin_login_options_message),
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val gso = GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(serverClient)
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)

                    launcher.launch(googleSignInClient.signInIntent)
                }) {
                    Icon(painter = painterResource(
                        id = R.drawable.google__g__logo),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Unspecified
                    )
                }

                Button(onClick = { throw RuntimeException("Test Crash") }) {
                    Text(text = "Test Crash")
                }

                LaunchedEffect(key1 = state.value?.isSuccess) {
                    scope.launch {
                        if (state.value?.isSuccess?.isNotEmpty() == true) {
                            val success = state.value?.isSuccess
                            Toast.makeText(context,"$success", Toast.LENGTH_LONG).show()
                            onAction(SignUpScreenActions.Home)
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

                LaunchedEffect(key1 = googleSignInState.success) {
                    scope.launch {
                        if (googleSignInState.success != null) {
                            Toast.makeText(context,"Sign up Success", Toast.LENGTH_LONG).show()
                            onAction(SignUpScreenActions.Home)
                        }
                    }
                }

                LaunchedEffect(key1 = googleSignInState.error) {
                    scope.launch {
                        if (googleSignInState.error?.isNotEmpty() == true) {
                            val error = state.value?.isError
                            Toast.makeText(context,"$error", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    if (googleSignInState.loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
sealed class SignUpScreenActions {
    object Home : SignUpScreenActions()
    object Login: SignUpScreenActions()
}
