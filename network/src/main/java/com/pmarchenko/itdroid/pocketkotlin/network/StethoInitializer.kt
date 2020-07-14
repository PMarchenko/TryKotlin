package com.pmarchenko.itdroid.pocketkotlin.network

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class StethoInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        context?.let { StethoInjectorImpl.injectInto(it) }
        return true
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("No supported for StethoInitializer")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("No supported for StethoInitializer")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("No supported for StethoInitializer")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        throw UnsupportedOperationException("No supported for StethoInitializer")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("No supported for StethoInitializer")
    }
}
