package com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.R
import java.util.*

class MainMenuDialog {
    companion object{
        fun openMenu(context: Context){
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

            val btnClose = dialog.findViewById(R.id.btn_close_dialog_options) as TextView
            val editSent = dialog.findViewById<EditText>(R.id.edttxt_menu)
            val editName = dialog.findViewById<EditText>(R.id.edttxt_name_menu)
            val editContact = dialog.findViewById<EditText>(R.id.edttxt_contact_menu)
            val btnSent = dialog.findViewById<TextView>(R.id.img_btn_send_menu)

            lateinit var mDataBase: DatabaseReference
            lateinit var id: String
            val idRand: UUID = UUID.randomUUID()
            id = idRand.toString()

            btnSent.setOnClickListener {
                if (editSent.length() != 0) {
                    val send = "Имя: ${editName.text}, Отзыв: ${editSent.text}, Контакт: ${editContact.text}"
                    mDataBase = FirebaseDatabase.getInstance().getReference("Feedback/Items/$id/TextFeedback")
                    mDataBase.ref.setValue(send)
                    Toast.makeText(context,
                        "Спасибочки!",
                        Toast.LENGTH_LONG)
                        .show()
                    dialog.cancel()
                } else {
                    Toast.makeText(context,
                        "Ой, Вы совсем ничего не написали!)",
                        Toast.LENGTH_LONG)
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