package com.example.examtrackerandmore.UI.exams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.examtrackerandmore.data.ExamDataAccessObject
import com.example.examtrackerandmore.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
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

    // Updating the preference here
    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }


}

enum class SortOrder