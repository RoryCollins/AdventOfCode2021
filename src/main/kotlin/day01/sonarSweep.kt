package day01

import java.io.File

fun main(args: Array<String>) {
    val numbers = File("src/main/kotlin/day01/input.txt")
        .readLines()
        .map { it.toInt() }
    println("Part One: ${numbers.windowed(2).count { it.last() > it.first() }}")
    println("Part Two: ${numbers.windowed(4).count { it.last() > it.first() }}")
}
