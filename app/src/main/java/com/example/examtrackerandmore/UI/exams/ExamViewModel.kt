package com.example.examtrackerandmore.UI.exams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.examtrackerandmore.data.ExamDataAccessObject
import kotlinx.coroutines.flow.MutableStateFlow

class ExamViewModel @ViewModelInject constructor(
    private val examDataAccessObject: ExamDataAccessObject
) : ViewModel() {

    val exams = examDataAccessObject.getExams().asLiveData() // liveData: ?

    //val SortOrder = MutableStateFlow()


}

enum class SortOrder