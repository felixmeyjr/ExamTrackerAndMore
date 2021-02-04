package com.example.examtrackerandmore.UI.addeditexam

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.data.ExamDataAccessObject

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

    var numberTuts = state.get<Int>("numberTuts") ?: exam?.no_tuts ?: 0 // catch NULL
        set(value) {
            field = value
            state.set("numberTuts", value)
        }


}