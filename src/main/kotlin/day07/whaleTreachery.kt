package day07

import java.io.File
import kotlin.math.abs

val positions = File("src/main/kotlin/day07/input_test.txt")
    .readText()
    .split(",")
    .map { it.toInt() }

fun main() {
    val partOne = (positions.minOf { it }..positions.maxOf { it }).map { target ->
        target to positions.sumOf { abs(it - target) }
    }
    val partTwo = (positions.minOf { it }..positions.maxOf { it }).map { target ->
        target to positions.sumOf { (1..abs(it - target)).sum() }
    }

    println("Part One: ${partOne.minOf { it.second }}")
    println("Part Two: ${partTwo.minOf { it.second }}")
}