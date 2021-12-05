package day05

import java.io.File
import kotlin.math.abs
import kotlin.math.sign

val ventLines = File("src/main/kotlin/day05/input.txt")
    .readLines()
    .map { line ->
        line.split("->")
            .map { it.trim() }
            .map { it.split(",") }
            .map { Point(it.first().toInt(), it.last().toInt()) }
    }
    .map { it.first() to it.last() }

data class Point(val X: Int, val Y: Int)

@OptIn(ExperimentalStdlibApi::class)
private fun getIntersections(lines: List<Pair<Point, Point>>): Int {
    return buildMap<Point, Int> {
        for ((start, end) in lines) {
            when {
                start.X == end.X -> {
                    for (y in minOf(start.Y, end.Y)..maxOf(start.Y, end.Y)) {
                        put(Point(start.X, y), getOrDefault(Point(start.X, y), 0) + 1)
                    }
                }
                start.Y == end.Y -> {
                    for (x in minOf(start.X, end.X)..maxOf(start.X, end.X)) {
                        put(Point(x, start.Y), getOrDefault(Point(x, start.Y), 0) + 1)
                    }
                }
                else -> {
                    val dx = (end.X - start.X).sign
                    val dy = (end.Y - start.Y).sign
                    (0..abs(end.X - start.X)).map {
                        val xy = Point(start.X + it * dx, start.Y + it * dy)
                        put(xy, getOrDefault(xy, 0) + 1)
                    }
                }
            }
        }
    }.values.count { it > 1 }
}

fun main() {
    println(getIntersections(ventLines))
}