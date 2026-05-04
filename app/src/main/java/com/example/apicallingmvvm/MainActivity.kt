package com.example.apicallingmvvm

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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallingmvvm.data.model.DashboardItemWisePendingResponse
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.data.prefs.PrefProvider
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.PendingOrdersRowBinding
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.utils.Utility
import com.example.apicallingmvvm.presentation.viewmodel.DashboardItemWisePendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DashboardItemWisePendingViewModel by viewModels()

    
    private val pendingOrderList =
        ArrayList<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>()

    private var adapterPendingOrderData: BaseGenericRecyclerViewAdapter<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>? =
        null

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
        fetchData()

        binding.btnGoToRoom.setOnClickListener {
            val intent = android.content.Intent(this, com.example.apicallingmvvm.presentation.ui.RoomActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchData() {
        Log.d("MainActivity", "fetchData: Triggering API call")
        viewModel.fetchItemWisePending(
            "4110337",
            "0",
            "1",
            "0",
            "50",
            "4/30/2026"
        )
    }

    private fun observeViewModel() {
        viewModel.ItemWisePending.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("MainActivity", "Success: Data received")
                    resource.data?.let { response ->
                        Log.d("MainActivity", "Response: $response")
                        val newList = response.flatMap { item ->
                            item.data.flatMap { data -> data.pendingdata }
                        }
                        Log.d("MainActivity", "Items count: ${newList.size}")
                        
                        pendingOrderList.clear()
                        pendingOrderList.addAll(newList)
                        adapterPendingOrderData?.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    Log.e("MainActivity", "Error: ${resource.message}")
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    Log.d("MainActivity", "Loading...")
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        adapterPendingOrderData = object :
            BaseGenericRecyclerViewAdapter<DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata>(
                pendingOrderList
            ) {
            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val binding = PendingOrdersRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ItemWisePendingOrderViewHolder(binding)
            }

            override fun getViewType(position: Int): Int = 0

            override fun onBindData(
                holder: RecyclerView.ViewHolder?,
                `val`: DashboardItemWisePendingResponse.DashboardItemWisePendingResponseItem.Pendingdata
            ) {
                val viewHolder = holder as ItemWisePendingOrderViewHolder
                viewHolder.binding.apply {
                    tvPendingPONumber.text = "PO: ${`val`.poNum}"
                    tvPendingDateValue.text = `val`.poDt
                    tvPendingAmntValue.text = Utility.formatToRupees(`val`.amount.toDoubleOrNull() ?: 0.0)
                    tvPendingQuantity.text = "Qty: ${`val`.pendingQty}"
                    tvPendingItemmName.text = `val`.itemName
                }
            }
        }

        binding.rvPendingOrder.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterPendingOrderData
        }
    }

    class ItemWisePendingOrderViewHolder(val binding: PendingOrdersRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}
