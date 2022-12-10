package com.example.ivyapp.patientsDisplay

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ivyapp.databinding.PatientItemBinding
import com.example.ivyapp.patientDatabase.Patient

class PatientDisplayListAdapter(val clickListener: PatientListener): ListAdapter<Patient, PatientDisplayListAdapter.PatientViewHolder>(PatientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient!!, clickListener)
    }


    class PatientViewHolder private constructor(val binding: PatientItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(patient: Patient, clickListener: PatientListener) {
            binding.patient = patient
            binding.clickListener = clickListener
            binding.firstNameTextView.text = patient.patientFirstName
            binding.lastNameTextView.text = patient.patientLastName
            binding.ivPumpUnitNumTextView.text = "Unit No: ${patient.infusionPumpUnitNum}"
            binding.flowRateTextView.text = "Flow Rate: ${patient.flowRate} ml/hr"
        }

        companion object {
            fun from(parent: ViewGroup): PatientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PatientItemBinding.inflate(layoutInflater, parent, false)
                return PatientViewHolder(binding)
            }
        }

    }

}

class PatientDiffCallback: DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.patientId == newItem.patientId
    }

    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }

}

class PatientListener(val clickListener: (Patient) -> Unit) {
    fun onClick(patient: Patient) = clickListener(patient)
}