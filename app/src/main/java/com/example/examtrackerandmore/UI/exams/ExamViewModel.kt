package com.example.examtrackerandmore.UI.exams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.examtrackerandmore.data.ExamDataAccessObject

class ExamViewModel @ViewModelInject constructor(
    private val examDataAccessObject: ExamDataAccessObject
) : ViewModel() {

    val exams = examDataAccessObject.getExams().asLiveData() // liveData: ?
}