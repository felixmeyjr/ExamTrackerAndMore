package com.example.examtrackerandmore.UI.exams

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.examtrackerandmore.R
import com.example.examtrackerandmore.data.Exam
import dagger.hilt.android.AndroidEntryPoint

// Add Logic for fragment_exams_overview here

@AndroidEntryPoint
class ExamsFragment : Fragment(R.layout.fragment_exams_overview) {

    private val viewModel: ExamViewModel by viewModels()
}