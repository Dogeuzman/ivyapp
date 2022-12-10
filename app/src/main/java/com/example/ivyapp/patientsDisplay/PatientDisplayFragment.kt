package com.example.ivyapp.patientsDisplay

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivyapp.MainActivity
import com.example.ivyapp.R
import com.example.ivyapp.databinding.FragmentPatientDisplayBinding
import com.example.ivyapp.notificationDisplay.NotificationDisplayFragment
import com.example.ivyapp.patientDatabase.PatientDatabase
import com.example.ivyapp.patientDatabase.PatientRepository
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_patient_display.*


class PatientDisplayFragment : Fragment() {

    private lateinit var patientDisplayViewModel: PatientDisplayViewModel
//    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPatientDisplayBinding  = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_patient_display,
            container,
            false
        )

//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//
//        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.Open, R.string.Close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//
//        navView.setNavigationItemSelectedListener {
//
//            it.isChecked = true
//
//            when (it.itemId) {
//                R.id.patientDisplayFragment -> Toast.makeText(activity, "This is the home page", Toast.LENGTH_SHORT).show()
//                R.id.notificationDisplayFragment -> {
//                    val action = PatientDisplayFragmentDirections.actionPatientDisplayFragmentToNotificationDisplayFragment()
//                    NavHostFragment.findNavController(this).navigate(action)
//                }
//            }
//
//            true
//        }

        val application = requireNotNull(this.activity).application
        val dao = PatientDatabase.getInstance(application).patientDao
        val repository = PatientRepository(dao)
        val factory = PatientDisplayViewModelFactory(repository)
        patientDisplayViewModel = ViewModelProvider(this, factory).get(PatientDisplayViewModel::class.java)

        binding.patientDisplayViewModel = patientDisplayViewModel

        val adapter = PatientDisplayListAdapter(PatientListener { patient ->
            patientDisplayViewModel.initUpdateAndDelete(patient)
        })

        binding.patientRecyclerView.adapter = adapter

        patientDisplayViewModel.patients.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        patientDisplayViewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })


        patientDisplayViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = PatientDisplayFragmentDirections.actionPatientDisplayFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                patientDisplayViewModel.doneNavigatingToLogin()
            }
        })

        binding.patientRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

//    private fun replaceFragment(fragment: Fragment, title: String) {
//        val fragmentManger = (activity as AppCompatActivity).supportFragmentManager
//        val fragmentTransaction = fragmentManger.beginTransaction()
//        fragmentTransaction.replace(R.id.patientDisplayFragment, fragment)
//        fragmentTransaction.commit()
//        drawerLayout.closeDrawers()
//        (activity as AppCompatActivity).title = title
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}