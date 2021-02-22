package com.example.opentriviadbdemoapp.ui.quiz.game

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.data.model.QuizResult
import com.example.opentriviadbdemoapp.databinding.FragmentQuizGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class QuizGameFragment : Fragment() {

    private var fragment: FragmentQuizGameBinding? = null
    private val binding get() = fragment!!
    private val args: QuizGameFragmentArgs by navArgs()
    private val position: MutableLiveData<Int> = MutableLiveData(0)
    private val quizResult: MutableList<QuizResult> = mutableListOf()

    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragment = FragmentQuizGameBinding.inflate(inflater, container, false)
        score = 0

        binding.viewTimer.base = SystemClock.elapsedRealtime() + 60000
        binding.viewTimer.start()

        position.observe(viewLifecycleOwner, { position ->
            if (position <= 9) {
                getQuestion(position)
            } else {
                exitQuiz()
            }
        })

        binding.viewTimer.setOnChronometerTickListener {
            if (it.text.equals("00:00")) {
                exitQuiz()
            }
        }
        return binding.root
    }

    private fun getQuestion(position: Int) {
        val quizLayout = args.quizQuestion[position]
        binding.apply {
            val convertQuestion =
                HtmlCompat.fromHtml(quizLayout.quizQuestion, HtmlCompat.FROM_HTML_MODE_LEGACY)

            tvQuizQuestion.text = convertQuestion
            tvQuizCategory.text = quizLayout.quizCategory
            chQuizDifficulty.text = quizLayout.quizDifficulty
            tvQuizCount.text = (position + 1).toString()

            //randomizes the questions and converts it to UTF-8 format
            val quizQuestionRandom =
                (quizLayout.quizWrongAnswer + quizLayout.quizCorrectAnswer).shuffled()

            val quizQuestionConvert = quizQuestionRandom.map {
                HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            tvAnswer1.text = quizQuestionConvert[0]
            tvAnswer2.text = quizQuestionConvert[1]

            if (quizLayout.quizType != "multiple") {
                tvAnswer3.isVisible = false
                tvAnswer4.isVisible = false
            } else {
                tvAnswer3.isVisible = true
                tvAnswer4.isVisible = true
                tvAnswer3.text = quizQuestionConvert[2]
                tvAnswer4.text = quizQuestionConvert[3]
            }
        }
        cardViewButton(quizLayout)
    }

    private fun cardViewButton(quizQuestion: QuizQuestion) {
        binding.materialCardView1.setOnClickListener {
            nextQuestion()
            isCorrect(quizQuestion, binding.tvAnswer1.text.toString())
        }
        binding.materialCardView2.setOnClickListener {
            nextQuestion()
            isCorrect(quizQuestion, binding.tvAnswer2.text.toString())
        }
        binding.materialCardView3.setOnClickListener {
            nextQuestion()
            isCorrect(quizQuestion, binding.tvAnswer3.text.toString())
        }
        binding.materialCardView4.setOnClickListener {
            nextQuestion()
            isCorrect(quizQuestion, binding.tvAnswer4.text.toString())
        }
    }

    private fun nextQuestion() {
        position.postValue(position.value?.plus(1))
    }

    private fun isCorrect(quizQuestion: QuizQuestion, answer: String) {
        if (answer == quizQuestion.quizCorrectAnswer) {
            score += 1
        }
        quizResult.add(
            QuizResult(quizQuestion, answer)
        )
        Log.d("isCorrect", score.toString())
    }

    private fun exitQuiz() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.result_title))
            .setMessage(resources.getString(R.string.result_message, score.toString()))
            .setNeutralButton(resources.getString(R.string.result_button_close)) { dialog, which ->
                val action = QuizGameFragmentDirections.actionQuizGameFragmentToNavQuiz()
                Navigation.findNavController(binding.root).navigate(action)
            }
            .setPositiveButton(resources.getString(R.string.result_button_show)) { dialog, which ->
                val `class`: Array<QuizResult> = quizResult.toTypedArray()
                val action =
                    QuizGameFragmentDirections.actionQuizGameFragmentToQuizResultsFragment(`class`)
                Navigation.findNavController(binding.root).navigate(action)
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }

}