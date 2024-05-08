package com.example.gymquizapp.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.gymquizapp.R
import com.example.gymquizapp.databinding.FeedbackMessageDialogBinding
import com.example.gymquizapp.enumerator.MessageType

class FeedbackMessageDialog(
    private val messageType: MessageType,
    private val parentActivity: Activity) {

    private lateinit var alertDialog: AlertDialog

    fun showFeedbackMessageDialog(
        messageBody: String,
        confirmationButtonAction: () -> Unit,
        messageTitle: String? = null,
        confirmationButtonText: String? = null){

        alertDialog = createFeedbackMessageDialog(messageBody,
                                                 messageTitle,
                                                 confirmationButtonAction,
                                                 confirmationButtonText)

        alertDialog.show()
    }

    private fun createFeedbackMessageDialog(
        messageBody: String,
        messageTitle: String?,
        confirmationButtonAction: () -> Unit,
        confirmationButtonText: String?): AlertDialog{

            val viewBinding = FeedbackMessageDialogBinding.inflate(parentActivity.layoutInflater)
            val icon = ContextCompat.getDrawable(parentActivity, getHeaderIconId())

            viewBinding.dialogHeader.background = getBackgroundColor()
            viewBinding.dialogHeaderIcon.setImageDrawable(icon)
            viewBinding.dialogHeaderTitle.text = messageTitle ?: getHeaderTitleText()
            viewBinding.dialogBodyMessage.text = messageBody
            viewBinding.dialogConfirmationButton.setOnClickListener { confirmationButtonAction() }
            viewBinding.dialogConfirmationButton.text = confirmationButtonText ?: getConfirmationButtonText()

            val alertDialogBuilder = AlertDialog.Builder(parentActivity)
            alertDialogBuilder.setView(viewBinding.root)
            return alertDialogBuilder.create()
    }

    private fun getBackgroundColor(): Drawable? {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getDrawable(parentActivity, R.color.message_warning_yellow)
            MessageType.Success -> ContextCompat.getDrawable(parentActivity, R.color.message_success_green)
        }
    }
    private fun getConfirmationButtonText(): String {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getString(parentActivity, R.string.capital_resume)
            MessageType.Success -> ContextCompat.getString(parentActivity, R.string.capital_okay)
        }
    }

    private fun getHeaderTitleText(): String {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getString(parentActivity, R.string.warning)
            MessageType.Success -> ContextCompat.getString(parentActivity, R.string.success)
        }
    }

    private fun getHeaderIconId(): Int{
        return when (messageType){
            MessageType.Warning -> R.drawable.ic_warning
            MessageType.Success -> R.drawable.ic_success
        }
    }
}