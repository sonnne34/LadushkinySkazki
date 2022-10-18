package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.ConnectionType
import com.ladushkinySkazky.ladushkinnyskazki.domian.NetworkMonitorUtil
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.MainMenuDialog
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var btnMenu: ImageButton
    private val networkMonitor = NetworkMonitorUtil(this)

    private lateinit var mPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPreferences = getSharedPreferences(NAME_PREF, MODE_PRIVATE)
        btnMenu = findViewById(R.id.img_btn_menu_main)
        btnMenu.setOnClickListener {
            MainMenuDialog.openMenu(this)
        }

        //проверка подключения к интернету
        networkMonitorResult()

        //вычисление размеров объектов для змейки
        sizePx()
    }

    private fun setWidth(pxWidth: Int) {
        mPreferences.edit()
            .putString(WIDTH, pxWidth.toString())
            .apply()
    }

    private fun setSizeHead(sizeHead: Int) {
        mPreferences.edit()
            .putString(SIZE_HEAD, sizeHead.toString())
            .apply()
    }

    //Вычисление размеров объектов
    private fun sizePx() {
        if (getSize() == "") {
            val displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics
            val pxWidth = displayMetrics.widthPixels - 50
            val sizeHead = pxWidth / CELLS_ON_FIELD
            setSizeHead(sizeHead)
            setWidth(pxWidth)
        }
    }

    private fun getSize(): String {
        return mPreferences.getString(SIZE_HEAD, "").toString()
    }

    //проверка подключения к интернету
    private fun networkMonitorResult() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Wifi Connection")
                            }
                            ConnectionType.Cellular -> {
                                Log.i("NETWORK_MONITOR_STATUS", "Cellular Connection")
                            }
                            else -> {}
                        }
                    }
                    false -> {
                        Log.i("NETWORK_MONITOR_STATUS", "No Connection")
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
    }

    override fun onResume() {
        super.onResume()
        //проверка подключения к интернету
        networkMonitor.register()

    }

    override fun onPause() {
        super.onPause()
        //остановка проверки подключения к интернету
        networkMonitor.unregister()
    }

    override fun onDestroy() {
        moveTaskToBack(true)
        super.onDestroy()
        exitProcess(0)
    }

    companion object {
        const val CELLS_ON_FIELD = 10
        const val WIDTH = "pxWidth"
        const val SIZE_HEAD = "sizeHead"
        const val NAME_PREF = "preference"
    }
}