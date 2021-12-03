package day02

import java.io.File

val instructions = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }
    .map { it.first() to it.last().toInt() }

data class State(val X: Int, val Y: Int, val Aim: Int)

fun parseInstruction(instruction: Pair<String, Int>, current: State): State {
    val (direction, distance) = instruction
    return when (direction) {
        "forward" -> State(current.X + distance, current.Y + (current.Aim * distance), current.Aim)
        "up" -> State(current.X, current.Y, current.Aim - distance)
        "down" -> State(current.X, current.Y, current.Aim + distance)
        else -> throw IllegalArgumentException(direction)
    }
}

fun main() {
    val destination =
        instructions.fold(State(0, 0, 0)) { current, instruction -> parseInstruction(instruction, current) }
    println(destination.X * destination.Y)
}
