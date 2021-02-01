package com.example.examtrackerandmore.UI.exams

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examtrackerandmore.R
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.databinding.FragmentExamsOverviewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Add Logic for fragment_exams_overview here

// TODO: 27.01.2021 add documentation of #5 

@AndroidEntryPoint
class ExamsFragment : Fragment(R.layout.fragment_exams_overview) {

    private val viewModel: ExamViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentExamsOverviewBinding.bind(view)

        // examAdapter: create one object as fragment todo ?
        val examAdapter = ExamsAdapter()

        binding.apply {
            recyclerViewExams.apply {
                adapter = examAdapter


                // how to manage items on screen (horizontal, vertical...)
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

                // #10 Swipe to delete
                // DragDirs: drag and drop (vertical)
                // SwipeDirs: Left and right (delete for example)
                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    // swipe function; viewholder = current Item that should be moved
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        // Get current item at position of viewholder
                        val exam = examAdapter.currentList[viewHolder.adapterPosition]
                        viewModel.onExamSwiped(exam)
                    }
                }).attachToRecyclerView(recyclerViewExams)
            }
        }

        viewModel.exams.observe(viewLifecycleOwner) {
            // always sort items by days left to exam


            examAdapter.submitList(it)
        }

        // Flow can only be selected from Coroutine -> dont block UI thread
        // Can be done way more easier -> Handling in fragment
        // Show snackbar with UNDO option on UI
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.examsEvent.collect { event ->
                // Get stream of values: examsEventObject
                when(event) {
                    is ExamViewModel.ExamsEvent.ShowUndoDeleteExamMessage -> {
                        Snackbar.make(requireView(), "Exam deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                // add item back to db
                                viewModel.onUndoDeleteClick(event.exam)
                            }.show()
                    }
                }
            }
        }

        // Activate options menu when creating
        setHasOptionsMenu(true)
    }

    // Connect menu fragment with on create
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_exams, menu)

        // Get preferences from data storage
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_finished_exams).isChecked =
                viewModel.preferencesFlow.first().hideCompleted // read from data and get the bool from hide finished
        }
    }

    // Updates fragment with OptionItem
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_hide_finished_exams -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedClick(item.isChecked)
                true
            }
            R.id.action_delete_all_finished -> {

                true
            }
            R.id.action_delete_all -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}