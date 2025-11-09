package com.example.projectroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.projectroom.databinding.ActivitySecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val itemViewModel: ItemViewModel by viewModels()
    private var existingItem: ItemEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        existingItem = intent.getSerializableExtra("item") as? ItemEntity
        existingItem?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextDescription.setText(it.description)
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (existingItem == null) {
                    itemViewModel.insert(ItemEntity(title = title, description = description))
                } else {
                    existingItem!!.title = title
                    existingItem!!.description = description
                    itemViewModel.update(existingItem!!)
                }
                finish()
            }
        }
    }
}