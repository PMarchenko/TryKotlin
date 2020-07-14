package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.toast

class EditorActivity : AppCompatActivity() {

    companion object {

        fun asDeepLinkIntent(projectId: Long) =
            Intent(
                "pocketkotlin.VIEW_PROJECT",
                Uri.parse("app://pocketkotlin/project/$projectId")
            )

        private fun parseProjectId(intent: Intent?): Long =
            intent?.data?.lastPathSegment?.toLongOrNull() ?: -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projectId = parseProjectId(intent)

        if (projectId > 0) {
            val editor = supportFragmentManager.findFragmentByTag(EditorFragment.TAG)
            if (editor == null) {
                supportFragmentManager.commit {
                    add(
                        android.R.id.content,
                        EditorFragment.newInstance(projectId),
                        EditorFragment.TAG
                    )
                }
            }

            onBackPressedDispatcher.addCallback(this, backButtonCallback)
        } else {
            toast(R.string.error_message__invalid_project_id)
            finish()
        }
    }

    private val backButtonCallback = object :
        OnBackPressedCallback(true) {

        private val backButtonResolver = BackButtonResolver()

        override fun handleOnBackPressed() {
            if (backButtonResolver.allowBackPressed()) {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            } else {
                //todo snackbar
                toast(R.string.error_message__editor_exit)
            }
        }
    }
}
