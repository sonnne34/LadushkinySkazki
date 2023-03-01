package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.app.Dialog
import android.content.Context
import android.text.format.DateFormat
import android.view.Gravity
import android.view.Window
import android.widget.EditText
import android.widget.ListPopupWindow
import android.widget.TextView
import android.widget.Toast
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.firebase.SendDataFB
import java.util.*


class FeedbackMainMenuDialog {
    companion object {
        fun openMenu(context: Context) {
            val dialog = Dialog(context, R.style.CustomDialogMenu)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_feedback)
            dialog.window?.setGravity(Gravity.TOP)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val editName = dialog.findViewById<EditText>(R.id.edt_name_menu_dialog)
            val editSent = dialog.findViewById<EditText>(R.id.edt_feedback_menu_dialog)
            val editContact = dialog.findViewById<EditText>(R.id.edt_contact_menu_dialog)
            val btnSent = dialog.findViewById<TextView>(R.id.img_btn_send_menu_dialog)
            val btnClose = dialog.findViewById(R.id.btn_close_menu_dialog) as TextView

            btnSent.setOnClickListener {
                if (editSent.length() != 0) {
                    val name = "Имя: ${editName.text}"
                    val text = "Отзыв: ${editSent.text}"
                    val contact = "Контакт: ${editContact.text}"
                    val df = DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date())
                    val dataTime = df.toString()

                    SendDataFB().sendFeedback(dataTime, name, text, contact,
                        onSuccess = {
                            showToast(context, context.getString(R.string.toast_thanks))
                            dialog.cancel()
                        }, onFail = {
                            showToast(context, context.getString(R.string.toast_error_send_feedback))
                            dialog.cancel()
                        })
                } else {
                    showToast(context, context.getString(R.string.toast_empty_message))
                }
            }
            btnClose.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()
        }

        private fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}