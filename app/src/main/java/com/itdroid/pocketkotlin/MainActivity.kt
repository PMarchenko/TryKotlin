package com.itdroid.pocketkotlin

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.itdroid.pocketkotlin.main.PocketKotlinApp
import com.itdroid.pocketkotlin.navigation.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private val navigation by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback {
            if (!navigation.onBackPressedConsumed()) finishAfterTransition()
        }

        setContent {
            PocketKotlinApp()
        }
    }
}
