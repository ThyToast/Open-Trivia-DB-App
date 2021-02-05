package com.example.opentriviadbdemoapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val root = inflater.inflate(R.layout.fragment_catalog, container, false)

        return root
    }
}