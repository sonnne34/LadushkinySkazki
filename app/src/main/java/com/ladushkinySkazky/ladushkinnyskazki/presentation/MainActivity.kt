package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.interfaces.ConnectionType
import com.ladushkinySkazky.ladushkinnyskazki.interfaces.NetworkMonitorUtil
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.MainMenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.singletons.DisplaySingleton
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

//    private lateinit var viewModel: SkazkyViewModel

    private lateinit var btnMenu: ImageButton
    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModel = ViewModelProvider(this)[SkazkyViewModel::class.java]
//        viewModel.categoryList.observe(this) {
//
//        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerSnake, MainFragment.newInstance())
                .commitNow()
        }

        btnMenu = findViewById(R.id.img_btn_menu_main)

        btnMenu.setOnClickListener {
            MainMenuDialog.openMenu(this)
        }

        //проверка подключения к интернету
        networkMonitorResult()

        //вычисление размеров объектов для змейки
        sizePx()
    }



    //Вычисление размеров объектов
    private fun sizePx() {
        val displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics
        val pxWidth = displayMetrics.widthPixels - 50
        val sizeHead = pxWidth / CELLS_ON_FIELD
        DisplaySingleton.addWidth(pxWidth.toString())
        DisplaySingleton.addHead(sizeHead.toString())
    }

    //диалоговое окно перед выходом из приложения
    private fun openExitDialog(context: Context) {
        val quitDialog = AlertDialog.Builder(
            context
        )
        quitDialog.setTitle("Выход")
        quitDialog.setTitle("Вы уверенны, что хотите закрыть приложение?")
        quitDialog.setPositiveButton(
            "Да"
        ) { _, _ ->
            onDestroy()
        }
        quitDialog.setNegativeButton(
            "Ой, нет!"
        ) { _, _ -> }
        quitDialog.show()
    }

    //переопределения метода для обработки возвращения к фрагментам
    override fun onBackPressed() {
        //проверяем наличие фрагментов в стеке
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            //открывем диалог о выходе
            openExitDialog(this)
        } else {
            //возвращаемся к пред. фрагменту
            supportFragmentManager.popBackStack()
        }
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
    }
}