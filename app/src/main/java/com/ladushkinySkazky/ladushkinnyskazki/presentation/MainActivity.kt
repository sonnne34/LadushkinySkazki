package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.NetworkMonitorUtil
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.MainMenuDialog

class MainActivity : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMenu = findViewById<ImageButton>(R.id.img_btn_menu_main)
        btnMenu.setOnClickListener { MainMenuDialog.openMenu(this) }

        networkMonitorResult()
        sizePx()
    }

    private fun sizePx() {
        val mPreferences = getSharedPreferences(NAME_PREF, MODE_PRIVATE)
        if (!mPreferences.contains(WIDTH_SNAKE)) {
            val displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics
            val pxWidth = displayMetrics.widthPixels - 50
            val sizeHead = pxWidth / SNAKE_CELLS_ON_FIELD
            mPreferences.edit()
                .putString(WIDTH_SNAKE, pxWidth.toString())
                .putString(SIZE_HEAD_SNAKE, sizeHead.toString())
                .apply()
        }
    }

    private fun networkMonitorResult() {
        networkMonitor.result = { isAvailable, _ ->
            runOnUiThread {
                if (!isAvailable) {
                    Toast.makeText(
                        applicationContext,
                        "Проверьте подключение к интернету",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onPause() {
        super.onPause()
        networkMonitor.unregister()
    }

    companion object {
        const val SNAKE_CELLS_ON_FIELD = 10
        const val WIDTH_SNAKE = "pxWidth"
        const val SIZE_HEAD_SNAKE = "sizeHead"
        const val NAME_PREF = "preference"
    }
}