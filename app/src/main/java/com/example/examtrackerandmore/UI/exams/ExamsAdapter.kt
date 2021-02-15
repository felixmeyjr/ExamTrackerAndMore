package com.example.examtrackerandmore.UI.exams

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examtrackerandmore.data.Exam
import com.example.examtrackerandmore.databinding.ItemExamBinding

// TODO: 27.01.2021 what does this do? #5
// extend ListAdapter (extension of Reclycer view)
class ExamsAdapter(private val listener: OnItemClickListener) : ListAdapter<Exam, ExamsAdapter.ExamsViewHolder>(DiffCallback()) {

    // TODO: 27.01.2021 add documentation from #5
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamsViewHolder {
        val binding = ItemExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExamsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem) // bind item at correct position with logic below
    }

    inner class ExamsViewHolder(private val binding: ItemExamBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val exam = getItem(position)
                        listener.onItemClick(exam)
                    }
                }
                // TODO #10 maybe add other items
                }
            }


        // which data should get in which view -> id
        fun bind(exam: Exam) {
            binding.apply {
                // which data should be put where under what conditions?
                // define behaviour of inputs
                textExamName.text = exam.name
                //textDaysToExam.setText(Integer.toString(exam.daysLeft))
                textNoLecs.setText(Integer.toString(exam.no_lecs))
                textNoTuts.setText(Integer.toString(exam.no_tuts))
                // TODO: 27.01.2021 fix this warning
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(exam: Exam)
    }

    class DiffCallback : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Exam, newItem: Exam) =
            oldItem == newItem // == because data class (which has comparison function)

    }
}