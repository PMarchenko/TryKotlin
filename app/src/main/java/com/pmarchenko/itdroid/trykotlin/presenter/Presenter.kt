package com.pmarchenko.itdroid.trykotlin.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.trykotlin.view.State
import com.pmarchenko.itdroid.trykotlin.view.View

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
open class Presenter<out V, out VM, S : State>(protected val view: V, protected val viewModel: VM)
        where V : View<S>,
              V : LifecycleOwner,
              VM : ViewModel