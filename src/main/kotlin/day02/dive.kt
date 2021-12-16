package day02

import shared.Coordinate
import shared.CoordinateScore
import java.io.File

val instructions = File("src/main/kotlin/day02/input.txt")
    .readLines()
    .map { it.split(" ") }
    .map { it.first() to it.last().toInt() }


fun parseInstruction(instruction: Pair<String, Int>, current: CoordinateScore): CoordinateScore {
    val (direction, distance) = instruction
    return when (direction) {
        "forward" -> CoordinateScore(
            Coordinate(current.coordinate.x + distance, current.coordinate.y + (current.score * distance)),
            current.score
        )
        "up" -> CoordinateScore( current.coordinate, current.score - distance )
        "down" -> CoordinateScore( current.coordinate, current.score + distance )
        else -> throw IllegalArgumentException(direction)
    }
}

fun main() {
    val destination =
        instructions.fold(CoordinateScore(Coordinate(0, 0), 0)) { current, instruction -> parseInstruction(instruction, current) }
    println(destination.coordinate.x * destination.coordinate.y)
}
