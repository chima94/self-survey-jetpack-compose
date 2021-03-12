package com.example.selfsurvey.signingup

import androidx.compose.runtime.Immutable


sealed class User {
    @Immutable
    data class LogginUser(val email : String) : User()
    object GuestUser : User()
    object NoUserLoggedIn : User()

}
object UserRepository {
    private var _user : User = User.NoUserLoggedIn
    val user : User
        get() = _user

    fun signIn(email: String, password : String){
        _user = User.LogginUser(email)
    }

    fun signUp(email: String, password: String){
        _user = User.LogginUser(email)
    }

    fun signInAsGuest(){
        _user = User.GuestUser
    }

    fun isKnownUserEmail(email: String) : Boolean{
        return !email.contains("signup")
    }
}