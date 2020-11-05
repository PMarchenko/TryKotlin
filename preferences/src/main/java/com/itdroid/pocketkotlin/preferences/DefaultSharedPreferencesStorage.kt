package com.itdroid.pocketkotlin.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import kotlinx.coroutines.*

/**
 * Do not forget to call [release] method !!
 *
 * !!Single thread usage only
 * @author itdroid
 */
internal class DefaultSharedPreferencesStorage(
    context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
) : Storage, SharedPreferences.OnSharedPreferenceChangeListener {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    private val observersRegistry: MutableMap<String, MutableList<Registry<Any>>> by lazy { mutableMapOf() }

    @Volatile
    private var prefsObserverRegistered = false

    /**
     * After calling this method all [LiveData] objects bound to this storage won't amit any data
     * */
    override fun release() {
        if (prefsObserverRegistered) {
            unregisterObserver()
        }

        observersRegistry.clear()
    }

    private fun registerObserver() {
        if (!prefsObserverRegistered) {
            prefs.registerOnSharedPreferenceChangeListener(this)
            prefsObserverRegistered = true
        }
    }

    private fun unregisterObserver() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                prefs.unregisterOnSharedPreferenceChangeListener(this@DefaultSharedPreferencesStorage)
                prefsObserverRegistered = false
            }
            coroutineScope.cancel()
        }
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if (key == null || prefs == null) return

        val registries = observersRegistry[key]
        if (registries.isNullOrEmpty()) return

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                for (registry in registries) {
                    registry.liveData.postValue(registry.pref.get())
                }
            }
        }
    }

    override fun <T> read(key: String, opt: T): T {
        @Suppress("UNCHECKED_CAST")
        return when (opt) {
            is String -> prefs.getString(key, opt) ?: opt
            is Boolean -> prefs.getBoolean(key, opt)
            is Float -> prefs.getFloat(key, opt)
            is Int -> prefs.getInt(key, opt)
            is Long -> prefs.getLong(key, opt)
            else -> error("Unknown type of $key, opt=$opt")
        } as T
    }

    override fun <T> save(key: String, value: T) {
        prefs.edit {
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                else -> error("Unknown type of $key=$value")
            }
        }
    }

    override fun <T> asLiveData(key: String, pref: AppPreferenceImpl<T>): LiveData<T> {
        val out = MutableLiveData<T>()
        @Suppress("UNCHECKED_CAST")
        registerLiveData(out, key, pref)
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                registerObserver()
                out.postValue(pref.get())
            }
        }
        return out
    }

    private fun <T> registerLiveData(
        liveData: MutableLiveData<T>,
        key: String,
        pref: AppPreferenceImpl<T>,
    ) {
        @Suppress("UNCHECKED_CAST")
        val registries = (
                observersRegistry[key]
                    ?: mutableListOf<Registry<Any>>()
                        .also { observersRegistry[key] = it }
                ) as MutableList<Registry<T>>

        registries.add(Registry(liveData, key, pref))
    }
}

private data class Registry<T>(
    val liveData: MutableLiveData<T>,
    val key: String,
    val pref: AppPreferenceImpl<T>,
)