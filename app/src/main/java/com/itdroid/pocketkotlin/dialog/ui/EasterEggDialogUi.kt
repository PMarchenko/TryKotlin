package com.itdroid.pocketkotlin.dialog.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun EasterEggDialogUi(
    dismissAction: () -> Unit,
) {
    val context = ContextAmbient.current
    DialogUi(
        title = TextInput(text = title(context)),
        onDismissRequest = dismissAction,
        negativeOnClick = dismissAction,
        negativeButtonText = TextInput(text = negativeMessage(context)),
        positiveOnClick = dismissAction,
        positiveButtonText = TextInput(text = positiveMessage(context))
    )
}

private fun title(context: Context) = restore(context, title)

private fun negativeMessage(context: Context) = restore(context, negativeMessage)

private fun positiveMessage(context: Context) = restore(context, positiveMessage)

private val title =
    arrayOf(1375609612, 1375609658, 1375609632, 1375609717, 1375609652, 1375609639, 1375609648,
        1375609717, 1375609659, 1375609658, 1375609634, 1375609717, 1375609652, 1375609717,
        1375609649, 1375609648, 1375609635, 1375609648, 1375609657, 1375609658, 1375609637,
        1375609648, 1375609639, 1375609716)

private val negativeMessage =
    arrayOf(1375609627, 1375609658, 1375609633, 1375609717, 1375609644, 1375609648, 1375609633)

private val positiveMessage =
    arrayOf(1375609606, 1375609632, 1375609639, 1375609648, 1375609721, 1375609717, 1375609628,
        1375609717, 1375609652, 1375609656)

private fun restore(context: Context, message: Array<Int>): String {
    val packageHash = context.packageName.hashCode()
    val messageBytes = ByteArray(message.size)
    message.forEachIndexed { index, i ->
        messageBytes[index] = (i xor packageHash).toByte()
    }
    return String(messageBytes)
}

@Suppress("unused")
private fun xor(context: Context, message: String): IntArray {
    val packageHash = context.packageName.hashCode()
    val bytes = message.toByteArray()
    val converted = IntArray(bytes.size)

    bytes.forEachIndexed { index, b ->
        converted[index] = b.toInt() xor packageHash
    }

    return converted
}
