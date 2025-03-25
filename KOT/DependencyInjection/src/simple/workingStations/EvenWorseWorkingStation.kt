package simple.workingStations

import simple.interfaces.*

class EvenWorseWorkingStation : WorkingStation {
    override lateinit var computer: Computer
    override lateinit var printer: Printer
    override lateinit var screen: Screen
}