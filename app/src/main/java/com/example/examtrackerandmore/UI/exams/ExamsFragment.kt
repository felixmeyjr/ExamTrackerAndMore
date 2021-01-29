package com.example.examtrackerandmore.UI.exams

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examtrackerandmore.R
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.databinding.FragmentExamsOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

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


                // how to manage items on screen (horizonzal, vertical...)
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

            }
        }

        viewModel.exams.observe(viewLifecycleOwner) {
            // always sort items by days left to exam


            examAdapter.submitList(it)
        }

        // Activate options menu when creating
        setHasOptionsMenu(true)
    }

    // Connect menu fragment with oncreate
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_exams, menu)
    }
}