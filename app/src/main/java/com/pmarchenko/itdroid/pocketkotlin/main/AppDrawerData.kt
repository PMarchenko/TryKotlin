package com.pmarchenko.itdroid.pocketkotlin.main

import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.navigation.*
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */

object AppDrawerData {

    val HEADER_ICON: ImageInput = ImageInput(vectorResId = R.drawable.ic__drawer__app_logo)

    val HEADER_TITLE: TextInput = TextInput(R.string.app_name)

    val MENU_ITEMS: List<DrawerItem> = listOf(
        DrawerScreen(MyProjectsDestination),
        DrawerScreen(ExamplesDestination),
        DrawerScreen(KoansDestination),
        DrawerScreen(InActionDestination),
        DrawerDivider,
        DrawerScreen(TrashDestination),
        DrawerScreen(SettingsDestination)
    )
}

sealed class DrawerItem

data class Section(val title: TextInput) : DrawerItem()

data class DrawerScreen(val destination: DrawerDestination) : DrawerItem()

object DrawerDivider : DrawerItem()


