package com.example.apicallingmvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallingmvvm.data.local.model.UserResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.ItemDashboardPendingBinding
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataViewModel by viewModels()

    private val dataList = ArrayList<UserResponse.UserResponseItem.Data>()
    private val allDataList = ArrayList<UserResponse.UserResponseItem.Data>()
    private var adapter: BaseGenericRecyclerViewAdapter<UserResponse.UserResponseItem.Data>? = null

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
        viewModel.fetchUser("4110337")

        setUpRecyclerView()
        observeViewModel()




    }


    private fun observeViewModel() {
        viewModel.users.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    binding.tvError.visibility = View.GONE
                    
                    resource.data?.let { list ->
                        dataList.clear()
//                        allDataList.clear()
                        list.forEach { userResponseItem ->
                            dataList.addAll(userResponseItem.data)
//                            allDataList.addAll(userResponseItem.data)
                        }
                        adapter?.notifyDataSetChanged()
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
        adapter = object : BaseGenericRecyclerViewAdapter<UserResponse.UserResponseItem.Data>(dataList) {
            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val binding = ItemDashboardPendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, item: UserResponse.UserResponseItem.Data) {
                (holder as ItemViewHolder).binding.apply {
                    tvName.text = item.slno.toString()

                }
            }

            override fun getViewType(position: Int): Int = 0
        }

        binding.rvPendingOrder.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = this@MainActivity.adapter
        }
    }


//    private fun se() {
//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//
//                dataList.clear()
//
//                if (p0.isNullOrEmpty()) {
//                    dataList.addAll(allDataList)
//                } else {
//                    val filtered = allDataList.filter {
//                        it.slno.toString().contains(p0, ignoreCase = true)
//                    }
//                    dataList.addAll(filtered)
//                }
//
//                adapter?.notifyDataSetChanged()
//
//                return true
//            }
//        })
//    }

    class ItemViewHolder(val binding: ItemDashboardPendingBinding) : RecyclerView.ViewHolder(binding.root)
}