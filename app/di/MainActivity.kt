package com.example.apicallingmvvm.di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallingmvvm.di.network.Resource
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.ItemRowBinding
import com.example.apicallingmvvm.di.ui.DataViewModel
import com.example.apicallingmvvm.model.UserResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataViewModel by viewModels()
    private val dataList = ArrayList<UserResponse.UserResponseItem.Data>()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpRecyclerView()
        observeViewModel()

        viewModel.fetchUser("4110337", "Abc")
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    resource.data?.let { list ->
                        dataList.clear()
                        list.forEach { userResponseItem ->
                            dataList.addAll(userResponseItem.data)
                        }
                        userAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = resource.message
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter(dataList)
        binding.rvPendingOrder.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    class UserAdapter(private val items: List<UserResponse.UserResponseItem.Data>) :
        RecyclerView.Adapter<UserAdapter.ViewHolder>() {

        class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.binding.tvItemmName.text = item.slno.toString()
        }

        override fun getItemCount(): Int = items.size
    }
}