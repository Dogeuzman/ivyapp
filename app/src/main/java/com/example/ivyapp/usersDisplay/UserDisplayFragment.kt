package com.example.ivyapp.usersDisplay

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ivyapp.R
import com.example.ivyapp.database.User
import com.example.ivyapp.database.UserDatabase
import com.example.ivyapp.database.UsersRepository
import com.example.ivyapp.databinding.FragmentUserDisplayBinding


class UserDisplayFragment : Fragment() {

    private lateinit var userDisplayViewModel: UserDisplayViewModel
    private lateinit var binding: FragmentUserDisplayBinding
//    private lateinit var adapter: UserDisplayRecyclerViewAdapter
    private lateinit var adapter: UserDisplayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentUserDisplayBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_display,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UsersRepository(dao)
        val factory = UserDisplayViewModelFactory(repository)
        userDisplayViewModel = ViewModelProvider(this, factory).get(UserDisplayViewModel::class.java)

        binding.userDisplayViewModel = userDisplayViewModel

        val adapter = UserDisplayListAdapter(UserListener { user ->
            listItemClicked(user)
        })

        binding.userRecyclerView.adapter = adapter

        userDisplayViewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        userDisplayViewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

//        userDisplayViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                val action = UserDisplayFragmentDirections.actionUserDisplayFragmentToLoginFragment()
//                NavHostFragment.findNavController(this).navigate(action)
//                userDisplayViewModel.doneNavigatingToLogin()
//            }
//        })



        binding.userRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.lifecycleOwner = this

//        binding.userRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        adapter = UserDisplayRecyclerViewAdapter({selectedItem: User -> listItemClicked(selectedItem)})
//        binding.userRecyclerView.adapter = adapter
//
//        userDisplayViewModel.users.observe(viewLifecycleOwner, Observer {
//            Log.i("MYTAG", it.toString())
//            adapter.setList(it)
//            adapter.notifyDataSetChanged()
////            binding.userRecyclerView.adapter = UserDisplayRecyclerViewAdapter(it, {selectedItem: User -> listItemClicked(selectedItem)})
//        })
//
//        userDisplayViewModel.statusMessage.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let {
//                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
//            }
//        })


        return binding.root
    }

    private fun listItemClicked(user: User) {
//        Toast.makeText(activity, "Selected User: ${user.name}", Toast.LENGTH_SHORT).show()
        userDisplayViewModel.initUpdateAndDelete(user)
    }

}