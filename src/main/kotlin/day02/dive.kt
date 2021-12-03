package day02

import java.io.File

val instructions = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }
    .map { it.first() to it.last().toInt() }

data class Coordinate(val X: Int, val Y: Int)
data class State(val Coordinate: Coordinate, val Aim: Int)

fun parseInstruction(instruction: Pair<String, Int>, current: State): State {
    val (direction, distance) = instruction
    return when (direction) {
        "forward" -> State(
            Coordinate(current.Coordinate.X + distance, current.Coordinate.Y + (current.Aim * distance)),
            current.Aim
        )
        "up" -> State( current.Coordinate, current.Aim - distance )
        "down" -> State( current.Coordinate, current.Aim + distance )
        else -> throw IllegalArgumentException(direction)
    }
}

fun main() {
    val destination =
        instructions.fold(State(Coordinate(0, 0), 0)) { current, instruction -> parseInstruction(instruction, current) }
    println(destination.Coordinate.X * destination.Coordinate.Y)
}
