package com.example.selfsurvey.signingup

import android.service.autofill.Validators.or
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.selfsurvey.R




@Composable
fun SignInSignUpScreen(
    onSignInAsGuest : () -> Unit,
    modifier: Modifier = Modifier,
    content : @Composable() () -> Unit
){

    LazyColumn(modifier = modifier){
        item{
            Spacer(modifier = Modifier.height(44.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ){
                content()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OrSignInAsGuest(
               onSignedInAsGuest = onSignInAsGuest,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}






@Composable
fun Email(
    emailState: TextFieldState = remember {EmailState()},
    imeAction : ImeAction = ImeAction.Next,
    onImeAction : () -> Unit = {}
){

    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.email),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                emailState.onFocusChange(focused)
                if (!focused) {
                    emailState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        )
    )


    emailState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}


@Composable
fun Password(
    label : String,
    passwordState : TextFieldState,
    modifier : Modifier = Modifier,
    imeAction : ImeAction = ImeAction.Done,
    onImeAction : () -> Unit = {}
){
    val showPassword = remember{ mutableStateOf(false)}
    
    OutlinedTextField(
        value = passwordState.text,
        onValueChange ={
            passwordState.text = it
            passwordState.enableShowErrors()
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                passwordState.onFocusChange(focused)
                if (!focused) {
                    passwordState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
                
            }
        },
        trailingIcon = {
            if(showPassword.value){
                IconButton(onClick = {showPassword.value = false}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility),
                        contentDescription = stringResource(R.string.hide_password)
                    )
                    
                }
            }else{
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility_off),
                        contentDescription = stringResource(R.string.show_password)
                    )
                }
            }
        },
        visualTransformation = if(showPassword.value){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        },
        isError = passwordState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone ={ onImeAction }
        )
    )

    passwordState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun OrSignInAsGuest(
    onSignedInAsGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(R.string.or),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
        OutlinedButton(
            onClick = onSignedInAsGuest,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 24.dp)
        ) {
            Text(text = stringResource(R.string.sign_in_as_guest))
        }
    }
}

@Composable
fun SignUpTopAppBar(topAppBarText : String, onBackPressed : () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}