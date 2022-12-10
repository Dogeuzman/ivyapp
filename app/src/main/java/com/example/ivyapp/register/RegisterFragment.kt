package com.example.ivyapp.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.ivyapp.R
import com.example.ivyapp.database.UserDatabase
import com.example.ivyapp.database.UsersRepository
import com.example.ivyapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )


        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UsersRepository(dao)
        val factory = RegisterViewModelFactory(repository)
        val registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.registerViewModel = registerViewModel


        registerViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                registerViewModel.doneNavigatingToLogin()
            }
        })

        registerViewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })


        binding.lifecycleOwner = this
        return binding.root
    }

}