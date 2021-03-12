package com.example.selfsurvey.signingup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selfsurvey.Screen
import com.example.selfsurvey.util.Event

class WelcomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo : LiveData<Event<Screen>> = _navigateTo

    fun handleContinue(email : String){
        if(userRepository.isKnownUserEmail(email)){
            _navigateTo.value = Event(Screen.SignUp)
        }else{
            _navigateTo.value = Event(Screen.SignUp)
        }
    }

    fun SignInAsGuest(){
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Screen.Survey)
    }
}



@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}