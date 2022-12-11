package com.example.ivyapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ivyapp.notificationDisplay.NotificationApiStatus
import com.example.ivyapp.patientDatabase.Patient

@BindingAdapter("notificationApiStatus")
fun bindStatus(statusImageView: ImageView, status: NotificationApiStatus?) {
    when (status) {
        NotificationApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NotificationApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("patientIVPumpUnitNum")
fun TextView.setIVPumpUnitNum(item: Patient?) {
    item?.let {
        text = item.infusionPumpUnitNum.toString()
    }
}


@BindingAdapter("patientFlowRate")
fun TextView.setFlowRate(item: Patient?) {
    item?.let {
        text = item.flowRate.toString()
    }
}