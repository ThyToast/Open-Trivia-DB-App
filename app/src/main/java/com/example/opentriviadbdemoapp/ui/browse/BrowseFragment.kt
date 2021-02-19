package com.example.opentriviadbdemoapp.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizCategoryList
import com.example.opentriviadbdemoapp.databinding.FragmentBrowseBinding
import com.example.opentriviadbdemoapp.ui.adapter.PagedListAdapter
import com.example.opentriviadbdemoapp.ui.viewModel.BrowseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BrowseFragment : Fragment() {

    private var fragment: FragmentBrowseBinding? = null
    private val binding get() = fragment!!

    private val browseViewModel: BrowseViewModel by viewModel()
    private val listViewModel: BrowseDataViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        fragment = FragmentBrowseBinding.inflate(inflater, container, false)
        binding.tvBrowse.isEnabled = false

        //retrieve the rest of the list from https://opentdb.com/api_category.php
        //this populates the drop down menu with items
        browseViewModel.getCategory()
        browseViewModel.quizCategoryResponse.observe(viewLifecycleOwner, { response ->
            binding.tvBrowse.isEnabled = true
            setDropMenuData(response.category)
        })

        return binding.root
    }

    private fun setDropMenuData(items: List<QuizCategoryList>) {
        val recyclerView = binding.rvBrowse
        val recyclerAdapter = PagedListAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dropMenu = binding.tvBrowse
        val menuList = items.map {
            it.categoryName
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.options_menu, menuList)
        dropMenu.setAdapter(adapter)

        dropMenu.setOnItemClickListener { adapterView: AdapterView<*>, view: View, i: Int, l: Long ->

            listViewModel.getQuizItemList(items[i].categoryId)
                .observe(viewLifecycleOwner, { response ->
                    recyclerAdapter.submitList(response)
                })
        }
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