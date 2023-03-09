package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment.AboutUsMainMenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.presentation.mainFragment.MainFragmentDirections

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
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.about_us -> {
                AboutUsMainMenuDialog.openMenu(this)
            }
            R.id.feedback -> {
                findNavController(R.id.container).navigate(
                    MainFragmentDirections.actionMainFragmentToFeedbackFragment()
                )
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val labelFragment = findNavController(R.id.container).currentDestination?.label.toString()
        if (labelFragment != "fragment_main") {
            menu?.clear()
        }
        return super.onPrepareOptionsMenu(menu)
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