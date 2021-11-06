package com.imran.noteme.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.noteme.R
import com.imran.noteme.db.NoteDatabase
import com.imran.noteme.db.model.Note
import com.imran.noteme.db.repository.NoteRepository
import com.imran.noteme.ui.adapter.TaskAdapter
import com.imran.noteme.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFragment : Fragment() {

    private lateinit var rvDoneTask: RecyclerView
    private var adapter: TaskAdapter? = null
    private var doneTaskList: List<Note>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDoneTask = view.findViewById(R.id.rvDoneTask)
        rvDoneTask.layoutManager = LinearLayoutManager(requireActivity())

        doneTaskList = ArrayList()
        val noteRepository = NoteRepository(NoteDatabase(requireActivity()))
        CoroutineScope(Dispatchers.Main).launch {
            doneTaskList = noteRepository.getTaskByStatus(Constants.STATUS_DONE)
        }.invokeOnCompletion {
            adapter = TaskAdapter(requireActivity(), doneTaskList as ArrayList<Note>)
            rvDoneTask.adapter = adapter
        }
    }

}