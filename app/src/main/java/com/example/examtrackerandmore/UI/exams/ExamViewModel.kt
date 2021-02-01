package com.example.examtrackerandmore.UI.exams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
    private val preferencesManager: PreferencesManager
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

    // Handle events for fragments (snackbar related); Belongs to ExamFragment, ExamEvent and ExamViewModel
    sealed class ExamsEvent {
        data class ShowUndoDeleteExamMessage(val exam: Exam) : ExamsEvent() // best practice to do this like this

    }


}

enum class SortOrder