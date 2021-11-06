package com.imran.noteme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imran.noteme.R
import com.imran.noteme.db.model.Note

class TaskAdapter(private val context: Context, private var openTaskList: List<Note>) :
    RecyclerView.Adapter<TaskAdapter.FriendViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return openTaskList.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val openTask = openTaskList[position]
        holder.tvTaskName.text = openTask.title
        holder.tvCreatedDate.text = openTask.createdAt.toString()
        holder.tvDeadline.text = openTask.deadLine

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClickListener(openTask)
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(openTask: Note)
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val tvCreatedDate: TextView = itemView.findViewById(R.id.tvCreatedDate)
        val tvDeadline: TextView = itemView.findViewById(R.id.tvDeadline)
        val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }
}