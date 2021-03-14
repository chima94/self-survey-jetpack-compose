package com.example.selfsurvey.survey

import android.net.Uri
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SurveyViewModel(
    private val surveyRepository : SurveyRepository,
    private val photoUriManager: PhotoUriManager
) : ViewModel() {


    private val _uiState = MutableLiveData<SurveyState>()
    val uiState : LiveData<SurveyState>
        get() = _uiState

    private lateinit var surveyInitialState : SurveyState

    private var Uri : Uri? = null

    init {
        viewModelScope.launch {
            val survey = surveyRepository.getSurvey()
            val questions : List<QuestionState> = survey.questions.mapIndexed{index, question ->
                val showPrevious = index > 0
                val showDone = index == survey.questions.size - 1
                QuestionState(
                    question = question,
                    questionIndex = index,
                    totalQuestionCount = survey.questions.size,
                    showPrevious = showPrevious,
                    showDone = showDone
                )
            }
            surveyInitialState = SurveyState.Questions(survey.title, questions)
            _uiState.value = surveyInitialState
        }
    }
    fun onDatePicked(questionId: Int, headerText: String?) {

    }

    fun computeResult(surveyState: SurveyState.Questions) {

    }

}







class SurveyViewModelFactory(
    private val photoUriManager: PhotoUriManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(SurveyRepository, photoUriManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
