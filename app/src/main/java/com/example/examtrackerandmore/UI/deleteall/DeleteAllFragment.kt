package com.example.examtrackerandmore.UI.deleteall

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */

@AndroidEntryPoint
class DeleteAllFragment : DialogFragment() {

    private val viewModel : DeleteAllViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Do you really want to delete all exams?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                // Call viewModel to delete operation
                viewModel.onConfirmClick()
            }
            .create()
}