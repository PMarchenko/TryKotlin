package com.pmarchenko.itdroid.pocketkotlin.ui.koans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R

/**
 * @author Pavel Marchenko
 */
class KotlinKoansFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kotlin_koans, container, false)
    }
}