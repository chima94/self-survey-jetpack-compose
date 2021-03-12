package com.example.selfsurvey.signingup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.selfsurvey.R
import com.example.selfsurvey.Screen
import com.example.selfsurvey.navigate
import com.example.selfsurvey.ui.theme.SelfSurveyTheme


class WelcomeFragment : Fragment() {

    private val viewModel : WelcomeViewModel by viewModels { WelcomeViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.navigateTo.observe(viewLifecycleOwner){navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Welcome)
            }
        }
        return ComposeView(requireContext()).apply {
            setContent {
                SelfSurveyTheme() {
                    WelcomeScreen(
                        onEvent = { event ->
                            when (event) {
                                is WelcomeEvent.SignInSignUp -> viewModel.handleContinue(
                                    event.email
                                )
                                WelcomeEvent.SignInAsGuest -> viewModel.SignInAsGuest()
                            }
                        }
                    )
                }
            }
        }
    }


}