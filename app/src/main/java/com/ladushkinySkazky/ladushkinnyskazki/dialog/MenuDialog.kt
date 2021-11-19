package com.ladushkinySkazky.ladushkinnyskazki.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.R
import java.util.*

class MenuDialog {
    companion object{
        fun openMenu(context: Context){

            val dialog = Dialog(context, R.style.CustomDialogMenu)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_option)
            dialog.window?.setGravity(Gravity.TOP)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val btnClose = dialog.findViewById(R.id.btn_close_dialog_options) as TextView
            val editSent = dialog.findViewById<EditText>(R.id.edttxt_menu)
            val btnSent = dialog.findViewById<ImageButton>(R.id.img_btn_send_menu)
            lateinit var mDataBase: DatabaseReference
            lateinit var id: String
            val idRand: UUID = UUID.randomUUID()
            id = idRand.toString()

            btnSent.setOnClickListener {

                mDataBase = FirebaseDatabase.getInstance().getReference("Feedback/Items/$id/TextFeedback")
                mDataBase.ref.setValue(editSent.text.toString())
                dialog.cancel()
                Toast.makeText(context,
                    "Отправлено!",
                    Toast.LENGTH_LONG)
                    .show()
            }



            btnClose.setOnClickListener {
                dialog.cancel()
            }

            dialog.show()
        }
    }
}