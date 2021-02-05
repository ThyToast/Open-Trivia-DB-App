package com.example.opentriviadbdemoapp.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.opentriviadbdemoapp.R

class QuizFragment : Fragment() {

    private lateinit var notificationsViewModel: QuizViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(QuizViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quiz, container, false)

        val dropMenu = root.findViewById<AutoCompleteTextView>(R.id.tv_quiz)


        //retrieve the rest of the list from https://opentdb.com/api_category.php
        //this populates the drop down menu with items
        val items = listOf("General Knowledge", "Entertainment: Books", "Entertainment: Film", "Entertainment: Music")
        val adapter = ArrayAdapter(requireContext(), R.layout.options_menu, items)
        dropMenu.setAdapter(adapter)

        return root
    }
}