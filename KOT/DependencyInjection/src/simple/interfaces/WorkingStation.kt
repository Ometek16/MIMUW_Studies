package simple.interfaces

interface WorkingStation {
    val computer: Computer
    val printer: Printer
    val screen: Screen

    fun work() {
        computer.boot()
        screen.display()
        computer.compute()
        printer.print()
        computer.shutdown()
    }
}