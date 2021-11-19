package com.ladushkinySkazky.ladushkinnyskazki

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.ladushkinySkazky.ladushkinnyskazki.dialog.MenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var btnMenu: ImageButton
    private val HELLO = "HELLO"
    private lateinit var sHello: SharedPreferences
    private var saveHello: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        btnMenu = findViewById(R.id.img_btn_menu_main)

        btnMenu.setOnClickListener {
            MenuDialog.openMenu(this)
        }

        //        loadHello()
//        if(saveHello != "Hello"){
//            openHelloDialog()
//        }


    }

//    private fun openHelloDialog() {
//        val helloDialog = AlertDialog.Builder(
//            this
//        )
//        helloDialog.setTitle("Ура!")
//        helloDialog.setTitle("Очень рады приветсвовать Вас!")
//        helloDialog.setPositiveButton(
//            "И мне радостно!"
//        ) { _, _ ->
//            val save = "Hello"
//            saveHello(save)
//            finish()
//        }
//        helloDialog.setNegativeButton(
//            "ну, привет..."
//        ) { _, _ ->
//            val save = "Hello"
//            saveHello(save)
//            finish()
//        }
//
//        helloDialog.show()
//    }
//
//    private fun loadHello() {
//
//        //загружаем
//        sHello = getPreferences(MODE_PRIVATE)
//        saveHello = sHello.getString(HELLO, "").toString()
//
//    }

//    private fun saveHello(hello: String) {
//
//        //сохраняем
//        sHello = getPreferences(MODE_PRIVATE)
//        val sl: SharedPreferences.Editor = sHello.edit()
//        sl.putString(HELLO, hello).toString()
//        sl.apply()
//    }

    private fun openQuitDialog() {
        val quitDialog = AlertDialog.Builder(
            this
        )
        quitDialog.setTitle("Выход")
        quitDialog.setTitle("Вы уверенны, что хотите выйти?")
        quitDialog.setPositiveButton(
            "Да"
        ) { _, _ ->
            onDestroy()
//            finish()
        }
        quitDialog.setNegativeButton(
            "Ой, нет!"
        ) { _, _ -> }
        quitDialog.show()
    }

//    override fun onBackPressed() {
//        openQuitDialog()
//    }

    override fun onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }
}