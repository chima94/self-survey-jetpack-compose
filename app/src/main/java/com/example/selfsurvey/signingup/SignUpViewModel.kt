package com.example.selfsurvey.signingup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfsurvey.Screen
import com.example.selfsurvey.util.Event

class SignUpViewModel(private val userRepository : UserRepository) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo : LiveData<Event<Screen>> = _navigateTo


    fun signUp(email : String, password : String){
        userRepository.signUp(email, password)
        _navigateTo.value = Event(Screen.Survey)

    }

    fun signInAsQuest(){
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Screen.Survey)
    }

    fun signIn(){
        _navigateTo.value = Event(Screen.SignIn)
    }
}








class SignUpViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}