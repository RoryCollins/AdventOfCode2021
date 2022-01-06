package day19

import shared.Solution
import java.io.File
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val input = File("src/main/kotlin/day19/input.txt").readText()
    solve(input).print()
}

fun solve(input: String): Solution<Int, Int> {
    val scanners = input
        .split("\n\n", "\r\n\r\n")
        .map { block ->
            block.split("\n", "\r\n")
                .drop(1)
                .map { observation -> observation.split(",").map { it.toInt() } }
                .map{Coordinate3D(it[0], it[1], it[2])}
        }
    val distances = scanners.map{ getBeaconDistances(it) }
//    distances[0].map{ println(it)}
    val mutuals = distances[1].filter { distances[0].containsKey(it.key) }
    mutuals.map{ println(it)}
    println(mutuals.flatMap { listOf(it.value.first, it.value.second)}.size)
    return Solution(123, 0)
}


fun getBeaconDistances(beacons: List<Coordinate3D>): Map<Coordinate3D, Pair<Coordinate3D, Coordinate3D>> {
    val distances = mutableMapOf<Coordinate3D, Pair<Coordinate3D, Coordinate3D>>()

//    for (i in beacons.indices){
//        val source = beacons[i]
//        for (j in i+1 until beacons.size){
//            val destination = beacons[j]
//            distances[source.getDistance(destination)] = source to destination
//        }
//    }

//    for (source in beacons){
//        val destination = beacons.filter { it != source }.minOf{dest ->
//            val distance = source.getDistance(dest)
//            distance.x + distance.y + distance.z
//        }
//        ds.put()
//    }
    return distances
}



data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    fun getDistance(other: Coordinate3D): Coordinate3D {
        return Coordinate3D(abs(other.x - x), abs(other.y - y), abs(other.z - z))
    }
}

fun hypotenuse(lengths: List<Int>) = sqrt(lengths.sumOf { it.toDouble().pow(2.0) }.toDouble())

data class CoordinateDistance(val p0: Coordinate3D, val p1: Coordinate3D, val distance: Coordinate3D)