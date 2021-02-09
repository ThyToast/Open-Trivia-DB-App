package com.example.opentriviadbdemoapp.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.databinding.FragmentBrowseBinding
import com.example.opentriviadbdemoapp.ui.adapter.RecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowseFragment : Fragment() {

    private var fragment: FragmentBrowseBinding? = null
    private val binding get() = fragment!!

    private val browseViewModel: BrowseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        fragment = FragmentBrowseBinding.inflate(inflater, container, false)
        val dropMenu = binding.tvBrowse
        val recyclerView = binding.rvBrowse
        val recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        //retrieve the rest of the list from https://opentdb.com/api_category.php
        //this populates the drop down menu with items
        val items = listOf(
            "General Knowledge",
            "Entertainment: Books",
            "Entertainment: Film",
            "Entertainment: Music"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.options_menu, items)
        dropMenu.setAdapter(adapter)
        browseViewModel.getQuiz(10, 9)


        browseViewModel.quizQuestionResponse.observe(viewLifecycleOwner, { response ->
            recyclerAdapter.setData(response.responseResult)
        })



        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_settings -> {
            findNavController().navigate(R.id.action_nav_browse_to_settingsFragment)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null

    }
}