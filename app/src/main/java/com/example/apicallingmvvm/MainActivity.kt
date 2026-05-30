package com.example.apicallingmvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallingmvvm.data.local.model.Todo
import com.example.apicallingmvvm.data.network.Resource
import com.example.apicallingmvvm.databinding.ActivityMainBinding
import com.example.apicallingmvvm.databinding.ItemDashboardPendingBinding
import com.example.apicallingmvvm.presentation.ui.adapter.BaseGenericRecyclerViewAdapter
import com.example.apicallingmvvm.presentation.viewmodel.DataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataViewModel by viewModels()

    private val dataList = ArrayList<Todo>()
    private var adapter: BaseGenericRecyclerViewAdapter<Todo>? = null

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
        viewModel.fetchUser()

        setUpRecyclerView()
        observeViewModel()

        binding.fabAdd.setOnClickListener {
            showBottomSheet()
        }
    }


    private fun observeViewModel() {
        viewModel.users.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    
                    resource.data?.let { list ->
                        Log.d("MainActivity", "Resource.Success: Received ${list.size} items")
                        dataList.clear()
                        dataList.addAll(list)
                        Log.d("MainActivity", "Total dataList size: ${dataList.size}")
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

        viewModel.todoResult.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Operation successful: ${resource.data?.title}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.deleteResult.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_crud, null)
        val etId = view.findViewById<EditText>(R.id.etId)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        btnCreate.setOnClickListener {
            val title = etTitle.text.toString()
            if (title.isNotEmpty()) {
                viewModel.createTodo(Todo(title = title, completed = false, userId = 1))
                dialog.dismiss()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_title), Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val idStr = etId.text.toString()
            val title = etTitle.text.toString()
            if (idStr.isNotEmpty() && title.isNotEmpty()) {
                val id = idStr.toInt()
                viewModel.updateTodo(id, Todo(id = id, title = title, completed = false, userId = 1))
                dialog.dismiss()
            } else if (idStr.isEmpty()) {
                Toast.makeText(this, getString(R.string.please_enter_id), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_title), Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val idStr = etId.text.toString()
            if (idStr.isNotEmpty()) {
                viewModel.deleteTodo(idStr.toInt())
                dialog.dismiss()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_id), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun setUpRecyclerView() {
        adapter = object : BaseGenericRecyclerViewAdapter<Todo>(dataList) {
            override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val binding = ItemDashboardPendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, item: Todo) {
                Log.d("MainActivity", "onBindData: id = ${item.id}")
                (holder as ItemViewHolder).binding.apply {
                    tvName.text = "User ID: ${item.userId}\nTitle: ${item.title}"
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