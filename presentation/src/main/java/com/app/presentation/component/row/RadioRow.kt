package com.app.presentation.component.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.user.User
import com.app.presentation.viewmodel.UserViewModel

@Composable
fun RadioRow(
    yesORNo: List<String>,
    id: Number,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {

    Row {
        yesORNo.forEach { text ->
            Row (
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 16.dp, start = 12.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            userViewModel.saveChecks(id, text)

                            if (id == 0 && text == "아니요") {
                                userViewModel.saveExerciseName("안함")
                            }
                            else if (id == 0 && text == "네") {
                                userViewModel.saveExerciseName("")
                            }
                            if (id == 1 && text == "아니요") {
                                userViewModel.saveWalkingOfWeek("안함")
                                userViewModel.saveWalkingOfTime("안함")
                            }
                            else if (id == 1 && text == "네") {
                                userViewModel.saveWalkingOfWeek("")
                                userViewModel.saveWalkingOfTime("")
                            }
                        }
                    )
            ) {
                RadioButton(
                    selected = ( text == selectedOption ),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF2377f9)
                    )
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}