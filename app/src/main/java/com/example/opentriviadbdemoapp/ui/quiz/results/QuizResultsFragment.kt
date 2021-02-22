package com.example.opentriviadbdemoapp.ui.quiz.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.databinding.FragmentQuizGameBinding
import com.example.opentriviadbdemoapp.databinding.FragmentQuizResultsBinding
import com.example.opentriviadbdemoapp.ui.adapter.BaseRecyclerAdapter
import com.example.opentriviadbdemoapp.ui.quiz.game.QuizGameFragmentArgs


class QuizResultsFragment : Fragment() {

    private var fragment: FragmentQuizResultsBinding? = null
    private val binding get() = fragment!!

    private val args: QuizResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentQuizResultsBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvResult
        val recyclerAdapter = BaseRecyclerAdapter()

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter.setData(args.quizResult.toList())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }


}