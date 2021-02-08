package com.example.opentriviadbdemoapp.ui.browse

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.opentriviadbdemoapp.R

class BrowseFragment : Fragment() {

    private lateinit var homeViewModel: BrowseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(BrowseViewModel::class.java)
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_browse, container, false)
        val dropMenu = root.findViewById<AutoCompleteTextView>(R.id.tv_browse)


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

        return root
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


}