package com.pmarchenko.itdroid.pocketkotlin.ui.koans

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.ui.experimental.linearLayout
import com.pmarchenko.itdroid.pocketkotlin.ui.experimental.textView
import com.pmarchenko.itdroid.pocketkotlin.ui.experimental.ui

/**
 * @author Pavel Marchenko
 */
class KotlinKoansFragment : Fragment() {

    lateinit var title: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_kotlin_koans, container, false)
        return ui {
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                weightSum = 100f

                linearLayout {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                            .apply {
                                weight = 50f
                            }

                    orientation = LinearLayout.VERTICAL
                    weightSum = 100f

                    textView {
                        setBackgroundColor(Color.RED)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }

                    textView {
                        setBackgroundColor(Color.GREEN)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }

                    textView {
                        setBackgroundColor(Color.BLUE)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }
                }

                linearLayout {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                            .apply {
                                weight = 50f
                            }

                    orientation = LinearLayout.VERTICAL
                    weightSum = 100f

                    textView {
                        setBackgroundColor(Color.GREEN)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }

                    textView {
                        setBackgroundColor(Color.BLUE)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }

                    textView {
                        setBackgroundColor(Color.RED)
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                                .apply {
                                    weight = 33f
                                }
                    }
                }
            }
        }
    }
}