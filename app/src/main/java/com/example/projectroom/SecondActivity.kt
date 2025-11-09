package com.example.projectroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSave: Button
    private lateinit var db: AppDatabase
    private var existingItem: ItemEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonSave = findViewById(R.id.buttonSave)
        db = AppDatabase.getDatabase(this)

        existingItem = intent.getSerializableExtra("item") as? ItemEntity

        existingItem?.let {
            editTextTitle.setText(it.title)
            editTextDescription.setText(it.description)
        }

        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            if (title.isNotEmpty() && description.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (existingItem == null) {
                        db.itemDao().insertItem(ItemEntity(title = title, description = description))
                    } else {
                        existingItem!!.title = title
                        existingItem!!.description = description
                        db.itemDao().updateItem(existingItem!!)
                    }
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }
}