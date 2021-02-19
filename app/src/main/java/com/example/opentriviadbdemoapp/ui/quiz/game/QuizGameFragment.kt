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
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.FragmentQuizGameBinding


class QuizGameFragment : Fragment() {

    private var fragment: FragmentQuizGameBinding? = null
    private val binding get() = fragment!!
    private val args: QuizGameFragmentArgs by navArgs()
    private val position: MutableLiveData<Int> = MutableLiveData(0)
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragment = FragmentQuizGameBinding.inflate(inflater, container, false)
        binding.viewTimer.base = SystemClock.elapsedRealtime() + 60000
        binding.viewTimer.start()

        position.observe(viewLifecycleOwner, { position ->
            if (position <= 9) {
                getQuestion(position)
            } else {
                //goes back to quiz fragment
//                setFragmentResult("requestKey", bundleOf("name" to "test"))
                //sends data back

                val action = QuizGameFragmentDirections.actionQuizGameFragmentToNavQuiz()
                Navigation.findNavController(binding.root).navigate(action)
            }
        })
        return binding.root
    }

    private fun getQuestion(position: Int) {
        val quizLayout = args.quizQuestion[position]
        binding.apply {
            val convertText =
                HtmlCompat.fromHtml(quizLayout.quizQuestion, HtmlCompat.FROM_HTML_MODE_LEGACY)

            tvQuizQuestion.text = convertText
            tvQuizCategory.text = quizLayout.quizCategory
            chQuizDifficulty.text = quizLayout.quizDifficulty
            tvQuizCount.text = (position + 1).toString()

            //randomizes the questions
            val quizQuestionRandom =
                (quizLayout.quizWrongAnswer + quizLayout.quizCorrectAnswer).shuffled()
            tvAnswer1.text = quizQuestionRandom[0]
            tvAnswer2.text = quizQuestionRandom[1]

            if (quizLayout.quizType != "multiple") {
                tvAnswer3.isVisible = false
                tvAnswer4.isVisible = false
            } else {
                tvAnswer3.isVisible = true
                tvAnswer4.isVisible = true
                tvAnswer3.text = quizQuestionRandom[2]
                tvAnswer4.text = quizQuestionRandom[3]
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
        Log.d("isCorrect", score.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }

}