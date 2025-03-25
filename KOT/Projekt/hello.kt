// kotlinc hello.kt -include-runtime -d hello.jar
// java -jar hello.jar


import java.util.Scanner

fun main() {
    omegaClearScreen()

    // Define the terminal size
    val rows = 24
    val columns = 80

    // Create a 2D array to represent the terminal screen
    val screen = Array(rows) { CharArray(columns) { '.' } }

    // Initial position of the cursor
    var cursorX = 0
    var cursorY = 0

    // Set the initial position to 'X'
    screen[cursorY][cursorX] = 'X'

    // Clear the screen and display the initial grid
    clearScreen()
    printScreen(screen)

    // Infinite loop to handle movement
    while (true) {
        when (readArrowKey()) {
            "UP" -> {
                if (cursorY > 0) cursorY--
            }
            "DOWN" -> {
                if (cursorY < rows - 1) cursorY++
            }
            "LEFT" -> {
                if (cursorX > 0) cursorX--
            }
            "RIGHT" -> {
                if (cursorX < columns - 1) cursorX++
            }
        }

        // Clear previous 'X'
        screen.forEachIndexed { i, row -> row.fill('.', 0, row.size) }

        // Set the new position to 'X'
        screen[cursorY][cursorX] = 'X'

        // Clear the screen and print the updated grid
        clearScreen()
        printScreen(screen)
    }
}

// Function to print the screen
fun printScreen(screen: Array<CharArray>) {
    for (row in screen) {
        println(row.concatToString())
    }
}

fun omegaClearScreen() {
    print("\u001b[H\u001b[2J")
    System.out.flush()
}

// Function to clear the terminal (works on most Unix-like systems)
fun clearScreen() {
    repeat(10) { print("\u001b[1A") }
    System.out.flush()
}

// Function to read arrow key input
fun readArrowKey2(): String {
    val scanner = Scanner(System.`in`)
    val key = scanner.nextLine()

    return when (key) {
        "\u001B[A" -> "UP"       // Arrow Up
        "\u001B[B" -> "DOWN"     // Arrow Down
        "\u001B[D" -> "LEFT"     // Arrow Left
        "\u001B[C" -> "RIGHT"    // Arrow Right
        else -> ""               // Invalid key
    }
}
