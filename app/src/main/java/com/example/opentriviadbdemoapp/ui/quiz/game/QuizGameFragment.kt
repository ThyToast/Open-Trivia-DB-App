package com.example.opentriviadbdemoapp.ui.quiz.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.opentriviadbdemoapp.databinding.FragmentQuizGameBinding


class QuizGameFragment : Fragment() {

    private var fragment: FragmentQuizGameBinding? = null
    private val binding get() = fragment!!

    private val args: QuizGameFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragment = FragmentQuizGameBinding.inflate(inflater, container, false)
        val quizQuestion = args.quizQuestion




        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }

}