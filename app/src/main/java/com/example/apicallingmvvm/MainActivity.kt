package com.example.apicallingmvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.ItemDashboardPendingBinding
import com.example.apicallingmvvm.presentation.ui.RoomActivity
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataViewModel by viewModels()

//    private val dataList = ArrayList<>()
//    private var adapter: BaseGenericRecyclerViewAdapter<>? = null

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

//        setUpRecyclerView()
//        observeViewModel()
        

//        viewModel.fetchdata("4")

        binding.btnGoToRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
    }

//    private fun observeViewModel() {
//        viewModel.Data.observe(this) { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    resource.data?.let { list ->
//                        dataList.clear()
//                        dataList.addAll(list)
//                        adapter?.notifyDataSetChanged()
//                    }
//                }
//                is Resource.Error -> {
//                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_LONG).show()
//                }
//                is Resource.Loading -> {
//
//                }
//            }
//        }
//    }

//    private fun setUpRecyclerView() {
//        adapter = object : BaseGenericRecyclerViewAdapter<>(dataList) {
//            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//                val binding = ItemDashboardPendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                return ItemViewHolder(binding)
//            }
//
//            override fun onBindData(holder: RecyclerView.ViewHolder?, item: ) {
//                (holder as ItemViewHolder).binding.apply {
//                    tvName.text = item.itemName
//
//                    Glide.with(holder.itemView.context)
//                        .load("https://picsum.photos/200")
//                        .into(ivItemImage)
//                }
//            }
//
//            override fun getViewType(position: Int): Int = 0
//        }
//
//        binding.rvPendingOrder.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            this.adapter = this@MainActivity.adapter
//        }
//    }

    class ItemViewHolder(val binding: ItemDashboardPendingBinding) : RecyclerView.ViewHolder(binding.root)
}
