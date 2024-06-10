package com.example.gymquizapp.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.gymquizapp.R
import com.example.gymquizapp.databinding.FeedbackMessageDialogBinding
import com.example.gymquizapp.enumerator.MessageType

class FeedbackMessageDialog(
    private val messageType: MessageType,
    private val parentActivity: Activity) {

    private var alertDialog: AlertDialog
    private var viewBinding: FeedbackMessageDialogBinding = FeedbackMessageDialogBinding.inflate(parentActivity.layoutInflater)

    init {

        alertDialog = AlertDialog.Builder(parentActivity).let {

            it.setView(viewBinding.root)
            it.create()
        }
    }

    fun showFeedbackMessageDialog(
        messageTitle: String? = null,
        messageBody: String? = null,
        denialButtonVisible: Boolean = false,
        confirmationButtonAction: () -> Unit = { },
        denialButtonAction: () -> Unit = { }){

        createFeedbackMessageDialog(messageTitle,
                                    messageBody,
                                    denialButtonVisible,
                                    confirmationButtonAction,
                                    denialButtonAction).show()
    }

    private fun createFeedbackMessageDialog(
        messageTitle: String?,
        messageBody: String?,
        denialButtonVisible: Boolean = false,
        confirmationButtonAction: () -> Unit,
        denialButtonAction: () -> Unit): AlertDialog{

        val icon = ContextCompat.getDrawable(parentActivity, getHeaderIconId())
        viewBinding.dialogHeaderIcon.setImageDrawable(icon)

        viewBinding.dialogHeaderTitle.text = messageTitle ?: getDefaultHeaderTitleText()

        viewBinding.dialogBodyMessage.text = messageBody ?: getDefaultMessageBodyText()

        viewBinding.dialogHeader.background = getBackgroundColor()

        viewBinding.confirmationButton.setOnClickListener { confirmationButtonAction(); alertDialog.dismiss()}

        if (denialButtonVisible)
            viewBinding.denialButton.setOnClickListener { denialButtonAction(); alertDialog.dismiss() }
        else
            viewBinding.denialButton.visibility = View.GONE

        return alertDialog
    }

    private fun getDefaultHeaderTitleText(): String {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getString(parentActivity, R.string.warning)
            MessageType.Success -> ContextCompat.getString(parentActivity, R.string.success)
        }
    }

    private fun getBackgroundColor(): Drawable? {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getDrawable(parentActivity, R.color.message_warning_yellow)
            MessageType.Success -> ContextCompat.getDrawable(parentActivity, R.color.message_success_green)
        }
    }

    private fun getDefaultMessageBodyText(): String {
        return when (messageType){
            MessageType.Warning -> ContextCompat.getString(parentActivity, R.string.careful)
            MessageType.Success -> ContextCompat.getString(parentActivity, R.string.hit)
        }
    }

    private fun getHeaderIconId(): Int{
        return when (messageType){
            MessageType.Warning -> R.drawable.ic_warning
            MessageType.Success -> R.drawable.ic_success
        }
    }
}