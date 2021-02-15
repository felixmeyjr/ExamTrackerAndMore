package com.example.examtrackerandmore.UI.addeditexam

import android.provider.SyncStateContract.Helpers.insert
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrackerandmore.UI.ADD_EXAM_RESULT_OK
import com.example.examtrackerandmore.UI.EDIT_EXAM_RESULT_OK
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.data.ExamDataAccessObject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * ViewModel holds data of add edit fragment
 */

class AddEditExamViewModel @ViewModelInject constructor(
    private val examDao: ExamDataAccessObject,
    @Assisted private val state: SavedStateHandle // SavedStateHandle saves data temporarily
) : ViewModel() {


    val exam = state.get<Exam>("exam")

    var examName = state.get<String>("examName") ?: exam?.name ?: "" // catch NULL
        set(value) {
        field = value
        state.set("examName", value)
        }

    var examDate = state.get<String>("examDate") ?: exam?.date ?: "" // catch NULL
        set(value) {
            field = value
            state.set("examDate", value)
        }

    var numberLecs = state.get<Int>("numberLecs") ?: exam?.no_lecs ?: 0 // catch NULL
        set(value) {
            field = value
            state.set("numberLecs", value)
        }

    var numberTuts = state.get<Int>("numberTuts") ?: exam?.no_tuts ?: 0
        set(value) {
            field = value
            state.set("numberTuts", value)
        }

    private val addEditExamEventChannel = Channel<AddEditExamEvent>()
    val addEditExamEvent = addEditExamEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (examName.isBlank()) {
            // Show invalid input message
            showInvalidInputMessage("Name cannot be empty")
            return
        }

        if (exam != null) {
            // Update existing exam
            val updatedExam = exam.copy(
                name = examName,
                date = examDate,
                no_lecs = numberLecs,
                no_tuts = numberTuts
            )
            updateExam(updatedExam)
        } else {
            val newExam =
                Exam(name = examName, date = examDate, no_lecs = numberLecs, no_tuts = numberTuts)
            createExam(newExam)
        }
    }

    private fun createExam(exam: Exam) = viewModelScope.launch {
        examDao.insert(exam)
        // navigate back
        addEditExamEventChannel.send(AddEditExamEvent.NavigateBackWithResult(ADD_EXAM_RESULT_OK))

    }

    private fun updateExam(exam: Exam) = viewModelScope.launch {
        examDao.update(exam)
        // navigate back
        addEditExamEventChannel.send(AddEditExamEvent.NavigateBackWithResult(EDIT_EXAM_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditExamEventChannel.send(AddEditExamEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditExamEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditExamEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditExamEvent()
    }
}