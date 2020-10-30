package com.pmarchenko.itdroid.pocketkotlin.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.settings.licenses.LicenseInfo
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
sealed class Destination

sealed class DrawerDestination(
    val title: TextInput,
    val image: ImageInput,
) : Destination()

object MyProjectsDestination :
    DrawerDestination(
        TextInput(R.string.screen__my_projects),
        ImageInput(vectorResId = R.drawable.ic__screen__my_projects),
    )

object ExamplesDestination :
    DrawerDestination(
        TextInput(R.string.screen__examples),
        ImageInput(vectorResId = R.drawable.ic__screen__examples)
    )

object KoansDestination :
    DrawerDestination(
        TextInput(R.string.screen__koans),
        ImageInput(vectorResId = R.drawable.ic__screen__kotlin_koans)
    )

object InActionDestination :
    DrawerDestination(
        TextInput(R.string.screen__in_action),
        ImageInput(vectorResId = R.drawable.ic__screen__in_action)
    )

object TrashDestination :
    DrawerDestination(
        TextInput(R.string.screen__trash_bin),
        ImageInput(Icons.Default.Delete),
    )

object SettingsDestination :
    DrawerDestination(
        TextInput(R.string.screen__settings),
        ImageInput(Icons.Default.Settings)
    )

object LicensesDestination : Destination()

data class LicenseDestination(val licenseInfo: LicenseInfo) : Destination()

data class ProjectEditorDestination(val projectId: Long, val fileId: Long = -1) : Destination()

data class ProjectConfigurationDestination(val projectId: Long) : Destination()
