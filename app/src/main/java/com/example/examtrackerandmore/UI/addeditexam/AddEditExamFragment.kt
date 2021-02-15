package com.example.examtrackerandmore.UI.addeditexam

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.examtrackerandmore.R
import com.example.examtrackerandmore.UI.util.exhaustive
import com.example.examtrackerandmore.databinding.FragmentAddEditExamBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditExamFragment : Fragment(R.layout.fragment_add_edit_exam) {

    // Obtain the viewmodel component
    private val viewModel : AddEditExamViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate view and obtain instance of responding Fragment
        val binding = FragmentAddEditExamBinding.bind(view)

        // in Apply block the views can be accessed
        // todo fix
        binding.apply {
            editTextExamName.setText(viewModel.examName)
            editTextExamDate.setText(viewModel.examDate)
            editTextNoLecs.setText(Integer.toString(viewModel.numberLecs))
            editTextNoTuts.setText(Integer.toString(viewModel.numberTuts))

            editTextExamName.addTextChangedListener {
                viewModel.examName = it.toString()
            }

            editTextExamDate.addTextChangedListener {
                viewModel.examDate = it.toString()
            }
            // todo add for noLecs and noTut
//            editTextNoLecs.addTextChangedListener {
//                viewModel.numberLecs = it.toIntOrNull()
//            }

            fabCheckMark.setOnClickListener {
                viewModel.onSaveClick()
            }




        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditExamEvent.collect { event ->
                when (event) {
                    is AddEditExamViewModel.AddEditExamEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditExamViewModel.AddEditExamEvent.NavigateBackWithResult -> {
                        binding.editTextExamName.clearFocus()
                        binding.editTextExamDate.clearFocus() // wipe existing input
                        binding.editTextNoLecs.clearFocus()
                        binding.editTextNoTuts.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}

