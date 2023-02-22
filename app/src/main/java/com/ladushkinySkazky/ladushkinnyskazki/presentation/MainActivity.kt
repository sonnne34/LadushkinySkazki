package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.NetworkMonitorUtil
import com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment.AboutUsMainMenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment.FeedbackMainMenuDialog

class MainActivity : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkMonitorResult()
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_us -> {
                AboutUsMainMenuDialog.openMenu(this)
            }
            R.id.feedback -> {
                FeedbackMainMenuDialog.openMenu(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun networkMonitorResult() {
        networkMonitor.result = { isAvailable, _ ->
            runOnUiThread {
                if (!isAvailable) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.toast_check_network),
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
}