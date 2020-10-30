package com.pmarchenko.itdroid.pocketkotlin.settings.licenses

import android.content.Context
import com.pmarchenko.itdroid.pocketkotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import kotlin.math.max

/**
 * @author Pavel Marchenko
 */
class RepositoryLicenses(private val context: Context) {

    fun readLicensesList(): Flow<List<LicenseInfo>> =
        flow {
            context.resources
                .openRawResource(R.raw.third_party_license_metadata)
                .bufferedReader()
                .useLines { lines ->
                    lines.map(String::asLicenseInfo)
                        .toCollection(mutableListOf())
                        .filterNotNull()
                        .sortedBy { it.name.toLowerCase(Locale.US) }
                        .also { emit(it) }
                }
        }


    @Suppress("BlockingMethodInNonBlockingContext")
    /**
     * It is Ok to use blocking call on [Dispatchers.IO]
     * */
    suspend fun readLicenseInfo(info: LicenseInfo) =
        flow {
            context.resources
                .openRawResource(R.raw.third_party_licenses)
                .bufferedReader().use { br ->
                    br.skip(max(info.offset - 1L, 0L))
                    val buffer = CharArray(info.length)
                    br.read(buffer)
                    emit(String(buffer))
                }

        }.flowOn(Dispatchers.IO)
}

private fun String.asLicenseInfo(): LicenseInfo? {
    // example 0:46 androidx.annotation:annotation
    val offsetEndIndex = indexOf(':')
    val offset = if (offsetEndIndex > 0) {
        substring(0, offsetEndIndex).toLong()
    } else {
        return null
    }

    val lengthEndIndex = indexOf(' ', offsetEndIndex)
    val length = if (lengthEndIndex > 0 && lengthEndIndex > offsetEndIndex) {
        substring(offsetEndIndex + 1, lengthEndIndex).toInt()
    } else {
        return null
    }

    return LicenseInfo(substring(lengthEndIndex + 1), offset, length)
}
