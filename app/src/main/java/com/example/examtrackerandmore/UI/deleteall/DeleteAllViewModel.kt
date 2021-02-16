package com.example.examtrackerandmore.UI.deleteall

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.examtrackerandmore.data.ExamDataAccessObject
import com.example.examtrackerandmore.dependencyinjection.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllViewModel @ViewModelInject constructor(
    private val examDao: ExamDataAccessObject,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        examDao.deleteExams()
    }
}