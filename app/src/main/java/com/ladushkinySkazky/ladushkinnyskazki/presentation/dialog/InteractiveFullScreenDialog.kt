package com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.ListPopupWindow
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.data.loadFirebase.LoadImage
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.InteractiveModel

class InteractiveFullScreenDialog {
    companion object {
        fun openFullscreen(context: Context, interactiveModel: InteractiveModel) {

            val dialog = Dialog(context, R.style.CustomDialogInteractiveFullScreen)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_interactive_fullscreen)
            dialog.window?.setGravity(Gravity.CENTER)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val img = dialog.findViewById(R.id.img_interactive_fullscreen) as ImageView
            LoadImage().loadFullImageInteractive(context, interactiveModel, img)

            dialog.show()
        }
    }
}