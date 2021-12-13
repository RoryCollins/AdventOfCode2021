package day13

import shared.Coordinate
import java.io.File

fun main() {
    val instructions = File("src/main/kotlin/day13/input.txt")
        .readText()
        .split("\r\n\r\n")
        .map { it.split("\r\n") }

    val coordinates = instructions.first()
        .map { it.split(",") }
        .map { Coordinate(it.first().toInt(), it.last().toInt()) }
        .toSet()

    val foldInstructions = instructions.last()
        .map {
            val (axis, value) = """fold along (\w)=(\d*)""".toRegex().find(it)?.destructured ?: error("Oh no")
            axis to value
        }

    println("Part One: ${fold(coordinates, foldInstructions[0].first, foldInstructions[0].second.toInt()).size}")
    println("Part Two:")
    val newCoordinates = foldInstructions.fold(coordinates) { nextCoordinates, instruction ->
        fold(
            nextCoordinates,
            instruction.first,
            instruction.second.toInt()
        )
    }
    (0..newCoordinates.maxOf { it.Y }).map { y ->
        (0..newCoordinates.maxOf { it.X }).map { x ->
            print(if (newCoordinates.contains(Coordinate(x, y))) "█" else " ")
        }
        print("\n")
    }
}

fun fold(coordinates: Set<Coordinate>, axis: String, value: Int): Set<Coordinate> {
    return if (axis == "y")
        coordinates.map { if (it.Y <= value) it else Coordinate(it.X, value - (it.Y - value)) }.toSet()
    else coordinates.map { if (it.X <= value) it else Coordinate(value - (it.X - value), it.Y) }.toSet()
}
