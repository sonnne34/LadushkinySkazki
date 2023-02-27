package com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.ActivitySnakeBinding
import com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity.SnakeCore.MINIMUM_GAME_SPEED
import com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity.SnakeCore.gameSpeed
import com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity.SnakeCore.isPlay
import com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity.SnakeCore.startTheGame

class SnakeActivity : AppCompatActivity() {

    private var _binding: ActivitySnakeBinding? = null
    private val binding: ActivitySnakeBinding
        get() = _binding ?: throw RuntimeException("ActivitySnakeBinding == null")

    private val allTale = mutableListOf<PartOfTale>()
    private var currentDirections: Direction = Direction.DOWN

    private lateinit var container: FrameLayout
    private lateinit var animal: ImageView
    private lateinit var head: ImageView
    private lateinit var bed: ImageView
    private lateinit var playerBackSound: MediaPlayer
    private lateinit var mPreferences: SharedPreferences
    private var loadTextWidth = 0
    private var loadTextHead = 0
    private var speed: Long = 0
    private var isMusic = true
    private var isSound = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sizePx()

        _binding = ActivitySnakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openWelcomeDialog()

        mPreferences = getSharedPreferences(NAME_PREF, MODE_PRIVATE)

        getSharedPreferences()
        setupView()
        setupOnClick()
        onBackPress(this)
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

    private fun getSharedPreferences() {
        loadTextWidth = mPreferences.getInt(SNAKE_WIDTH, 0)
        loadTextHead = mPreferences.getInt(SNAKE_SIZE_HEAD, 0)
        isMusic = mPreferences.getBoolean(IS_MUSIC, true)
        isSound = mPreferences.getBoolean(IS_SOUND, true)
    }

    private fun setupStartGame() {
        soundBack()
        isPlay = false
        generateNewAnimal()
        SnakeCore.nextMovie = { move(Direction.RIGHT) }
    }

    private fun setupView() {

        if (isMusic) {
            binding.icMusic.setImageResource(R.drawable.ic_music_on)
        } else {
            binding.icMusic.setImageResource(R.drawable.ic_music_off)
        }

        if (isSound) {
            binding.icSound.setImageResource(R.drawable.ic_sound_on)
        } else {
            binding.icSound.setImageResource(R.drawable.ic_sound_off)
        }

        //появляющиеся объекты ("еда" змейки)
        animal = ImageView(this)
        animal.layoutParams =
            FrameLayout.LayoutParams(loadTextHead, loadTextHead)
        animal.setImageResource(R.drawable.hedgehog_two)

        //голова змейки
        head = ImageView(this)
        head.layoutParams = FrameLayout.LayoutParams(loadTextHead, loadTextHead)
        head.setImageResource(R.drawable.unicorn)

        //кроватка
        bed = ImageView(this)
        bed.layoutParams = FrameLayout.LayoutParams(loadTextHead, loadTextHead)
        bed.setImageResource(R.drawable.home)

        //поле для игры
        container = binding.containerGame
        container.layoutParams =
            ConstraintLayout.LayoutParams(loadTextWidth, loadTextWidth).apply {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupOnClick() {
        //кнопки управления
        with(binding) {

            icQuestion.setOnClickListener {
                isPlay = false
                icPause.setImageResource(R.drawable.ic_play)
                openQuestionDialog()
            }

            icMusic.setOnClickListener {
                isMusic = !isMusic
                mPreferences.edit()
                    .putBoolean(IS_MUSIC, isMusic)
                    .apply()
                if (isMusic) {
                    icMusic.setImageResource(R.drawable.ic_music_on)
                    soundBack()
                } else {
                    icMusic.setImageResource(R.drawable.ic_music_off)
                    stopPlay(playerBackSound)
                }
            }

            icSound.setOnClickListener {
                isSound = !isSound
                mPreferences.edit()
                    .putBoolean(IS_SOUND, isSound)
                    .apply()
                if (isSound) {
                    icSound.setImageResource(R.drawable.ic_sound_on)
                } else {
                    icSound.setImageResource(R.drawable.ic_sound_off)
                }
            }

            icUp.setOnClickListener {
                SnakeCore.nextMovie =
                    { checkIfCurrentDirectionIsNotOpposite(Direction.UP, Direction.DOWN) }
            }
            icDown.setOnClickListener {
                SnakeCore.nextMovie =
                    { checkIfCurrentDirectionIsNotOpposite(Direction.DOWN, Direction.UP) }
            }
            icLeft.setOnClickListener {
                SnakeCore.nextMovie =
                    { checkIfCurrentDirectionIsNotOpposite(Direction.LEFT, Direction.RIGHT) }
            }
            icRight.setOnClickListener {
                SnakeCore.nextMovie =
                    { checkIfCurrentDirectionIsNotOpposite(Direction.RIGHT, Direction.LEFT) }
            }
            icPause.setOnClickListener {
                soundPause()
                if (isPlay) {
                    icPause.setImageResource(R.drawable.ic_play)
                } else {
                    icPause.setImageResource(R.drawable.ic_pause)
                }
                isPlay = !isPlay
            }

            icSpeed.performClick()
            icSpeed.setOnTouchListener { v, event ->
                when (v) {
                    icSpeed -> {
                        when (event?.action) {
                            MotionEvent.ACTION_DOWN -> {
                                speed = gameSpeed
                                gameSpeed = 250
                            }
                            MotionEvent.ACTION_UP -> {
                                gameSpeed = speed
                            }
                        }
                    }
                }
                true
            }
        }
    }


    //генератор новых объектов ("еды")
    private fun generateNewAnimal() {
        val viewCoordinate = generateAnimalCoordinates()
        (animal.layoutParams as FrameLayout.LayoutParams).topMargin =
            viewCoordinate.top
        (animal.layoutParams as FrameLayout.LayoutParams).leftMargin =
            viewCoordinate.left
        container.removeView(animal)
        container.addView(animal)
    }

    //координаты нового объекта "еды"
    private fun generateAnimalCoordinates(): ViewCoordinate {
        val viewCoordinate = ViewCoordinate(
            (1 until CELLS_ON_GENERATE).random() * loadTextHead,
            (1 until CELLS_ON_GENERATE).random() * loadTextHead
        )
        for (partTale in allTale) {
            if (partTale.viewCoordinate == viewCoordinate) {
                return generateAnimalCoordinates()
            }
        }
        if (head.top == viewCoordinate.top && head.left == viewCoordinate.left) {
            return generateAnimalCoordinates()
        }
        return viewCoordinate
    }

    //создание кроватки
    private fun generateBed() {
        val viewCoordinate = generateBedCoordinates()
        (bed.layoutParams as FrameLayout.LayoutParams).topMargin =
            viewCoordinate.top
        (bed.layoutParams as FrameLayout.LayoutParams).leftMargin =
            viewCoordinate.left

        container.removeView(animal)
        container.addView(bed)
    }

    //координаты нового объекта кроватка
    private fun generateBedCoordinates(): ViewCoordinate {
        val viewCoordinate = ViewCoordinate(
            (1 until CELLS_ON_GENERATE).random() * loadTextHead,
            (1 until CELLS_ON_GENERATE).random() * loadTextHead
        )
        for (partTale in allTale) {
            if (partTale.viewCoordinate == viewCoordinate) {
                return generateBedCoordinates()
            }
        }
        if (head.top == viewCoordinate.top && head.left == viewCoordinate.left) {
            return generateBedCoordinates()
        }
        return viewCoordinate
    }

    //съедение объекта, добавление объекта в тело змейки
    private fun checkIfSnakeEatsPerson() {
        if (head.left == animal.left && head.top == animal.top) {
            addPartOfTale(head.top, head.left)
            soundHello()
            ifFullTale()
        }
    }

    //собраны ли все друзья
    private fun ifFullTale() {
        if (allTale.size == FULL) {
            generateBed()
        } else {
            generateNewAnimal()
            increaseDifficult()
        }
    }

    // в кроватке
    private fun checkIfCompletePerson() {
        if (allTale.size == FULL && head.left == bed.left && head.top == bed.top) {
            isPlay = false
            soundCongratulation()
            openCongratulationDialog()
        }
    }

    //увеличение скорости игры
    private fun increaseDifficult() {
        if (gameSpeed <= MINIMUM_GAME_SPEED) {
            return
        }
        if (allTale.size % 3 == 0) {
            gameSpeed -= 100
        }
    }

    //хвост змейки
    private fun addPartOfTale(top: Int, left: Int) {
        val talePart = drawPartOfTale(top, left)
        allTale.add(PartOfTale(ViewCoordinate(top, left), talePart))
    }

    //создание хвоста тела
    private fun drawPartOfTale(top: Int, left: Int): ImageView {
        val taleImage = ImageView(this)
        taleImage.setImageResource(R.drawable.hedgehog_two)
        taleImage.layoutParams =
            FrameLayout.LayoutParams(loadTextHead, loadTextHead)
        (taleImage.layoutParams as FrameLayout.LayoutParams).topMargin = top
        (taleImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left

        container.addView(taleImage)
        return taleImage
    }

    //движение головы змейки
    private fun move(direction: Direction) {
        when (direction) {
            Direction.UP -> {
                moveHeadAndRotate(Direction.UP, 270f, -loadTextHead)
            }
            Direction.DOWN -> {
                moveHeadAndRotate(Direction.DOWN, 90f, loadTextHead)
            }
            Direction.LEFT -> {
                moveHeadAndRotate(Direction.LEFT, 180f, -loadTextHead)
            }
            Direction.RIGHT -> {
                moveHeadAndRotate(Direction.RIGHT, 0f, loadTextHead)
            }
        }

        runOnUiThread {
            if (checkIfSnakeSmash()) {
                isPlay = false
                soundCrash()
                openScoreDialog()
                return@runOnUiThread
            }

            makeTaleMove()
            checkIfSnakeEatsPerson()
            checkIfCompletePerson()
            container.removeView(head)
            container.addView(head)
        }
    }

    //проверка на повторное нажатие направления
    private fun checkIfCurrentDirectionIsNotOpposite(
        properDirections: Direction,
        oppositeDirections: Direction,
    ) {
        if (currentDirections == oppositeDirections) {
            move(currentDirections)
        } else {
            move(properDirections)
        }
    }

    //повороты головы змейки
    private fun moveHeadAndRotate(direction: Direction, angle: Float, coordinates: Int) {
        runOnUiThread {
            head.rotation = angle
            when (direction) {
                Direction.UP, Direction.DOWN -> {
                    (head.layoutParams as FrameLayout.LayoutParams).topMargin += coordinates
                }
                Direction.LEFT, Direction.RIGHT -> {
                    (head.layoutParams as FrameLayout.LayoutParams).leftMargin += coordinates
                }
            }
            currentDirections = direction
        }
    }

    //движение хвоста
    private fun makeTaleMove() {
        var tempTalePart: PartOfTale? = null
        for (index in 0 until allTale.size) {
            val talePart = allTale[index]
            container.removeView(talePart.imageView)
            if (index == 0) {
                tempTalePart = talePart
                allTale[index] = PartOfTale(
                    ViewCoordinate(head.top, head.left),
                    drawPartOfTale(head.top, head.left)
                )
            } else {
                val anotherTempPartOfTale = allTale[index]
                tempTalePart?.let {
                    allTale[index] = PartOfTale(
                        it.viewCoordinate,
                        drawPartOfTale(it.viewCoordinate.top, it.viewCoordinate.left)
                    )
                }
                tempTalePart = anotherTempPartOfTale
            }
        }
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    //проверка врезания в хвост и выхода за размеры игрового поля
    private fun checkIfSnakeSmash(): Boolean {
        for (talePart in allTale) {
            if (allTale.size != 0 && talePart.viewCoordinate.left == head.left && talePart.viewCoordinate.top == head.top) {
                return true
            }
        }
        if (head.top < 0
            || head.left < 0
            || head.top >= loadTextHead * CELLS_ON_FIELD
            || head.left >= loadTextHead * CELLS_ON_FIELD
        ) {
            return true
        }
        return false
    }

    //Звуки:

    private fun soundBack() {
        playerBackSound = MediaPlayer.create(this, R.raw.sverchki)
        if (isMusic) {
            play(playerBackSound)
            playerBackSound.setOnCompletionListener { play(playerBackSound) }
        } else {
            stopPlay(playerBackSound)

        }
    }

    private fun soundPause() {
        if (isSound) {
            val playerPauseSound = MediaPlayer.create(this, R.raw.blbl)
            play(playerPauseSound)
            playerPauseSound.setOnCompletionListener { stopPlay(playerPauseSound) }
        }
    }

    private fun soundHello() {
        if (isSound) {
            val mPlayerHelloSound = MediaPlayer.create(this, R.raw.hello)
            play(mPlayerHelloSound)
            mPlayerHelloSound.setOnCompletionListener { stopPlay(mPlayerHelloSound) }
        }
    }

    private fun soundCrash() {
        if (isSound) {
            val mPlayerGameOver = MediaPlayer.create(this, R.raw.puks)
            play(mPlayerGameOver)
            mPlayerGameOver.setOnCompletionListener { stopPlay(mPlayerGameOver) }
        }
    }

    private fun soundCongratulation() {
        if (isSound) {
            val mPlayerCongratulationSound = MediaPlayer.create(this, R.raw.zev)
            play(mPlayerCongratulationSound)
            mPlayerCongratulationSound.setOnCompletionListener { stopPlay(mPlayerCongratulationSound) }
        }
    }

    private fun play(mPlayer: MediaPlayer) {
        mPlayer.start()
    }

    private fun stopPlay(mPlayer: MediaPlayer) {
        mPlayer.stop()
        try {
            mPlayer.prepare()
            mPlayer.seekTo(0)
        } catch (t: Throwable) {
            Log.d("Exception", "Throwable $t")
        }
    }

    //Диалоги:

    private fun openQuestionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Правила игры:")
            .setMessage(
                "Нужно помочь Понюшке собрать всех ёжиков и отвести их домой.\n" +
                        "За пределы поля выходить нельзя.\n" +
                        "В цепочку ёжиков врезаться нельзя.\n" +
                        "Когда будет собрано 7 ёжиков, домик появится.\n" +
                        "Радоваться успехам - нужно! :)"
            )
            .setPositiveButton("Понятненько)") { _, _ ->
            }
            .setCancelable(false)
            .show()
    }

    private fun openWelcomeDialog() {
        AlertDialog.Builder(this)
            .setTitle("Вперёд!")
            .setMessage(
                "Нужно помочь Понюшке собрать всех ёжиков и отвести их домой.\n" +
                        "Помни, за пределы поля выходить нельзя, в цепочку ёжиков врезаться нельзя.\n" +
                        "Радоваться успехам - нужно! :)"
            )
            .setPositiveButton("К победе!)") { _, _ ->
                setupStartGame()
                startTheGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun openScoreDialog() {
        AlertDialog.Builder(this)
            .setTitle("Ой!")
            .setMessage("Собрано друзей: ${allTale.size}!")
            .setPositiveButton("ок") { _, _ ->
                this.recreate()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun openCongratulationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Спасибо за помощь!")
            .setMessage("Все ёжики пошли спатеньки, и ты, мой дружочек, засыпай!")
            .setPositiveButton("Спокойной ночки!") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onRestart() {
        super.onRestart()
        if (isMusic) {
            play(playerBackSound)
        }
    }

    override fun onStop() {
        if (isMusic) {
            stopPlay(playerBackSound)
        }
        isPlay = false
        super.onStop()

    }

    private fun onBackPress(context: Context) {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isPlay = false
                    AlertDialog.Builder(context)
                        .setTitle("Уже наигрались?")
                        .setPositiveButton("Да!") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("Ой, нет!") { _, _ ->
                            isPlay = true
                        }
                        .setCancelable(false)
                        .create()
                        .show()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val CELLS_ON_FIELD = 10
        const val CELLS_ON_GENERATE = 10
        const val FULL = 7
        private const val IS_MUSIC = "isMusic"
        private const val IS_SOUND = "isSound"
        const val SNAKE_CELLS_ON_FIELD = 10
        const val SNAKE_WIDTH = "snakeWidth"
        const val SNAKE_SIZE_HEAD = "snakeSizeHead"
        const val NAME_PREF = "preference"

    }
}