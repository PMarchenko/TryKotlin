package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.utils.toast
import java.util.concurrent.TimeUnit

class EditorActivity : AppCompatActivity() {

    companion object {

        fun asDeepLinkIntent(projectId: Long): Intent {
            return Intent("pocketkotlin.VIEW_PROJECT", Uri.parse("app://pocketkotlin/project/$projectId"))
        }

        const val INVALID_ID = -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projectId = parseProjectId(intent)
        if (projectId > 0) {
            var editor = supportFragmentManager.findFragmentByTag(EditorFragment.TAG)
            if (editor == null) {
                editor = EditorFragment.newInstance(projectId)
                supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, editor, EditorFragment.TAG)
                    .commit()
            }
        } else {
            toast(R.string.error_message__invalid_project_id)
            finish()
        }
    }

    private var backPressedTimestamp: Long = 0L

    override fun onBackPressed() {
        val prev = backPressedTimestamp
        backPressedTimestamp = System.currentTimeMillis()
        //half a second
        if (backPressedTimestamp - prev < TimeUnit.SECONDS.toMillis(2)) {
            super.onBackPressed()
        } else {
            toast(R.string.error_message__editor_exit)
        }
    }

    private fun parseProjectId(intent: Intent?) = intent?.data?.lastPathSegment?.toLongOrNull() ?: INVALID_ID

}
