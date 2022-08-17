package com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.text.format.DateFormat
import android.view.Gravity
import android.view.Window
import android.widget.EditText
import android.widget.ListPopupWindow
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.R
import java.util.*


class MainMenuDialog {
    companion object {
        fun openMenu(context: Context) {
            val dialog = Dialog(context, R.style.CustomDialogMenu)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_main_menu)
            dialog.window?.setGravity(Gravity.TOP)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val btnClose = dialog.findViewById(R.id.btn_close_menu_dialog) as TextView
            val editSent = dialog.findViewById<EditText>(R.id.edt_feedback_menu_dialog)
            val editName = dialog.findViewById<EditText>(R.id.edt_name_menu_dialog)
            val editContact = dialog.findViewById<EditText>(R.id.edt_contact_menu_dialog)
            val btnSent = dialog.findViewById<TextView>(R.id.img_btn_send_menu_dialog)

            lateinit var mDataBase: DatabaseReference
            lateinit var id: String
            val idRand: UUID = UUID.randomUUID()
            id = idRand.toString()

            btnSent.setOnClickListener {
                if (editSent.length() != 0) {

                    val name = "Имя: ${editName.text}"
                    val text = "Отзыв: ${editSent.text}"
                    val contact = "Контакт: ${editContact.text}"
                    val df = DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date())
                    val dataTime = df.toString()

                    mDataBase = FirebaseDatabase.getInstance().getReference("Feedback/$id/ID")
                    mDataBase.ref.setValue(id)

                    mDataBase = FirebaseDatabase.getInstance().getReference("Feedback/$id/DataTime")
                    mDataBase.ref.setValue(dataTime)

                    mDataBase =
                        FirebaseDatabase.getInstance().getReference("Feedback/$id/NameFeedback")
                    mDataBase.ref.setValue(name)

                    mDataBase =
                        FirebaseDatabase.getInstance().getReference("Feedback/$id/TextFeedback")
                    mDataBase.ref.setValue(text)

                    mDataBase =
                        FirebaseDatabase.getInstance().getReference("Feedback/$id/ContactFeedback")
                    mDataBase.ref.setValue(contact)

                    Toast.makeText(
                        context,
                        "Спасибочки!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    dialog.cancel()
                } else {
                    Toast.makeText(
                        context,
                        "Ой, Вы совсем ничего не написали!)",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            btnClose.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()
        }
    }
}