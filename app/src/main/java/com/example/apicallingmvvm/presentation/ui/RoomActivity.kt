package com.example.apicallingmvvm.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallingmvvm.data.local.entities.User
import com.example.apicallingmvvm.databinding.ActivityRoomBinding
import com.example.apicallingmvvm.databinding.PendingOrdersRowBinding
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private val viewModel: RoomViewModel by viewModels()
    private val userList = ArrayList<User>()
    private var adapter: BaseGenericRecyclerViewAdapter<User>? = null
    private var userToUpdate: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.btnAddUser.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageStr = binding.etAge.text.toString()

            if (name.isNotEmpty() && ageStr.isNotEmpty()) {
                val age = ageStr.toIntOrNull() ?: 0
                
                if (userToUpdate == null) {
                    // Normal Add
                    val user = User(name = name, age = age)
                    viewModel.insert(user)
                } else {
                    // Update existing
                    val updatedUser = userToUpdate!!.copy(name = name, age = age)
                    viewModel.update(updatedUser)
                    
                    // Reset to Add mode
                    userToUpdate = null
                    binding.btnAddUser.text = "Add User"
                }
                
                binding.etName.text.clear()
                binding.etAge.text.clear()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteAllUsers.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    private fun observeViewModel() {
        viewModel.allUsers.observe(this) { users ->
            userList.clear()
            userList.addAll(users)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        adapter = object : BaseGenericRecyclerViewAdapter<User>(userList) {
            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val binding = PendingOrdersRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UserViewHolder(binding)
            }

            override fun getViewType(position: Int): Int = 0

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: User) {
                val viewHolder = holder as UserViewHolder
                viewHolder.binding.apply {
                    tvPendingItemmName.text = `val`.name
                    tvPendingPONumber.text = "Age: ${`val`.age}"
                    
                    root.setOnClickListener {
                        // Enter Edit Mode
                        userToUpdate = `val`
                        binding.etName.setText(`val`.name)
                        binding.etAge.setText(`val`.age.toString())
                        binding.btnAddUser.text = "Update User"
                    }
                    
                    root.setOnLongClickListener {
                        viewModel.delete(`val`)
                        true
                    }
                }
            }
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    class UserViewHolder(val binding: PendingOrdersRowBinding) : RecyclerView.ViewHolder(binding.root)
}
