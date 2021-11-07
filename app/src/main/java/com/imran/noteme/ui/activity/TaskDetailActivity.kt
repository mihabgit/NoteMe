package com.imran.noteme.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.imran.noteme.R
import com.imran.noteme.db.model.Note
import com.imran.noteme.util.CommonTask
import com.imran.noteme.util.Constants

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var tvCreatedDate: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvTaskTitle: TextView
    private lateinit var tvTaskDescription: TextView
    private lateinit var tvTaskDeadline: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvUrl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NoteMe)
        setContentView(R.layout.activity_task_detail)

        setupToolbar()

        val note = intent.getParcelableExtra<Note>(Constants.NOTE_DATA)

        tvCreatedDate = findViewById(R.id.tvCreatedDate)
        tvStatus = findViewById(R.id.tvStatus)
        tvTaskTitle = findViewById(R.id.tvTaskTitle)
        tvTaskDescription = findViewById(R.id.tvTaskDescription)
        tvTaskDeadline = findViewById(R.id.tvTaskDeadline)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvUrl = findViewById(R.id.tvUrl)

        val fabEditTask = findViewById<FloatingActionButton>(R.id.fabEditTask)
        fabEditTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(Constants.NOTE_DATA, note)
            finish()
            startActivity(intent)
        }

        setTaskDetails(note)

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

    private fun setTaskDetails(note: Note?) {
        val dateString = note?.createdAt?.let { CommonTask().getDate(it, "dd/MM/yyyy") }
        tvCreatedDate.text = "Created Date: $dateString"
        val status = note?.status
        tvStatus.text = "Status: $status"
        tvTaskTitle.text = note?.title
        tvTaskDescription.text = note?.description
        val deadline = note?.deadLine
        tvTaskDeadline.text = "Deadline: $deadline"

        val email = note?.email
        if (email?.isBlank() == false) {
            tvEmail.visibility = View.VISIBLE
            tvEmail.text = "Email: $email"
        }
        val phone = note?.phone
        if (phone?.isBlank() == false) {
            tvPhone.visibility = View.VISIBLE
            tvPhone.text = "Phone: $phone"
        }
        val url = note?.url
        if (url?.isBlank() == false) {
            tvUrl.visibility = View.VISIBLE
            tvUrl.text = "Url: $url"
        }
    }

}