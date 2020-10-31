package com.itdroid.pocketkotlin.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.itdroid.pocketkotlin.navigation.state.BundleNavigationState
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.set

/**
 * @author itdroid
 */
class NavigationViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private val navigationState = BundleNavigationState(stateHandle)

    private val destinationStack: LinkedList<Destination>

    private val backInterceptors = LinkedHashMap<String, () -> Boolean>()

    private val _destination: MutableLiveData<Destination> = MutableLiveData()
    val destination: LiveData<Destination> = _destination

    init {
        destinationStack = navigationState.restoreNavigation()
        if (destinationStack.isEmpty()) {
            destinationStack.push(DEFAULT_DESTINATION)
        }

        applyNavigationChanges()
    }

    fun navigateTo(destination: Destination, clearOnTop: Boolean = false) {
        if (destination is DrawerDestination) {
            destinationStack.clear()
            if (destination !== DEFAULT_DESTINATION) {
                destinationStack.push(DEFAULT_DESTINATION)
            }
        } else if (clearOnTop) {
            destinationStack
                .findLast { it::class.java == destination::class.java }
                ?.let {
                    val index = destinationStack.indexOf(it)
                    while (destinationStack.size > index) {
                        destinationStack.pop()
                    }
                }
        }

        destinationStack.push(destination)
        applyNavigationChanges()
    }

    fun popCurrentScreen() {
        destinationStack.pop()
        applyNavigationChanges()
    }

    private fun applyNavigationChanges() {
        navigationState.saveNavigation(destinationStack)
        _destination.value = destinationStack.peek()
    }

    fun addOnBackPressedInterceptor(key: String, interceptor: () -> Boolean) {
        backInterceptors[key] = interceptor
    }

    fun removeOnBackPressedInterceptor(key: String) {
        backInterceptors.remove(key)
    }

    fun onBackPressedConsumed(): Boolean {
        for (key in backInterceptors.keys.reversed()) {
            if (backInterceptors[key]?.invoke() == true) {
                return true
            }
        }
        return if (destinationStack.size > 1) {
            popCurrentScreen()
            true
        } else {
            false
        }
    }

    companion object {

        private val DEFAULT_DESTINATION: Destination = MyProjectsDestination

        fun isDefaultDestination(dest: Destination) = dest == DEFAULT_DESTINATION
    }
}
