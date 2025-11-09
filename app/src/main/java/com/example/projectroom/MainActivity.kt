package com.example.projectroom

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabButton: FloatingActionButton
    private lateinit var adapter: ItemAdapter
    private lateinit var db: AppDatabase
    private val itemList = mutableListOf<ItemEntity>()

    private val editAddLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                loadItemsFromDB()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        recyclerView = findViewById(R.id.recyclerview)
        fabButton = findViewById(R.id.favbuton)

        adapter = ItemAdapter(itemList,
            onEditClick = { item -> editItem(item) },
            onDeleteClick = { item -> deleteItem(item) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            editAddLauncher.launch(intent)
        }

        loadItemsFromDB()
    }

    private fun loadItemsFromDB() {
        lifecycleScope.launch(Dispatchers.IO) {
            val items = db.itemDao().getAllItems()
            withContext(Dispatchers.Main) {
                adapter.updateList(items)
            }
        }
    }

    private fun deleteItem(item: ItemEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.itemDao().deleteItem(item)
            loadItemsFromDB()
        }
    }

    private fun editItem(item: ItemEntity) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("item", item)
        editAddLauncher.launch(intent)
    }
}