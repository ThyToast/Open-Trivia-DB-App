package com.example.opentriviadbdemoapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.databinding.FragmentCatalogBinding
import com.example.opentriviadbdemoapp.ui.adapter.BaseRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : Fragment() {

    private var fragment: FragmentCatalogBinding? = null
    private val binding get() = fragment!!

    private val catalogViewModel: CatalogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        fragment = FragmentCatalogBinding.inflate(inflater, container, false)
        val recyclerView = binding.rvCatalog
        val recyclerAdapter = BaseRecyclerAdapter()

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        catalogViewModel.getCategoryCount()

        catalogViewModel.quizCategoryComposite.observe(viewLifecycleOwner, { response ->
            recyclerAdapter.setData(response)
        })

        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_settings -> {
            findNavController().navigate(R.id.action_nav_catalog_to_settingsFragment)
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