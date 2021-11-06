package com.imran.noteme.ui.fragment

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
import com.imran.noteme.ui.adapter.TaskAdapter
import com.imran.noteme.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {

    private lateinit var rvTestTask: RecyclerView
    private var adapter: TaskAdapter? = null
    private var testTaskList: List<Note>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTestTask = view.findViewById(R.id.rvTestTask)
        rvTestTask.layoutManager = LinearLayoutManager(requireActivity())

        testTaskList = ArrayList()
        val noteRepository = NoteRepository(NoteDatabase(requireActivity()))
        CoroutineScope(Dispatchers.Main).launch {
            testTaskList = noteRepository.getTaskByStatus(Constants.STATUS_TEST)
        }.invokeOnCompletion {
            adapter = TaskAdapter(requireActivity(), testTaskList as ArrayList<Note>)
            rvTestTask.adapter = adapter
        }
    }

}