package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R

/**
 * @author Pavel Marchenko
 */
class ExamplesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_examples, container, false)
    }
}