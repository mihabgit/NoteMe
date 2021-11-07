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
import com.imran.noteme.ui.activity.AddTaskActivity
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
    private var noteList: List<Note>? = null
    private lateinit var noteRepository: NoteRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteRepository = NoteRepository(NoteDatabase(requireActivity()))
        rvOpenTask = view.findViewById(R.id.rvOpenTask)
        rvOpenTask.layoutManager = LinearLayoutManager(requireActivity())

        noteList = ArrayList()
        adapter = TaskAdapter(requireActivity(), noteList as ArrayList<Note>)

        getOpenNoteFromDb()
        noteItemClick()
        editOrDeleteClick()
    }

    private fun getOpenNoteFromDb() {
        CoroutineScope(Main).launch {
            noteList = noteRepository.getTaskByStatus(Constants.STATUS_OPEN)
        }.invokeOnCompletion {
            adapter?.setNoteList(noteList as ArrayList<Note>)
            rvOpenTask.adapter = adapter
        }
    }

    private fun noteItemClick() {
        adapter?.setOnItemClickListener(object : TaskAdapter.OnItemClickListener {
            override fun onItemClickListener(note: Note) {
                val intent = Intent(requireActivity(), TaskDetailActivity::class.java)
                intent.putExtra(Constants.NOTE_DATA, note)
                startActivity(intent)
            }
        })
    }

    private fun editOrDeleteClick() {

        adapter?.setOnEditClickListener(object : TaskAdapter.OnEditClickListener {
            override fun onEditClickListener(note: Note) {
                val intent = Intent(requireActivity(), AddTaskActivity::class.java)
                intent.putExtra(Constants.NOTE_DATA, note)
                startActivity(intent)
            }
        })

        adapter?.setOnDeleteClickListener(object : TaskAdapter.OnDeleteClickListener {
            override fun onDeleteClickListener(note: Note) {
                CoroutineScope(Main).launch {
                    noteRepository.deleteNote(note)
                    getOpenNoteFromDb()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getOpenNoteFromDb()
    }

}