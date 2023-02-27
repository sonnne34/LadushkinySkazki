package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.ListPopupWindow
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ladushkinySkazky.ladushkinnyskazki.R
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

            if (interactiveModel.ImageForLoad == null) {
                FirebaseStorage
                    .getInstance()
                    .getReferenceFromUrl(interactiveModel.Image)
                    .downloadUrl.addOnSuccessListener { uri ->
                        interactiveModel.ImageForLoad = uri
                        img.load(uri) {
                            crossfade(true)
                        }
                    }
            } else {
                img.load(interactiveModel.ImageForLoad) {
                    crossfade(true)
                }
            }
            dialog.show()
        }
    }
}