package com.example.ivyapp.notificationDisplay

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivyapp.R
import com.example.ivyapp.databinding.FragmentNotificationDisplayBinding

class NotificationDisplayFragment : Fragment() {

    private lateinit var notificationDisplayViewModel: NotificationDisplayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNotificationDisplayBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notification_display,
            container,
            false
        )

        // val application = requireNotNull(this.activity).application
        val factory = NotificationDisplayViewModelFactory()
        notificationDisplayViewModel = ViewModelProvider(this, factory).get(NotificationDisplayViewModel::class.java)

        binding.notificationDisplayViewModel = notificationDisplayViewModel

        val adapter = NotificationDisplayListAdapter()

        binding.notificationRecyclerView.adapter = adapter

        notificationDisplayViewModel.notifications.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}