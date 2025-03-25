import java.io.Console
import kotlinx.coroutines.*
import java.util.*

const val ROWS = 24
const val COLUMNS = 80

var cursorX = 0
var cursorY = 0

fun initScreen() {
    print("\u001b[H\u001b[2J")
    System.out.flush()
}

fun printScreen(message: String, screen: Array<CharArray>) {
    repeat(ROWS + 1) { print("\u001b[1A") }
    println("Game Of Life\t\t\tSpeed: -----")
    for (i in 0..<ROWS) {
        print("\r")
        for (j in 0..<COLUMNS) {
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


fun main() {
    // Create a 2D array to represent the terminal screen
    val screen = Array(ROWS) { CharArray(COLUMNS) { '.' } }

    val console: Console? = System.console()
    if (console == null) {
        println("No console available.")
        return
    }

    // Set terminal to raw mode to capture each keypress without Enter
    Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", "stty raw -echo < /dev/tty")).waitFor()

    initScreen()

    val job = launch {
        while (true) {
            println("\u001b[H\u001b[2J") // Clear the screen (ANSI escape code)
            println("Screen updated at: ${System.currentTimeMillis()}") // Update the screen with the current time
            delay(1000) // Wait for 1 second
        }
    }

    try {
        while (true) {
            printScreen("", screen)
            val key = System.`in`.read()
            // Check for ESC key (ASCII 27)
            if (key.toChar() == 'q') {
                exitScreen()
                break
            }

            // Check for arrow keys (27 91 ?)
            if (key == 91) { // '[' character
                when (readArrowKey()) {
                    "UP" -> if (cursorY > 0) cursorY--
                    "DOWN" -> if (cursorY < ROWS - 1) cursorY++
                    "LEFT" -> if (cursorX > 0) cursorX--
                    "RIGHT" -> if (cursorX < COLUMNS - 1) cursorX++
                }
            }

            if (key == 32) {
                screen[cursorY][cursorX] = if (screen[cursorY][cursorX] == '.') 'X' else '.'
            }

        }
    } finally {
        // Restore terminal to normal mode
        Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", "stty -raw echo < /dev/tty")).waitFor()
    }
}


fun main2() {
    println("===== ===== =====")
    println("+++++ +++++ +++++")

    // Move the cursor up by 2 lines to reach the line with "====="
    repeat(2) { print("\u001b[1A") }
    System.out.flush()
    print("\u001b[6C")
    println("-----")
    println()
    System.out.flush()
}