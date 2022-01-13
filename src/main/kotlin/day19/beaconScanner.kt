package day19

import shared.Solution
import java.io.File

fun main() {
    val input = File("src/main/kotlin/day19/input.txt").readText()
    val scanners = getScannersFromInput(input)
    solve(scanners).print()
}

fun solve(scanners: List<List<Coordinate3D>>): Solution<Int, Int> {
    return Solution(getAllSignals(scanners, 12).count(), 0)
}

fun getScannersFromInput(input: String): List<List<Coordinate3D>> {
    val scanners = input
        .split("\n\n", "\r\n\r\n")
        .map { block ->
            block.split("\n", "\r\n")
                .drop(1)
                .map { observation -> observation.split(",").map { it.toInt() } }
                .map { Coordinate3D(it[0], it[1], it[2]) }
        }
    return scanners
}


fun getAllSignals(scanners: List<List<Coordinate3D>>, sensitivity: Int): Set<Coordinate3D> {
    val distances = scanners.map { getBeaconDistances(it) }
    val scannerInformation = mutableMapOf<Int, ScannerInformation>()
    val explored = mutableListOf<Int>()
    scannerInformation[0] = ScannerInformation(Coordinate3D(0,0,0), 0)
    val queue = arrayListOf(0)
    val alignedScanners = mutableListOf<Scanner>(
        Scanner(
            ScannerInformation(Coordinate3D(0,0,0), 0),
            scanners[0]
        ))

    while(queue.isNotEmpty()){
        val i = queue.removeFirst()
        explored.add(i)
        val overlap = distances
            .filterIndexed { index, _ -> !explored.contains(index) }
            .filter { mutualCoordinates(it, distances[i]).first.count() >= sensitivity }

        for(o in overlap){
            val scannerIndex = distances.indexOf(o)
            queue.add(scannerIndex)
            println("$i overlaps with $scannerIndex")
            val relativeScannerInformation = getLocationByOverlap(mutualCoordinates(distances[i], o))

            alignedScanners.add(Scanner(relativeScannerInformation, scanners[scannerIndex]))
//                scanners[scannerIndex].map{ it.rotations()}[relativeScannerInformation.orientationIndex].map{it.minus(relativeScannerInformation.location)}.toList())
//            scannerInformation[distances.indexOf(o)] = scannerInformation[i]?.location?.plus().location) ?: error("uh oh")
        }
    }

//    return scanners[0].count() + distances.indices.sumOf { i ->
//        val overlap = distances
//            .filterIndexed { index, _ -> !explored.contains(index) && index != i }
//            .filter { getNumberOfMutualCoordinates(it, distances[i]).count() >= sensitivity }
//        overlap.forEach { explored.add(distances.indexOf(it)) }
//        overlap.sumOf { scanners[distances.indexOf(it)].count() - getNumberOfMutualCoordinates(it, distances[i]).count() }
//    }
}

fun mutualCoordinates(
    distances0: Map<Int, Pair<Coordinate3D, Coordinate3D>>,
    distances1: Map<Int, Pair<Coordinate3D, Coordinate3D>>
): Pair<Set<Coordinate3D>, Set<Coordinate3D>>{
    val mutuals = distances1.keys.intersect(distances0.keys)

    val x = mutuals.flatMap{ distances0[it]!!.toList()}.toSet()
    val y = mutuals.flatMap{ distances1[it]!!.toList()}.toSet()

    return x to y
}

fun getBeaconDistances(beacons: List<Coordinate3D>): Map<Int, Pair<Coordinate3D, Coordinate3D>> {
    val distances = mutableMapOf<Int, Pair<Coordinate3D, Coordinate3D>>()

    for (i in beacons.indices) {
        val source = beacons[i]
        for (j in i + 1 until beacons.size) {
            val destination = beacons[j]
            distances[source.minus(destination).fingerPrint()] = source to destination
        }
    }
    return distances
}

fun getLocationByOverlap(scanners: Pair<Set<Coordinate3D>, Set<Coordinate3D>>): ScannerInformation{
    val allRotations = scanners.second.map { it.rotations() }
    val distances = mutableSetOf<Coordinate3D>()
    for ( rotationIndex in allRotations[0].indices){
        for ( origin in scanners.first){
            val distance = origin.minus(allRotations[0].elementAt(rotationIndex))
            distances.add(distance)
            if (allRotations.all { scanners.first.contains(it.elementAt(rotationIndex).plus(distance)) }){
                return ScannerInformation(distance, rotationIndex)
            }
        }
    }
    error("Nothing found")
}

data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    fun minus(other: Coordinate3D): Coordinate3D {
        return Coordinate3D((x - other.x), (y - other.y), (z-other.z))
    }

    fun plus(other: Coordinate3D): Coordinate3D {
        return Coordinate3D(other.x + x, other.y + y, other.z + z)
    }

    fun fingerPrint(): Int = listOf(x, y, z).sumOf { it * it }

    fun rotations(): Set<Coordinate3D> = rotateAboutX().flatMap { it.rotateAboutY() }.flatMap { it.rotateAboutZ() }.toSet()

    private fun rotateAboutZ(): Set<Coordinate3D> {
        return setOf(
            this,
            Coordinate3D(-y, x, z),
            Coordinate3D(-x, -y, z),
            Coordinate3D(y, -x, z)
        )
    }

    private fun rotateAboutY(): Set<Coordinate3D> {
        return setOf(
            this,
            Coordinate3D(-z, y, x),
            Coordinate3D(-x, y, -z),
            Coordinate3D(z, y, -x)
        )
    }

    private fun rotateAboutX(): Set<Coordinate3D> {
        return setOf(
            this,
            Coordinate3D(x, -z, y),
            Coordinate3D(x, -y, -z),
            Coordinate3D(x, z, -y),
        )
    }
}

data class ScannerInformation(val location: Coordinate3D, val orientationIndex: Int)

data class Scanner(val information: ScannerInformation, val signals: List<Coordinate3D>)