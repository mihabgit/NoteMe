package com.imran.noteme.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.imran.noteme.R
import com.imran.noteme.db.NoteDatabase
import com.imran.noteme.db.model.Note
import com.imran.noteme.db.repository.NoteRepository
import com.imran.noteme.util.CommonTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var tilTaskName: TextInputLayout
    private lateinit var tilTaskDescription: TextInputLayout
    private lateinit var tilDeadline: TextInputLayout
    private lateinit var tilStatus: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilUrl: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NoteMe)
        setContentView(R.layout.activity_add_task)

        setupToolbar()

        val noteRepository = NoteRepository(NoteDatabase(this))

        tilTaskName = findViewById(R.id.tilTaskName)
        tilTaskDescription = findViewById(R.id.tilTaskDescription)
        tilDeadline = findViewById(R.id.tilDeadline)
        tilStatus = findViewById(R.id.tilStatus)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilUrl = findViewById(R.id.tilUrl)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            CoroutineScope(Main).launch {

                noteRepository.insertNote(getNoteData())
                finish()
            }
        }

        tilDeadline.editText?.setOnClickListener { CommonTask().showDatePickerWithMinDate(this, tilDeadline) }
        tilStatus.editText?.setOnClickListener { CommonTask().showStatusList(this, tilStatus, populateStatusList()) }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getNoteData(): Note {
        val title = tilTaskName.editText?.text.toString().trim()
        val desc = tilTaskDescription.editText?.text.toString().trim()
        val deadline = tilDeadline.editText?.text.toString().trim()
        val status = tilStatus.editText?.text.toString().trim()
        val email = tilEmail.editText?.text.toString().trim()
        val phone = tilPhone.editText?.text.toString().trim()
        val url = tilUrl.editText?.text.toString().trim()

        val currentDate = Calendar.getInstance().time

        return Note(title = title, description = desc, createdAt = currentDate , deadLine = deadline,
            status = status, email = email, phone = phone, url = url)
    }

    private fun populateStatusList(): List<String> {
        val statusList = mutableListOf<String>()
        statusList.add("Open")
        statusList.add("In-progress")
        statusList.add("Test")
        statusList.add("Done")

        return statusList
    }
}