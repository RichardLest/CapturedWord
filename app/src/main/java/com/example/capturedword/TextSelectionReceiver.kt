package com.example.capturedword

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TextSelectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_PROCESS_TEXT == intent.action) {
            // Crear un intent para iniciar la actividad TextSelectionActivity
            val textSelectionIntent = Intent(context, TextSelectionActivity::class.java)
            // Pasar los datos del intent original al intent de la actividad
            textSelectionIntent.putExtras(intent)
            // Iniciar la actividad
            context.startActivity(textSelectionIntent)
        }
    }
}