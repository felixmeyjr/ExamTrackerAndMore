package com.example.examtrackerandmore.UI.exams

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.examtrackerandmore.UI.ADD_EXAM_RESULT_OK
import com.example.examtrackerandmore.UI.EDIT_EXAM_RESULT_OK
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.data.ExamDataAccessObject
import com.example.examtrackerandmore.data.PreferencesManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * TODO add explanation here
 */

class ExamViewModel @ViewModelInject constructor(
    private val examDataAccessObject: ExamDataAccessObject,
    private val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val exams = examDataAccessObject.getExams().asLiveData() // liveData: ?

    val preferencesFlow = preferencesManager.preferencesFlow

    // Event Channel
    private val examsEventChannel = Channel<ExamsEvent>()
    val examsEvent = examsEventChannel.receiveAsFlow()

    // Updating the preference here
    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onExamSelected(exam: Exam) = viewModelScope.launch{
        examsEventChannel.send(ExamsEvent.NavigateToEditExamScreen(exam))
    }

    // Swipe to delete
    fun onExamSwiped(exam: Exam) = viewModelScope.launch {
        examDataAccessObject.delete(exam)

        // ViewModel is responsible for showing the snackbar window with UNDO option; only fragment
        // or activity can show snackbar
        // Channel: to send data between Coroutines
        examsEventChannel.send(ExamsEvent.ShowUndoDeleteExamMessage(exam))
    }

    fun onUndoDeleteClick(exam: Exam) = viewModelScope.launch {
        examDataAccessObject.insert(exam)
    }

    // Click of add exam fab
    fun onAddNewExamClick() = viewModelScope.launch {
        // viewmodel tells ui what to do
        examsEventChannel.send(ExamsEvent.NavigateToAddExamScreen)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_EXAM_RESULT_OK -> showExamSavedConfirmationMessage("Exam added")
            EDIT_EXAM_RESULT_OK -> showExamSavedConfirmationMessage("Exam updated")
        }
    }

    private fun showExamSavedConfirmationMessage(text: String) = viewModelScope.launch {
        examsEventChannel.send(ExamsEvent.ShowExamSavedConfirmationMessage(text))
    }

    // Handle events for fragments (snackbar related); Belongs to ExamFragment, ExamEvent and ExamViewModel
    sealed class ExamsEvent {
        object NavigateToAddExamScreen : ExamsEvent()
        data class NavigateToEditExamScreen(val exam: Exam) : ExamsEvent()
        data class ShowUndoDeleteExamMessage(val exam: Exam) : ExamsEvent() // best practice to do this like this
        data class ShowExamSavedConfirmationMessage(val msg: String) : ExamsEvent()
    }
}

enum class SortOrder