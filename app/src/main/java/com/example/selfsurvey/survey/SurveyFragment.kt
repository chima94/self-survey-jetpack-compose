package com.example.selfsurvey.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.selfsurvey.R
import com.example.selfsurvey.ui.theme.SelfSurveyTheme
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState


class SurveyFragment : Fragment() {

    private val viewModel: SurveyViewModel by viewModels {
        SurveyViewModelFactory(PhotoUriManager(requireContext().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            id = R.id.sign_in_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                SelfSurveyTheme {
                    viewModel.uiState.observeAsState().value?.let { surveyState ->
                        when (surveyState) {
                            is SurveyState.Questions -> SurveyQuestionsScreen(
                                questions = surveyState,
                                onAction = { id, action -> handleSurveyAction(id, action) },
                                onDonePressed = { viewModel.computeResult(surveyState) },
                                onBackPressed = {
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            )
                            is SurveyState.Result -> SurveyResultScreen(
                                result = surveyState,
                                onDonePressed = {
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    private fun handleSurveyAction(questionId : Int, actionType : SurveyActionType){
        when(actionType){
            SurveyActionType.PICK_DATE -> showDatePicker(questionId)
            SurveyActionType.TAKE_PHOTO -> takePhoto()
            SurveyActionType.SELECT_CONTACT -> selectContact(questionId)
        }
    }


    private fun showDatePicker(questionId: Int) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        activity?.let {
            picker.show(it.supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                viewModel.onDatePicked(questionId, picker.headerText)
            }
        }
    }

    private fun takePhoto() {

    }


    private fun selectContact(questionId: Int) {

    }

}