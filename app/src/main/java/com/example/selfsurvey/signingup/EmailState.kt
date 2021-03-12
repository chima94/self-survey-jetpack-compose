package com.example.selfsurvey.signingup

import java.util.regex.Pattern

private const val EMAIL_VALIADATION_REGEX = "^(.+)@(.+)\$"

class EmailState : TextFieldState(validator = ::isEmailValid, errorFor =  ::emailValidationError)

private fun emailValidationError(email : String) : String{
    return "invalid email $email"
}


private fun isEmailValid(email: String) : Boolean{
    return Pattern.matches(EMAIL_VALIADATION_REGEX, email)
}