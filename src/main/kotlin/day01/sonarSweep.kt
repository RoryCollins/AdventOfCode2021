package day01

import java.io.File

fun main(args: Array<String>) {
    val numbers = File("src/main/kotlin/day01/input.txt").readLines().map { it.toInt() }
    println(partOne(numbers))
    println(partTwo(numbers))
}

private fun partOne(numbers: List<Int>): Int {
    return numbers.windowed(2).count{it.last() > it.first()}
}

private fun partTwo(numbers: List<Int>): Int {
    return numbers.windowed(4).count{it.last() > it.first()}
}