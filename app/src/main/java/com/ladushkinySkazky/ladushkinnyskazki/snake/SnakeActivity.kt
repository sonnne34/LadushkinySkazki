package com.ladushkinySkazky.ladushkinnyskazki.snake

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ladushkinySkazky.ladushkinnyskazki.presentation.MainActivity
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.refactoring.singletons.DisplaySingleton
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeCore.MINIMUM_GAME_SPEED
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeCore.gameSpeed
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeCore.isPlay
import com.ladushkinySkazky.ladushkinnyskazki.snake.SnakeCore.startTheGame
import kotlinx.android.synthetic.main.activity_snake.*

class SnakeActivity : AppCompatActivity() {

    private val allTale = mutableListOf<PartOfTale>()
    private var currentDirections: Direction = Direction.DOWN

    private lateinit var animal: ImageView
    private lateinit var head: ImageView
    private lateinit var bed: ImageView
    private lateinit var playerBackSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake)

        //появляющиеся объекты ("еда" змейки)
        animal = ImageView(this)
        animal.layoutParams =
            FrameLayout.LayoutParams(loadTextHead().toInt(), loadTextHead().toInt())
        animal.setImageResource(R.drawable.hedgehog_two)

        //голова змейки
        head = ImageView(this)
        head.layoutParams = FrameLayout.LayoutParams(loadTextHead().toInt(), loadTextHead().toInt())
        head.setImageResource(R.drawable.unicorn)

        //кроватка
        bed = ImageView(this)
        bed.layoutParams = FrameLayout.LayoutParams(loadTextHead().toInt(), loadTextHead().toInt())
        bed.setImageResource(R.drawable.cradle)

        soundBack()

        //поле для игры
        containerSnake.layoutParams =
            ConstraintLayout.LayoutParams(loadTextWidth().toInt(), loadTextWidth().toInt()).apply {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }

        //запуск игры, создание "еды", первое движение
        startTheGame()
        generateNewAnimal()
        SnakeCore.nextMovie = { move(Direction.RIGHT) }

        //кнопки управления
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
    }

    //загрузка ширины экрана
    private fun loadTextWidth(): String {
        return DisplaySingleton.sizeWidth
    }

    //загрузка размера объектов/шага
    private fun loadTextHead(): String {
        return DisplaySingleton.sizeHead
    }

    //размер шага
    private fun loadSizeShag(): String {
        return (DisplaySingleton.sizeHead.toInt() / 10).toString()
    }

    //проверка на повторное нажатие направления
    private fun checkIfCurrentDirectionIsNotOpposite(
        properDirections: Direction,
        oppositeDirections: Direction
    ) {
        if (currentDirections == oppositeDirections) {
            move(currentDirections)
        } else {
            move(properDirections)
        }
    }

    //генератор новых объектов ("еды")
    private fun generateNewAnimal() {
        val viewCoordinate = generateAnimalCoordinates()
        (animal.layoutParams as FrameLayout.LayoutParams).topMargin =
            viewCoordinate.top
        (animal.layoutParams as FrameLayout.LayoutParams).leftMargin =
            viewCoordinate.left
        containerSnake.removeView(animal)
        containerSnake.addView(animal)
    }

    //координаты нового объекта "еды"
    private fun generateAnimalCoordinates(): ViewCoordinate {
        val viewCoordinate = ViewCoordinate(
            (1 until CELLS_ON_GENERATE).random() * loadTextHead().toInt(),
            (1 until CELLS_ON_GENERATE).random() * loadTextHead().toInt()
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

    //съедение объекта, добавление объекта в тело змейки
    private fun checkIfSnakeEatsPerson() {
        if (head.left == animal.left && head.top == animal.top) {

//        if ( checkTop() && checkLeft()) {
//            val headTop = head.top + loadTextHead().toInt()
//            val headLeft = head.left + loadTextHead().toInt()

            addPartOfTale(head.top, head.left)
//            addPartOfTale(headTop, headLeft)
            soundHello()
            ifFullTale()
        }

    }

    private fun checkTop(): Boolean {
        val sizeLeftMin = head.top - (loadTextHead().toInt() / 2)
        val sizeLeftMax = head.top + (loadTextHead().toInt() / 2)
        var bool = false
        for (i in sizeLeftMin..sizeLeftMax) {
            if (i == animal.top) {
                bool = true
            }
        }
        return bool
    }

    private fun checkLeft(): Boolean {
        val sizeTopMin = head.left - (loadTextHead().toInt() / 2)
        val sizeTopMax = head.left + (loadTextHead().toInt() / 2)
        var bool = false
        for (i in sizeTopMin..sizeTopMax) {
            if (i == animal.left) {
                bool = true
            }
        }
        return bool
    }

    //создание кроватки
    private fun generateBed() {
        val viewCoordinate = generateBedCoordinates()
        (bed.layoutParams as FrameLayout.LayoutParams).topMargin =
            viewCoordinate.top
        (bed.layoutParams as FrameLayout.LayoutParams).leftMargin =
            viewCoordinate.left

        containerSnake.removeView(animal)
        containerSnake.addView(bed)
    }

    //координаты нового объекта кроватка
    private fun generateBedCoordinates(): ViewCoordinate {
        val viewCoordinate = ViewCoordinate(
            (1 until CELLS_ON_GENERATE).random() * loadTextHead().toInt(),
            (1 until CELLS_ON_GENERATE).random() * loadTextHead().toInt()
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

    // в кроватке
    private fun checkIfCompletePerson() {
        if (allTale.size == FULL && head.left == bed.left && head.top == bed.top) {
            isPlay = false
            soundCongratulation()
            showCongratulation()
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

    //увеличение скорости игры
    private fun increaseDifficult() {
        if (gameSpeed <= MINIMUM_GAME_SPEED) {
            return
        }
        if (allTale.size % 3 == 0) {
            gameSpeed -= 100
        }
    }

    //сообщение GAME OVER
    private fun showCongratulation() {
        AlertDialog.Builder(this)
            .setTitle("Спасибо за помощь!")
            .setMessage("Все ёжики пошли спатеньки, и ты, мой дружочек, засыпай!")
            .setPositiveButton("Спокойной ночки!") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setCancelable(false)
            .create()
            .show()
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
//            FrameLayout.LayoutParams(loadTextHead().toInt(), loadTextHead().toInt())
            FrameLayout.LayoutParams(loadTextHead().toInt(), loadTextHead().toInt())
        (taleImage.layoutParams as FrameLayout.LayoutParams).topMargin = top
        (taleImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left

        containerSnake.addView(taleImage)
        return taleImage
    }

    //движение головы змейки
    private fun move(direction: Direction) {
        when (direction) {
            Direction.UP -> {
                moveHeadAndRotate(Direction.UP, 270f, -loadTextHead().toInt())
            }
            Direction.DOWN -> {
                moveHeadAndRotate(Direction.DOWN, 90f, loadTextHead().toInt())
            }
            Direction.LEFT -> {
                moveHeadAndRotate(Direction.LEFT, 180f, -loadTextHead().toInt())
            }
            Direction.RIGHT -> {
                moveHeadAndRotate(Direction.RIGHT, 0f, loadTextHead().toInt())
            }
        }

        runOnUiThread {
            if (checkIfSnakeSmash()) {
                isPlay = false
                soundCrash()
                showScore()
                return@runOnUiThread
            }

            makeTaleMove()
            checkIfSnakeEatsPerson()
            checkIfCompletePerson()
            containerSnake.removeView(head)
            containerSnake.addView(head)
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

    //сообщение GAME OVER
    private fun showScore() {
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

    //проверка врезания в хвост и выхода за размеры игрового поля
    private fun checkIfSnakeSmash(): Boolean {
        for (talePart in allTale) {
            if (allTale.size != 0 && talePart.viewCoordinate.left == head.left && talePart.viewCoordinate.top == head.top) {
                return true
            }
        }
        if (head.top < 0
            || head.left < 0
            || head.top >= loadTextHead().toInt() * CELLS_ON_FIELD
            || head.left >= loadTextHead().toInt() * CELLS_ON_FIELD
        ) {
            return true
        }
        return false
    }

    //движение хвоста
    private fun makeTaleMove() {
        var tempTalePart: PartOfTale? = null
        for (index in 0 until allTale.size) {
            val talePart = allTale[index]
            containerSnake.removeView(talePart.imageView)
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

    private fun play(mPlayer: MediaPlayer) {
        mPlayer.start()
    }

    private fun stopPlay(mPlayer: MediaPlayer) {
        mPlayer.stop()

        try {
            mPlayer.prepare()
            mPlayer.seekTo(0)
        } catch (t: Throwable) {
//            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun soundBack() {
        playerBackSound = MediaPlayer.create(this, R.raw.sverchki)
        play(playerBackSound)
        playerBackSound.setOnCompletionListener { play(playerBackSound) }
    }

    private fun soundPause() {
        val playerPauseSound = MediaPlayer.create(this, R.raw.blbl)
        play(playerPauseSound)
        playerPauseSound.setOnCompletionListener { stopPlay(playerPauseSound) }
    }

    private fun soundControl() {
        val playerMoviSound = MediaPlayer.create(this, R.raw.mrr)
        play(playerMoviSound)
        playerMoviSound.setOnCompletionListener { stopPlay(playerMoviSound) }
    }

    private fun soundHello() {
        val mPlayerHelloSound = MediaPlayer.create(this, R.raw.hello)
//        val mPlayerHelloSound = MediaPlayer.create(this, R.raw.pruvet)
        play(mPlayerHelloSound)
        mPlayerHelloSound.setOnCompletionListener { stopPlay(mPlayerHelloSound) }
    }

    private fun soundCrash() {
        val mPlayerGameOver = MediaPlayer.create(this, R.raw.puks)
        play(mPlayerGameOver)
        mPlayerGameOver.setOnCompletionListener { stopPlay(mPlayerGameOver) }
    }

    private fun soundCongratulation() {
        val mPlayerCongratulationSound = MediaPlayer.create(this, R.raw.zev)
        play(mPlayerCongratulationSound)
        mPlayerCongratulationSound.setOnCompletionListener { stopPlay(mPlayerCongratulationSound) }
    }

    override fun onRestart() {
        super.onRestart()
        play(playerBackSound)
    }

    override fun onStop() {
        super.onStop()
        stopPlay(playerBackSound)
    }

    companion object {
        const val CELLS_ON_FIELD = 10
        const val CELLS_ON_GENERATE = 10
        const val FULL = 7
    }
}