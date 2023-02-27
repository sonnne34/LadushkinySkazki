package com.ladushkinySkazky.ladushkinnyskazki.presentation.snakeActivity

object SnakeCore {

    private const val START_GAME_SPEED = 900L
    const val MINIMUM_GAME_SPEED = 400L

    var nextMovie: () -> Unit = {}
    var isPlay = true
    private val thread: Thread
    var gameSpeed = START_GAME_SPEED

    init {
        thread = Thread {
            while (true) {
                Thread.sleep(gameSpeed)
                if (isPlay) {
                    nextMovie()
                }
            }
        }
        thread.start()
    }

    fun startTheGame() {
        gameSpeed = START_GAME_SPEED
        isPlay = true

    }

}