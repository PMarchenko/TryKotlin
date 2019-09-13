package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pmarchenko.itdroid.pocketkotlin.R

class MyProjectsFragment : Fragment() {

    private lateinit var homeViewModel: ProjectsFeedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(ProjectsFeedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_my_projects, container, false)
    }
}