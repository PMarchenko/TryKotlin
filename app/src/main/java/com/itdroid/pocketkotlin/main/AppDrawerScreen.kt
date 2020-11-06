package com.itdroid.pocketkotlin.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.runtime.onDispose
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.actions.ScreenKotlinInAction
import com.itdroid.pocketkotlin.examples.ScreenExamples
import com.itdroid.pocketkotlin.koans.ScreenKotlinKoans
import com.itdroid.pocketkotlin.myprojects.ScreenMyProjects
import com.itdroid.pocketkotlin.navigation.*
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.settings.ScreenSettings
import com.itdroid.pocketkotlin.trash.ScreenTrash
import com.itdroid.pocketkotlin.ui.compose.*
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.checkAllMatched
import com.itdroid.pocketkotlin.utils.tint

/**
 * @author itdroid
 */

private const val INTERCEPTOR_DRAWER = "INTERCEPTOR_MAIN_DRAWER"

@Composable
fun AppDrawerScreen(destination: DrawerDestination) {
    val nav = navigation()
    val scaffoldState = rememberScaffoldState()
    val drawerState = scaffoldState.drawerState

    onActive {
        nav.addOnBackPressedInterceptor(INTERCEPTOR_DRAWER) {
            if (drawerState.isOpen) {
                drawerState.close()
                true
            } else false
        }
    }

    onDispose {
        nav.removeOnBackPressedInterceptor(INTERCEPTOR_DRAWER)
    }

    val isDefaultDestination = NavigationViewModel.isDefaultDestination(destination)
    val upNavigationAction =
        if (isDefaultDestination) {
            { drawerState.open() }
        } else {
            { nav.popCurrentScreen() }
        }
    val drawerNavigationAction: (Destination) -> Unit = {
        drawerState.close()
        nav.navigateTo(it)
    }

    AppDrawerScreenInput(
        drawerData = AppDrawerData,
        destination = destination,
        scaffoldState = scaffoldState,
        isDefaultDestination = isDefaultDestination,

        upNavigationAction = upNavigationAction,
        drawerNavigationAction = drawerNavigationAction
    )
}

@Composable
private fun AppDrawerScreenInput(
    drawerData: AppDrawerData,
    destination: DrawerDestination,
    scaffoldState: ScaffoldState,
    isDefaultDestination: Boolean,

    upNavigationAction: () -> Unit,
    drawerNavigationAction: (Destination) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Toolbar(
                title = destination.title,
                icon = ImageInput(
                    vector = if (isDefaultDestination) Icons.Filled.Menu else Icons.Filled.ArrowBack
                ),
                iconOnClick = upNavigationAction,
            )
        },
        drawerScrimColor = MaterialTheme.colors.surface.copy(alpha = 0.7f),
        drawerContent = {
            DrawerContent(
                content = drawerData,
                current = destination,
                onClick = drawerNavigationAction
            )
        }
    ) {
        Crossfade(current = destination) {
            when (it) {
                MyProjectsDestination -> ScreenMyProjects()
                ExamplesDestination -> ScreenExamples()
                KoansDestination -> ScreenKotlinKoans()
                InActionDestination -> ScreenKotlinInAction()
                TrashDestination -> ScreenTrash()
                SettingsDestination -> ScreenSettings()
            }.checkAllMatched
        }
    }
}

@Composable
private fun DrawerContent(
    content: AppDrawerData,
    current: DrawerDestination,
    onClick: (DrawerDestination) -> Unit,
) {
    ScrollableColumn {
        DrawerHeader(
            content.HEADER_ICON,
            content.HEADER_TITLE,
            current.title
        )
        for (item in content.MENU_ITEMS) {
            when (item) {
                is Section -> MenuSection(item)
                is DrawerScreen ->
                    MenuItem(
                        destination = item.destination,
                        isSelected = item.destination == current,
                        onClick = onClick
                    )
                is DrawerDivider -> AppDivider()
            }.checkAllMatched
        }
    }
}

@Composable
private fun DrawerHeader(
    image: ImageInput,
    title: TextInput,
    subtitle: TextInput,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface, CircleShape)
                .padding(24.dp)
        ) {
            AppImage(image)
        }

        AppText(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.h5
        )

        AppText(
            text = subtitle,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
private fun MenuSection(item: Section) {
    ListSection(item.title, modifier = Modifier.padding(horizontal = 16.dp))
    AppDivider(startIndent = 16.dp)
}

@Composable
private fun MenuItem(
    destination: DrawerDestination,
    isSelected: Boolean,
    onClick: (DrawerDestination) -> Unit,
) {
    ListItem(
        modifier = if (isSelected) {
            Modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.4f))
        } else {
            Modifier
        }.clickable(onClick = { onClick(destination) }),
        icon = { AppImage(image = destination.image.tint(AmbientContentColor.current)) },
        text = { AppText(destination.title) },
    )
}

@Preview("AppDrawerScreen preview [Light Theme]")
@Composable
private fun ScreenMainPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppDrawerScreenInput(
            drawerData = AppDrawerData,
            destination = MyProjectsDestination,
            scaffoldState = rememberScaffoldState(),
            isDefaultDestination = true,
            upNavigationAction = {},
            drawerNavigationAction = {}
        )
    }
}

@Preview("AppDrawerScreen preview [Dark Theme]")
@Composable
private fun ScreenMainPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppDrawerScreenInput(
            drawerData = AppDrawerData,
            destination = MyProjectsDestination,
            scaffoldState = rememberScaffoldState(),
            isDefaultDestination = false,
            upNavigationAction = {},
            drawerNavigationAction = {}
        )
    }
}
