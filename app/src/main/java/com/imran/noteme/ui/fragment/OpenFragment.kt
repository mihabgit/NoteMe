package com.imran.noteme.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.noteme.R
import com.imran.noteme.db.NoteDatabase
import com.imran.noteme.db.model.Note
import com.imran.noteme.db.repository.NoteRepository
import com.imran.noteme.ui.activity.TaskDetailActivity
import com.imran.noteme.ui.adapter.TaskAdapter
import com.imran.noteme.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [OpenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpenFragment : Fragment() {

    private lateinit var rvOpenTask: RecyclerView
    private var adapter: TaskAdapter? = null
    private var openTaskList: List<Note>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOpenTask = view.findViewById(R.id.rvOpenTask)
        rvOpenTask.layoutManager = LinearLayoutManager(requireActivity())

        openTaskList = ArrayList()
        val noteRepository = NoteRepository(NoteDatabase(requireActivity()))
        CoroutineScope(Main).launch {
            openTaskList = noteRepository.getTaskByStatus(Constants.STATUS_OPEN)
        }.invokeOnCompletion {
            adapter = TaskAdapter(requireActivity(), openTaskList as ArrayList<Note>)
            rvOpenTask.adapter = adapter

            adapter?.setOnItemClickListener(object : TaskAdapter.OnItemClickListener {
                override fun onItemClickListener(openTask: Note) {
                    val intent = Intent(requireActivity(), TaskDetailActivity::class.java)
                    intent.putExtra(Constants.NOTE_DATA, openTask)
                    startActivity(intent)
                }
            })
        }
    }
}