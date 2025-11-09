package com.example.projectroom

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectroom.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val itemViewModel: ItemViewModel by viewModels()

    private val editAddLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemAdapter(listOf(),
            onEditClick = { item -> editItem(item) },
            onDeleteClick = { item -> itemViewModel.delete(item) }
        )

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        // Observe DB data (LiveData)
        itemViewModel.allItems.observe(this) { list ->
            adapter.updateList(list)
        }

        binding.favbuton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            editAddLauncher.launch(intent)
        }
    }

    private fun editItem(item: ItemEntity) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("item", item)
        editAddLauncher.launch(intent)
    }
}