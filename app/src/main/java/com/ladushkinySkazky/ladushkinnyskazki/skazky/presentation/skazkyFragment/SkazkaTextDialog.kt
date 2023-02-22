package com.ladushkinySkazky.ladushkinnyskazki.skazky.presentation.skazkyFragment

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.ListPopupWindow
import android.widget.TextView
import coil.load
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.skazky.domian.models.SkazkiCatModel

class SkazkaTextDialog {
    companion object {
        fun openBody(context: Context, skazkiCatModel: SkazkiCatModel) {

            val dialog = Dialog(context, R.style.CustomDialogBodySkazka)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_skazka_body)
            dialog.window?.setGravity(Gravity.CENTER)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val img = dialog.findViewById(R.id.img_body_skazka) as ImageView
            val txtName = dialog.findViewById<TextView>(R.id.txt_name_body_skazka)
            val txtBody = dialog.findViewById<TextView>(R.id.txt_body_skazka)

            txtName.text = skazkiCatModel.Items?.NameSkazka
            txtBody.text = skazkiCatModel.Items?.BodySkazka
            img.load(skazkiCatModel.Items?.SkazkaUriPicture) {
                placeholder(R.drawable.background_image)
                crossfade(true)
            }

            dialog.show()
        }
    }
}