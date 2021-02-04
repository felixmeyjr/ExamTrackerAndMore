package com.example.examtrackerandmore.UI.addeditexam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.examtrackerandmore.R
import com.example.examtrackerandmore.databinding.FragmentAddEditExamBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditExamFragment : Fragment(R.layout.fragment_add_edit_exam) {

    // Obtain the viewmodel component
    private val viewModel : AddEditExamViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate view and obtain instance of responding Fragment
        val binding = FragmentAddEditExamBinding.bind(view)

        // todo fix
        binding.apply {
            editTextExamName.setText(viewModel.examName)
            editTextExamDate.setText(viewModel.examDate)
            editTextNoLecs.setText(viewModel.numberLecs?.let { Integer.toString(it) })
            editTextNoTuts.setText(viewModel.numberTuts?.let { Integer.toString(it) })
        }
    }
}