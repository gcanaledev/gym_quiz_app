package com.example.gymquizapp.dialog

import android.app.Activity
import android.app.AlertDialog
import com.example.gymquizapp.enumerator.MessageType

class FeedbackMessageDialog(messageType: MessageType, parentActivity: Activity) {

    private var alertDialog: AlertDialog

    init {

        alertDialog = AlertDialog.Builder(parentActivity).create()
    }


    private fun showSuccessMessage(){


    }

    private fun showWarningMessage(){


    }

}