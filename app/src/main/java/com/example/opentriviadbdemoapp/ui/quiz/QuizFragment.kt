package com.example.opentriviadbdemoapp.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.opentriviadbdemoapp.R

class QuizFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_quiz, container, false)
        val dropMenu = root.findViewById<AutoCompleteTextView>(R.id.tv_quiz)


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
            findNavController().navigate(R.id.action_nav_quiz_to_settingsFragment)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}