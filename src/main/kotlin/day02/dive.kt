package day02

import java.io.File

val instructions = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }
    .map { it.first() to it.last().toInt() }

data class Point(val X: Int, val Y: Int, val Aim: Int)

fun parseInstruction(instruction: Pair<String, Int>, current: Point): Point {
    val (direction, distance) = instruction
    return when (direction) {
        "forward" -> Point(current.X + distance, current.Y + (current.Aim * distance), current.Aim)
        "up" -> Point(current.X, current.Y, current.Aim - distance)
        "down" -> Point(current.X, current.Y, current.Aim + distance)
        else -> throw IllegalArgumentException(direction)
    }
}

fun main() {
    val destination =
        instructions.fold(Point(0, 0, 0)) { current, instruction -> parseInstruction(instruction, current) }
    println(destination.X * destination.Y)
}
