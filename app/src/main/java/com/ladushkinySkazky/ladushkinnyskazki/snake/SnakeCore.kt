package com.ladushkinySkazky.ladushkinnyskazki.snake

const val START_GAME_SPEED = 800L
const val MINIMUM_GAME_SPEED = 100L

object SnakeCore {
    var nextMovie: () -> Unit = {}
    var isPlay = true
    private val thread: Thread
    var gameSpeed = START_GAME_SPEED

    init {
        thread = Thread(Runnable {
            while (true) {
                Thread.sleep(gameSpeed)
                if (isPlay) {
                    nextMovie()
                }
            }
        })
        thread.start()
    }

    fun startTheGame() {
        gameSpeed = START_GAME_SPEED
        isPlay = true
    }

}