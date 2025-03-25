package simple.workingStations

import simple.interfaces.*

class DecentWorkingStation(override val computer: Computer, override val printer: Printer, override val screen: Screen) : WorkingStation {

}