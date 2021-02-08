package com.example.opentriviadbdemoapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.opentriviadbdemoapp.R

class CatalogFragment : Fragment() {

    private lateinit var dashboardViewModel: CatalogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(CatalogViewModel::class.java)
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_catalog, container, false)

        return root
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
}