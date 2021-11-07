package com.imran.noteme.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.imran.noteme.R
import com.imran.noteme.db.model.Note
import com.imran.noteme.util.CommonTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskAdapter(private val context: Context, private var noteList: List<Note>) :
    RecyclerView.Adapter<TaskAdapter.FriendViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onEditClickListener: OnEditClickListener? = null
    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnEditClickListener(onEditClickListener: OnEditClickListener) {
        this.onEditClickListener = onEditClickListener
    }

    fun setOnDeleteClickListener(onDeleteClickListener: OnDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val note = noteList[position]
        val taskName = note.title
        holder.tvTaskName.text = "Task Name: $taskName"
        val dateString = note.createdAt?.let { CommonTask().getDate(it, "dd/MM/yyyy") }
        holder.tvCreatedDate.text = "Created Date: $dateString"
        val deadline = note.deadLine
        holder.tvDeadline.text = "Deadline: $deadline"

        holder.llTaskDetail.setOnClickListener {
            onItemClickListener?.onItemClickListener(note)
        }

        holder.ivEdit.setOnClickListener {
            onEditClickListener?.onEditClickListener(note)
        }

        holder.ivDelete.setOnClickListener {
            onDeleteClickListener?.onDeleteClickListener(note)
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(note: Note)
    }

    interface OnEditClickListener {
        fun onEditClickListener(note: Note)
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(note: Note)
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val tvCreatedDate: TextView = itemView.findViewById(R.id.tvCreatedDate)
        val tvDeadline: TextView = itemView.findViewById(R.id.tvDeadline)
        val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val llTaskDetail: LinearLayout = itemView.findViewById(R.id.llTaskDetail)
    }
}