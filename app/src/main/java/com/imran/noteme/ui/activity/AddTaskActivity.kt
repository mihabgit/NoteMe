package com.imran.noteme.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.imran.noteme.R
import com.imran.noteme.db.NoteDatabase
import com.imran.noteme.db.model.Note
import com.imran.noteme.db.repository.NoteRepository
import com.imran.noteme.util.CommonTask
import com.imran.noteme.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
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

        val noteForEdit = intent.getParcelableExtra<Note>(Constants.NOTE_DATA)
        val noteRepository = NoteRepository(NoteDatabase(this))

        tvTitle = findViewById(R.id.tvTitle)
        tilTaskName = findViewById(R.id.tilTaskName)
        tilTaskDescription = findViewById(R.id.tilTaskDescription)
        tilDeadline = findViewById(R.id.tilDeadline)
        tilStatus = findViewById(R.id.tilStatus)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilUrl = findViewById(R.id.tilUrl)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        if (noteForEdit != null) {
            tvTitle.text = getString(R.string.edit_task)
            btnSubmit.text = getString(R.string.update)
        } else {
            tvTitle.text = getString(R.string.add_task)
            btnSubmit.text = getString(R.string.submit)
        }


        btnSubmit.setOnClickListener {
            CoroutineScope(Main).launch {
                if (isValid()) {
                    if (noteForEdit?.id != null) {
                        val note = getNoteData()
                        note.id = noteForEdit.id
                        noteRepository.insertNote(note)
                    } else {
                        noteRepository.insertNote(getNoteData())
                    }
                    //finish()
                    showSuccessDialog()
                }
            }
        }

        tilDeadline.editText?.setOnClickListener {
            CommonTask().showDatePickerWithMinDate(
                this,
                tilDeadline
            )
        }
        tilStatus.editText?.setOnClickListener {
            CommonTask().showStatusList(
                this,
                tilStatus,
                populateStatusList()
            )
        }

        setNoteData(noteForEdit)
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

    private fun setNoteData(note: Note?) {
        tilTaskName.editText?.setText(note?.title)
        tilTaskDescription.editText?.setText(note?.description)
        tilDeadline.editText?.setText(note?.deadLine)
        tilStatus.editText?.setText(note?.status)
        tilEmail.editText?.setText(note?.email)
        tilPhone.editText?.setText(note?.phone)
        tilUrl.editText?.setText(note?.url)
    }

    private fun getNoteData(): Note {
        val title = tilTaskName.editText?.text.toString().trim()
        val desc = tilTaskDescription.editText?.text.toString().trim()
        val deadline = tilDeadline.editText?.text.toString().trim()
        val status = tilStatus.editText?.text.toString().trim()
        val email = tilEmail.editText?.text.toString().trim()
        val phone = tilPhone.editText?.text.toString().trim()
        val url = tilUrl.editText?.text.toString().trim()

        //val currentDate = Calendar.getInstance().time
        val currentDate = System.currentTimeMillis()

        return Note(
            title = title, description = desc, createdAt = currentDate, deadLine = deadline,
            status = status, email = email, phone = phone, url = url
        )
    }

    private fun populateStatusList(): List<String> {
        val statusList = mutableListOf<String>()
        statusList.add("Open")
        statusList.add("In-progress")
        statusList.add("Test")
        statusList.add("Done")

        return statusList
    }

    private fun isValid(): Boolean {
        val title = tilTaskName.editText?.text.toString().trim()
        val desc = tilTaskDescription.editText?.text.toString().trim()
        val deadline = tilDeadline.editText?.text.toString().trim()
        val status = tilStatus.editText?.text.toString().trim()

        if (title.isBlank()) {
            tilTaskName.error = "Enter task title"
            return false
        } else {
            tilTaskName.error = null
        }

        if (desc.isBlank()) {
            tilTaskDescription.error = "Enter task description"
            return false
        } else {
            tilTaskDescription.error = null
        }

        if (deadline.isBlank()) {
            tilDeadline.error = "Enter task deadline"
            return false
        } else {
            tilDeadline.error = null
        }

        if (status.isBlank()) {
            tilStatus.error = "Select task status"
            return false
        } else {
            tilStatus.error = null
        }

        return true
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.success)
        builder.setMessage(R.string.dialog_title)

        builder.setPositiveButton("OK") { _, _ ->
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}