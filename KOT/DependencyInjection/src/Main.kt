import simple.interfaces.*
import simple.workingStations.*

fun terribleWorkingStation() {
    System.out.println("===== ===== =====")
    val terribleWorkingStation = TerribleWorkingStation()
    terribleWorkingStation.work()
    System.out.println("===== ===== =====")
}

fun evenWorseWorkingStation() {
    System.out.println("===== ===== =====")
    val evenWorseWorkingStation = EvenWorseWorkingStation()
    evenWorseWorkingStation.screen = Screen()
    evenWorseWorkingStation.printer = Printer()
    evenWorseWorkingStation.computer = Computer()
    evenWorseWorkingStation.work()
    System.out.println("===== ===== =====")
}

fun decentWorkingStation() {
    System.out.println("===== ===== =====")
    val decentWorkingStation = DecentWorkingStation(Computer(), Printer(), Screen())
    decentWorkingStation.work()
    System.out.println("===== ===== =====")
}


fun main() {
    terribleWorkingStation()
    evenWorseWorkingStation()
    decentWorkingStation()
}