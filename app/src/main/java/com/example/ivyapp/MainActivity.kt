package com.example.ivyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ivyapp.login.LoginFragment
import com.example.ivyapp.notificationDisplay.NotificationDisplayFragment
import com.example.ivyapp.patientsDisplay.PatientDisplayFragment
import com.google.android.material.navigation.NavigationView
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        navView.setNavigationItemSelectedListener{
//
//            it.isChecked = true
//
//            when(it.itemId) {
//                R.id.patientDisplayFragment -> replaceFragment(PatientDisplayFragment(), "Home")
//                R.id.notificationDisplayFragment -> replaceFragment(NotificationDisplayFragment(), "Notifications")
//                R.id.aboutFragment2 -> replaceFragment(AboutFragment(), "About")
////                R.id.aboutFragment -> replaceFragment(AboutFragment(), it.title.toString())
////                R.id.loginFragment -> replaceFragment(LoginFragment(), it.title.toString())
//            }
//
//            true
//        }


//        @Suppress("UNUSED VARIABLE")
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        drawerLayout = binding.drawerLayout

        // myNavHostFragment(activity_main.xml fragment) is the navController setup with navView
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)


        navController.addOnDestinationChangedListener{ _, destination, _ ->
            var fragmentName =  ""

            when (destination.id) {
                R.id.patientDisplayFragment -> fragmentName = "Home"
                R.id.notificationDisplayFragment -> fragmentName = "Notifications"
                R.id.aboutFragment2 -> fragmentName = "About"
                R.id.faqFragment -> fragmentName = "FAQ"
//                R.id.updatePatientFragment -> fragmentName = "Update"
            }

            if(destination.id==R.id.loginFragment || destination.id==R.id.registerFragment){
                supportActionBar?.hide()
                supportActionBar?.title = ""
            }
            else{
                supportActionBar?.show()
                supportActionBar?.title = fragmentName
            }
        }

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)
    }

//    private fun replaceFragment(fragment: Fragment, title: String) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.replaceFrame, fragment)
//        fragmentTransaction.commit()
//        drawerLayout.closeDrawers()
//        setTitle(title)
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected((item))) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }

//    override fun onBackPressed() {
//        Toast.makeText(this, "Do something", Toast.LENGTH_SHORT).show()
//    }
}