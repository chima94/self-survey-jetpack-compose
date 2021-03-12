package com.example.selfsurvey.signingup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.selfsurvey.R
import com.example.selfsurvey.Screen
import com.example.selfsurvey.navigate
import com.example.selfsurvey.ui.theme.SelfSurveyTheme


class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.navigateTo.observe(viewLifecycleOwner){navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let{navigateTo ->
                navigate(navigateTo, Screen.SignUp)
            }

        }

        return ComposeView(requireContext()).apply {
            id = R.id.sign_up_fragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent { 
                SelfSurveyTheme {
                    SignUp(
                        onNavigationEvent = {event ->
                            when(event){
                                is SignUpEvent.SignUp ->{
                                    viewModel.signUp(event.email, event.password)
                                }
                                is SignUpEvent.SignIn ->{
                                    viewModel.signIn()
                                }
                                is SignUpEvent.SignInAsQuest ->{
                                    viewModel.signInAsQuest()
                                }
                                is SignUpEvent.NavigateBack ->{
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            }

                        }
                    )
                }
            }
        }
    }


}