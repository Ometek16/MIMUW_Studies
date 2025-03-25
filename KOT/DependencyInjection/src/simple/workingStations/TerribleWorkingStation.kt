package simple.workingStations

import simple.interfaces.*

class TerribleWorkingStation() : WorkingStation {
    override val computer = Computer()
    override val printer = Printer()
    override val screen = Screen()
}