package com.ladushkinySkazky.ladushkinnyskazki.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.domian.NetworkMonitorUtil
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.AboutUsMainMenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.presentation.dialog.FeedbackMainMenuDialog
import com.ladushkinySkazky.ladushkinnyskazki.snake.ViewCoordinate
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private var timerOne: CountDownTimer? = null
    private var timerTwo: CountDownTimer? = null
    private var timerThree: CountDownTimer? = null
    private var timerFour: CountDownTimer? = null
    private var timerFive: CountDownTimer? = null
    private var timerSix: CountDownTimer? = null

    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch { snow() }
        container = findViewById(R.id.activity_main_container)

        Log.d("timer", "onCreate")



        networkMonitorResult()
        sizePx()
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    private suspend fun snow() {

        val snowOne = ImageView(this)
        val snowTwo = ImageView(this)
        val snowThree = ImageView(this)
        val snowFour = ImageView(this)
        val snowFive = ImageView(this)
        val snowSix = ImageView(this)

        snowOne.setImageResource(R.drawable.snowflake_one)
        snowTwo.setImageResource(R.drawable.snowflake_two)
        snowThree.setImageResource(R.drawable.snowflake_three)
        snowFour.setImageResource(R.drawable.snowflake_four)
        snowFive.setImageResource(R.drawable.snowflake_five)
        snowSix.setImageResource(R.drawable.snowflake_six)

        delay((5_000..7_000).random().toLong())
        startTimerOne(snowOne)
        delay((1_000..5_000).random().toLong())
        startTimerTwo(snowTwo)
        delay((1_000..5_000).random().toLong())
        startTimerThree(snowThree)
        delay((1_000..5_000).random().toLong())
        startTimerFour(snowFour)
        delay((1_000..5_000).random().toLong())
        startTimerFive(snowFive)
        delay((1_000..5_000).random().toLong())
        startTimerSix(snowSix)

    }

    private fun startTimerOne(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerOne = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerOne ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerOne?.start()
    }

    private fun startTimerTwo(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerTwo = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerTwo ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerTwo?.start()
    }

    private fun startTimerThree(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerThree = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerThree ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerThree?.start()
    }

    private fun startTimerFour(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerFour = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerFour ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerFour?.start()
    }

    private fun startTimerFive(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerFive = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerFive ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerFive?.start()
    }

    private fun startTimerSix(image: ImageView) {
        val timerPeriod = (20_000..30_000).random().toLong()
        timerSix = object : CountDownTimer(Long.MAX_VALUE, timerPeriod) {
            override fun onTick(millisUntilFinished: Long) {
                generateSnow(image)
                Log.d("timer", "timerSix ${image.drawable}")
            }

            override fun onFinish() {}
        }
        timerSix?.start()
    }

    private fun generateSnow(snow: ImageView) {
        val sizeSnow = (32..128).random()
        val viewCoordinate = generateSnowCoordinates()

        snow.layoutParams = FrameLayout.LayoutParams(sizeSnow, sizeSnow)
        (snow.layoutParams as FrameLayout.LayoutParams).topMargin =
            viewCoordinate.top
        (snow.layoutParams as FrameLayout.LayoutParams).leftMargin =
            viewCoordinate.left

        animSnow(snow)
        container.removeView(snow)
        container.addView(snow)
    }

    private fun generateSnowCoordinates(): ViewCoordinate {
        val displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics
        val pxWidth = displayMetrics.widthPixels
        val pxHeight = displayMetrics.heightPixels

        return ViewCoordinate(
            (1 until pxHeight).random(),
            (1 until pxWidth).random()
        )
    }

    private fun animSnow(image: ImageView) {
        image.clearAnimation()
        //анимация альфа канала (прозрачности от 0 до 1)
        val animation: Animation = AlphaAnimation(1.0f, 0.0f)
        //длительность анимации 1/10 секунды
        animation.duration = 15_000
        //сдвижка начала анимации (с середины)
        animation.startOffset = 100
        //режим повтора - сначала или в обратном порядке
        animation.repeatMode = Animation.START_ON_FIRST_FRAME
        //режим повтора (бесконечно)
        animation.repeatCount = 1
        //накладываем анимацию
        image.startAnimation(animation)
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

    private fun sizePx() {
        val mPreferences = getSharedPreferences(NAME_PREF, MODE_PRIVATE)
        if (!mPreferences.contains(SNAKE_WIDTH)) {
            val displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics
            val pxWidth = displayMetrics.widthPixels - 50
            val sizeHead = pxWidth / SNAKE_CELLS_ON_FIELD
            mPreferences.edit()
                .putInt(SNAKE_WIDTH, pxWidth)
                .putInt(SNAKE_SIZE_HEAD, sizeHead)
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

    override fun onRestart() {
        super.onRestart()
        coroutineScope.launch { snow() }
        Log.d("timer", "onRestart coroutineScope.cancel() ${coroutineScope.isActive}")
        Log.d("timer", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
        Log.d("timer", "onResume")
    }

    override fun onPause() {
        super.onPause()
        networkMonitor.unregister()
        Log.d("timer", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("timer", "onStop")
        Log.d("timer", "timer?.cancel()")
        timerOne?.cancel()
        timerTwo?.cancel()
        timerThree?.cancel()
        timerFour?.cancel()
        timerFive?.cancel()
        timerSix?.cancel()
        coroutineScope.cancel()
        Log.d("timer", "coroutineScope.cancel() ${coroutineScope.isActive}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("timer", "onDestroy")
    }

    companion object {
        const val SNAKE_CELLS_ON_FIELD = 10
        const val SNAKE_WIDTH = "snakeWidth"
        const val SNAKE_SIZE_HEAD = "snakeSizeHead"
        const val NAME_PREF = "preference"
    }
}