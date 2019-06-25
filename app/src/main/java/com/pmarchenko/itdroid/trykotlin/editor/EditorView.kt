package com.pmarchenko.itdroid.trykotlin.editor

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.pmarchenko.itdroid.trykotlin.view.View
import com.pmarchenko.itdroid.trykotlin.view.ViewStateHandler

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorView(
    private val lcycle: Lifecycle,
    stateHandler: ViewStateHandler<EditorViewState>
) : View<EditorViewState>(stateHandler), LifecycleOwner {

    override fun getLifecycle() = lcycle
}