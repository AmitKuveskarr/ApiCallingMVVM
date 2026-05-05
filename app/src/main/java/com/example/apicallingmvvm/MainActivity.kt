package com.example.apicallingmvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.ItemDashboardPendingBinding
import com.example.apicallingmvvm.presentation.ui.RoomActivity
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.viewmodel.DashboardItemWisePendingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DashboardItemWisePendingViewModel by viewModels()

    private val pendingOrderList = ArrayList<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>()
    private var adapter: BaseGenericRecyclerViewAdapter<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>? = null

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
        

        viewModel.fetchItemWisePending("4110337", "0", "1", "0", "50", "4/30/2026")

        binding.btnGoToRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.itemWisePending.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { list ->
                        pendingOrderList.clear()
                        pendingOrderList.addAll(list)
                        adapter?.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                   
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = object : BaseGenericRecyclerViewAdapter<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>(pendingOrderList) {
            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val binding = ItemDashboardPendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, item: DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata) {
                (holder as ItemViewHolder).binding.apply {
                    tvPendingItemmName.text = item.itemName

                    Glide.with(holder.itemView.context)
                        .load("https://picsum.photos/200")
                        .into(ivItemImage)
                }
            }

            override fun getViewType(position: Int): Int = 0
        }

        binding.rvPendingOrder.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = this@MainActivity.adapter
        }
    }

    class ItemViewHolder(val binding: ItemDashboardPendingBinding) : RecyclerView.ViewHolder(binding.root)
}
