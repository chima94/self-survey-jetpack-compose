
package com.example.selfsurvey.survey

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Question(
    question: Question,
    answer: Answer<*>?,
    onAnswer : (Answer<*>) -> Unit,
    onAction : (Int, SurveyActionType) -> Unit,
    modifier : Modifier = Modifier
){

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ){
        item {
            Spacer(modifier = Modifier.height(44.dp))

            val backgroundColor = if(MaterialTheme.colors.isLight){
                MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
            }else{
                MaterialTheme.colors.onSurface.copy( alpha = 0.06f)
            }
            
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = backgroundColor,
                        shape = MaterialTheme.shapes.small
                    )
                    ){
                    Text(
                        text = stringResource(id = question.questionText),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 16.dp)
                    )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if(question.description != null){
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(id = question.description),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp, start = 8.dp, end = 8.dp)
                    )
                }
            }

            when(question.answer){
                is PossibleAnswer.MultipleChoice -> MultipleQuestionChoice(
                    possibleAnswer = question.answer,
                    answer = answer as Answer.MultipleChoice?,
                    onAnswerSelected = {newAnswer, selected ->
                        if(answer == null){
                            onAnswer(Answer.MultipleChoice(setOf(newAnswer)))
                        }else{
                            onAnswer(answer.withAnswerSelected(newAnswer, selected))
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                is PossibleAnswer.SingleChoice -> SingleQuestionChoice()
                is PossibleAnswer.Action -> ActionQuestionChoice()
                is PossibleAnswer.Slider -> SliderQuestion()
            }
        }
    }
}






@Composable
fun MultipleQuestionChoice(
    possibleAnswer: PossibleAnswer.MultipleChoice,
    answer : Answer.MultipleChoice?,
    onAnswerSelected : (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.optionsStringRes.associateBy { stringResource(id = it) }
    Column(modifier = modifier) {
        for(option in options){
            var checkedState by remember(answer) {
                val selectedOption = answer?.answersStringRes?.contains(option.value)
                mutableStateOf(selectedOption ?: false)
            }

            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                checkedState = !checkedState
                                onAnswerSelected(option.value, checkedState)
                            }
                        )
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = option.key)
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {selected ->
                            checkedState = selected
                            onAnswerSelected(option.value, selected)

                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}




@Composable
fun SliderQuestion() {
    TODO("Not yet implemented")
}




@Composable
fun ActionQuestionChoice() {
    TODO("Not yet implemented")
}


@Composable
fun SingleQuestionChoice() {
    TODO("Not yet implemented")
}
