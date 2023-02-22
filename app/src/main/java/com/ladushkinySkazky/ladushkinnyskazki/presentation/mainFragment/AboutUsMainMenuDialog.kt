package com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.ListPopupWindow
import android.widget.TextView
import com.ladushkinySkazky.ladushkinnyskazki.R


class AboutUsMainMenuDialog {
    companion object {
        fun openMenu(context: Context) {
            val dialog = Dialog(context, R.style.CustomDialogMenu)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_about_us)
            dialog.window?.setGravity(Gravity.TOP)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val btnClose = dialog.findViewById(R.id.btn_close_menu_dialog) as TextView

            btnClose.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()
        }
    }
}