package com.example.gymquizapp

import android.content.Context
import android.content.Intent

class Tools {
    companion object{
        fun restartApplication(context: Context){

            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.let { Intent.makeRestartActivityTask(it.component) }
                .also{ context.startActivity(it) }

            Runtime.getRuntime().exit(0)
        }
    }
}