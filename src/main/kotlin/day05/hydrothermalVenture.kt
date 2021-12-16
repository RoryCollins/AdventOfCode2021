package day05

import shared.Coordinate
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

val ventLines = File("src/main/kotlin/day05/input.txt")
    .readLines()
    .map { line ->
        line.split("->")
            .map { it.trim() }
            .map { it.split(",") }
            .map { Coordinate(it.first().toInt(), it.last().toInt()) }
    }
    .map { it.first() to it.last() }

private fun getIntersections(lines: List<Pair<Coordinate, Coordinate>>): Int {
    val result = mutableMapOf<Coordinate, Int>()

    for ((start, end) in lines) {
        when {
            start.x == end.x -> {
                for (y in minOf(start.y, end.y)..maxOf(start.y, end.y)) {
                    result[Coordinate(start.x, y)] = result.getOrDefault(Coordinate(start.x, y), 0) + 1
                }
            }
            start.y == end.y -> {
                for (x in minOf(start.x, end.x)..maxOf(start.x, end.x)) {
                    result[Coordinate(x, start.y)] = result.getOrDefault(Coordinate(x, start.y), 0) + 1
                }
            }
            else -> {
                val dx = (end.x - start.x).sign
                val dy = (end.y - start.y).sign
                (0..abs(end.x - start.x)).map {
                    val xy = Coordinate(start.x + it * dx, start.y + it * dy)
                    result[xy] = result.getOrDefault(xy, 0) + 1
                }
            }
        }
    }

    return result.values.count { it > 1 }
}

fun main() {
    println(getIntersections(ventLines))
}