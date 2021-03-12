package com.example.selfsurvey

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen {Welcome, SignUp, SignIn, Survey}

fun Fragment.navigate(to : Screen, from : Screen){

    if(to == from){
        throw InvalidParameterException("can't navigate to $to")
    }

    when(to){
        Screen.Welcome ->{
            findNavController().navigate(R.id.welcomeFragment)
        }
        Screen.SignIn ->{
            findNavController().navigate(R.id.signInFragment)
        }
        Screen.SignUp ->{
            findNavController().navigate(R.id.signUpFragment)
        }
        Screen.Survey ->{
            findNavController().navigate(R.id.surveyFragment)
        }
    }
}