package com.example.filenamecoder.chat

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast


fun createChooseFileIntent(): Intent {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    return intent
}


fun getFileName(context: Context, uri: Uri): String {
    var fileName: String? = null
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                fileName = it.getString(displayNameIndex)
            }
        }
    }
    return fileName ?: "Unknown File"
}

fun handleFileSelection(context: Context, uri: Uri) {
    val fileName = uri.lastPathSegment ?: "Не удалось получить имя файла"

    Toast.makeText(context, "Выбран файл: $fileName", Toast.LENGTH_SHORT).show()
}