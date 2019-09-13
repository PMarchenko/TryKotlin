package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import com.google.android.material.appbar.AppBarLayout

/**
 * @author Pavel Marchenko
 */
interface EditorHost {

    fun registerEditor(editorBridge: EditorBridge)

    fun unregisterEditor()

}