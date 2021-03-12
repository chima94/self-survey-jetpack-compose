package com.example.selfsurvey.signingup

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.selfsurvey.R
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.log

sealed class WelcomeEvent{
    data class SignInSignUp(val email : String) : WelcomeEvent()
    object SignInAsGuest : WelcomeEvent()
}

@Composable
fun WelcomeScreen(onEvent : (WelcomeEvent) -> Unit){
    var brandingBottom by remember { mutableStateOf(0f) }
    var showBranding by remember{ mutableStateOf(true) }
    var heightWithBranding by remember { mutableStateOf(0) }

    var currentOffsetHolder by remember { mutableStateOf(0f) }
    currentOffsetHolder = if(showBranding) 0f else -brandingBottom
    val currentOffsetHolderDp = with(LocalDensity.current){ currentOffsetHolder.toDp()}
    val heightDp = with(LocalDensity.current){heightWithBranding.toDp()}
    Surface(modifier = Modifier.fillMaxSize()){
        val offset by animateDpAsState(targetValue = currentOffsetHolderDp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .brandingPreferredHeight(showBranding, heightDp)
                .offset(y = offset)
                .onSizeChanged {
                    if (showBranding) {
                        heightWithBranding = it.height
                    }
                }
        ) {
            Branding(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .onGloballyPositioned {
                        if (brandingBottom == 0f) {
                            brandingBottom = it.boundsInParent().bottom
                        }
                    }
            )

            SignInCreateAccount(
                onEvent = onEvent,
                onFocusChange = {focused -> showBranding = !focused},
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

        }
    }
}


private fun Modifier.brandingPreferredHeight(
    showBranding : Boolean,
    heightDp : Dp
) : Modifier{
    return if(!showBranding){
        this
            .wrapContentHeight(unbounded = true)
            .height(heightDp)
    }else{
        this
    }
}



@Composable
private fun Branding(modifier: Modifier = Modifier){
    
    Column(
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {

        Logo(
            modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 76.dp)
        )
        Text(
            text = stringResource(R.string.survey_text),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

    }
}




@Composable
private fun Logo(
    modifier : Modifier = Modifier,
    lightTheme : Boolean = MaterialTheme.colors.isLight
){
    val assetId = if(lightTheme){
        R.drawable.ic_logo_light
    }else{
        R.drawable.ic_logo_dark
    }

    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}


@Composable
private fun SignInCreateAccount(
    onEvent: (WelcomeEvent) -> Unit,
    onFocusChange : (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    val emailState = remember{ EmailState()}
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CompositionLocalProvider( LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(id = R.string.signIn_or_createAccount),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(vertical = 24.dp)
            )
        }

        val onSubmit = {
            if(emailState.isValid){
                onEvent(WelcomeEvent.SignInSignUp(emailState.text))
            }else{
                emailState.enableShowErrors()
            }
        }
        onFocusChange(emailState.isFocused)
        Email(emailState = emailState, imeAction = ImeAction.Done, onImeAction = onSubmit)
        Button(
            onClick = onSubmit,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
        ) {
            Text(
                text = stringResource(R.string.continue_btn),
                style = MaterialTheme.typography.subtitle2
            )
        }

        OrSignInAsGuest(
            onSignedInAsGuest = { onEvent(WelcomeEvent.SignInAsGuest) },
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewLogo(){
    SignInCreateAccount(onEvent = { /*TODO*/ }, onFocusChange = { /*TODO*/ })
}