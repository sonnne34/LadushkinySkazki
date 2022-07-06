package com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.ListPopupWindow
import android.widget.TextView
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.loaders.LoadImage
import com.ladushkinySkazky.ladushkinnyskazki.data.models.SkazkiCatModel

class SkazkaBodyDialog {
    companion object{
        fun openBody(context: Context, skazkiCatModel: SkazkiCatModel){

            var catModel = skazkiCatModel
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

            txtName.text = catModel.Items?.NameSkazka
            txtBody.text = catModel.Items?.BodySkazka.toString()
            LoadImage().loadImageNameSkazka(context, catModel, img)

            dialog.show()
        }
    }
}