package com.example.opentriviadbdemoapp.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizCategoryList
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.FragmentQuizBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizFragment : Fragment() {

    private var fragment: FragmentQuizBinding? = null
    private val binding get() = fragment!!

    private val quizViewModel: QuizViewModel by viewModel()
    private lateinit var quizQuestion: Array<QuizQuestion>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        fragment = FragmentQuizBinding.inflate(inflater, container, false)
        quizViewModel.getQuiz(10)

        binding.btnQuizNext.isEnabled = false

        //retrieve the rest of the list from https://opentdb.com/api_category.php
        //this populates the drop down menu with items
        quizViewModel.getCategory()
        quizViewModel.quizCategoryResponse.observe(viewLifecycleOwner, { response ->
            setDropMenuData(response.category)
        })

        quizViewModel.quizQuestionResponse.observe(viewLifecycleOwner, { response ->
            quizQuestion = response.responseResult.toTypedArray()
            binding.btnQuizNext.isEnabled = true
        })

        binding.btnQuizNext.setOnClickListener { v ->
            v.context as AppCompatActivity
            val `class`: Array<QuizQuestion> = quizQuestion
            val action = QuizFragmentDirections.actionNavQuizToQuizGameFragment(`class`)
            Navigation.findNavController(v).navigate(action)
        }

        return binding.root
    }

    private fun setDropMenuData(items: List<QuizCategoryList>) {
        val dropMenu = binding.tvQuiz
        val menuList = items.map {
            it.categoryName
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.options_menu, menuList)
        dropMenu.setAdapter(adapter)
        dropMenu.setOnItemClickListener { adapterView: AdapterView<*>, view: View, i: Int, l: Long ->
            binding.btnQuizNext.isEnabled = false
            quizViewModel.getQuiz(10, items[i].categoryId)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }
}