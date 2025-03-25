package com.GameOfLife

import java.io.Console
import kotlinx.coroutines.*

class _GameOfLife {
    val ROWS = 24
    val COLUMNS = 80

    var cursorX = 0
    var cursorY = 0

    fun initScreen() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    fun printScreen(time: String, screen: Array<CharArray>) {
        repeat(ROWS + 1) { print("\u001b[1A") }
        println("Game Of Life\t\t\t $time \t\t\tSpeed: -----")
        for (i in 0 until ROWS) {  // Change to 'until' instead of '..<' for Kotlin
            print("\r")
            for (j in 0 until COLUMNS) { // Same change here
                if (j == cursorX && i == cursorY) print("\u001b[48;5;250m")
                print(screen[i][j])
                if (j == cursorX && i == cursorY) print("\u001b[0m")
            }
            print("\n\r")
        }
        System.out.flush()
    }

    fun exitScreen() {
        repeat(ROWS + 1) { print("\u001b[1A") }
        print("\u001b[2K")
        println("Thank you for playing!!\r")
        System.out.flush()
    }

    // Function to read arrow key input
    fun readArrowKey(): String {
        val arrowChar = System.`in`.read().toChar()

        return when (arrowChar) {
            'A' -> "UP"       // Arrow Up
            'B' -> "DOWN"     // Arrow Down
            'D' -> "LEFT"     // Arrow Left
            'C' -> "RIGHT"    // Arrow Right
            else -> ""        // Invalid key
        }
    }
}

// Define the main function at the top level
fun _main(args: Array<String>) {
    // Create an instance of GameOfLife
    val game = _GameOfLife()

    // Create a 2D array to represent the terminal screen
    val screen = Array(game.ROWS) { CharArray(game.COLUMNS) { '.' } }

    val console: Console? = System.console()
    if (console == null) {
        println("No console available.")
        return
    }

    // Set terminal to raw mode to capture each keypress without Enter
    Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", "stty raw -echo < /dev/tty")).waitFor()

    game.initScreen()


    try {
        while (true) {
            game.printScreen("", screen)
            val key = System.`in`.read()
            // Check for ESC key (ASCII 27)
            if (key.toChar() == 'q') {
                game.exitScreen()
                break
            }

            // Check for arrow keys (27 91 ?)
            if (key == 91) { // '[' character
                when (game.readArrowKey()) {
                    "UP" -> if (game.cursorY > 0) game.cursorY--
                    "DOWN" -> if (game.cursorY < game.ROWS - 1) game.cursorY++
                    "LEFT" -> if (game.cursorX > 0) game.cursorX--
                    "RIGHT" -> if (game.cursorX < game.COLUMNS - 1) game.cursorX++
                }
            }

            if (key == 32) {
                screen[game.cursorY][game.cursorX] = if (screen[game.cursorY][game.cursorX] == '.') 'X' else '.'
            }
        }
    } finally {
        // Restore terminal to normal mode
        Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", "stty -raw echo < /dev/tty")).waitFor()
    }
}
