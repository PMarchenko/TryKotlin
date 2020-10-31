package com.itdroid.pocketkotlin.navigation.state

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.itdroid.pocketkotlin.navigation.*
import com.itdroid.pocketkotlin.utils.checkAllMatched
import java.util.*

/**
 * @author itdroid
 */
class BundleNavigationState(stateHandle: SavedStateHandle) : StateHandler(stateHandle) {

    fun restoreNavigation(): LinkedList<Destination> {
        return restore<Bundle>(STATE_KEY_NAV_STACK)
            ?.asDestinationsStack()
            ?: LinkedList()
    }

    fun saveNavigation(nav: LinkedList<Destination>) {
        save(STATE_KEY_NAV_STACK, nav.toBundle())
    }

    private fun Destination.toBundle(): Bundle =
        Bundle().also { out ->
            when (this) {
                MyProjectsDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_MY_PROJECTS)
                ExamplesDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_EXAMPLE)
                KoansDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_KOANS)
                InActionDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_IN_ACTION)
                TrashDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_TRASH)
                SettingsDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_SETTINGS)
                LicensesDestination -> out.putString(KEY_DESTINATION_TYPE, TYPE_LICENSES)
                is LicenseDestination -> {
                    out.putString(KEY_DESTINATION_TYPE, TYPE_LICENSE)
                    out.putParcelable(KEY_LICENSE_INFO, licenseInfo)
                }
                is ProjectEditorDestination -> {
                    out.putString(KEY_DESTINATION_TYPE, TYPE_PROJECT_DETAILS)
                    out.putLong(KEY_PROJECT_ID, projectId)
                    out.putLong(KEY_FILE_ID, fileId)
                }
                is ProjectConfigurationDestination -> {
                    out.putString(KEY_DESTINATION_TYPE, TYPE_PROJECT_CONFIGURATION)
                    out.putLong(KEY_PROJECT_ID, projectId)
                }
            }.checkAllMatched
        }

    private fun Bundle.asDestination(): Destination? =
        when (val type = getString(KEY_DESTINATION_TYPE)) {
            TYPE_MY_PROJECTS -> MyProjectsDestination
            TYPE_EXAMPLE -> ExamplesDestination
            TYPE_KOANS -> KoansDestination
            TYPE_IN_ACTION -> InActionDestination
            TYPE_TRASH -> TrashDestination
            TYPE_SETTINGS -> SettingsDestination
            TYPE_LICENSES -> LicensesDestination
            TYPE_LICENSE -> LicenseDestination(
                licenseInfo = getParcelable(KEY_LICENSE_INFO)
                    ?: error("Cannot restore license info")
            )
            TYPE_PROJECT_DETAILS -> ProjectEditorDestination(
                projectId = getLong(KEY_PROJECT_ID),
                fileId = getLong(KEY_FILE_ID)
            )
            TYPE_PROJECT_CONFIGURATION -> ProjectConfigurationDestination(
                projectId = getLong(KEY_PROJECT_ID),
            )
            else -> error("Unsupported type: $type")
        }

    private fun LinkedList<Destination>.toBundle() =
        Bundle().also { out ->
            out.putInt(KEY_NAV_STACK_SIZE, size)
            for ((index, dest) in this.withIndex()) {
                out.putBundle(STATE_KEY_NAV_STACK + index, dest.toBundle())
            }
        }

    private fun Bundle.asDestinationsStack(): LinkedList<Destination> =
        LinkedList<Destination>()
            .also { out ->
                val size = getInt(KEY_NAV_STACK_SIZE, -1)
                for (index in 0 until size) {
                    getBundle(STATE_KEY_NAV_STACK + index)?.also { destinationBundle ->
                        destinationBundle.asDestination()?.let {
                            out.add(it)
                        }
                    }
                }
            }

    companion object {
        private const val STATE_KEY_NAV_STACK = "STATE_NAV_STACK"

        private const val TYPE_MY_PROJECTS = "MyProjectsDestination"
        private const val TYPE_EXAMPLE = "ExamplesDestination"
        private const val TYPE_KOANS = "KoansDestination"
        private const val TYPE_IN_ACTION = "InActionDestination"
        private const val TYPE_TRASH = "TrashDestination"
        private const val TYPE_SETTINGS = "SettingsDestination"
        private const val TYPE_LICENSES = "LicensesDestination"
        private const val TYPE_LICENSE = "LicenseDestination"
        private const val TYPE_PROJECT_DETAILS = "ProjectDetailsDestination"
        private const val TYPE_PROJECT_CONFIGURATION = "ProjectConfigurationDestination"

        private const val KEY_NAV_STACK_SIZE = "KEY_STACK_SIZE"
        private const val KEY_DESTINATION_TYPE = "CURRENT_DESTINATION_TYPE"
        private const val KEY_LICENSE_INFO = "LICENSE_INFO"
        private const val KEY_PROJECT_ID = "PROJECT_ID"
        private const val KEY_FILE_ID = "FILE_ID"
    }
}