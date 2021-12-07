package day07

import java.io.File
import kotlin.math.abs


fun getFuelExpense(positions: List<Int>, fuelCalculation: (Int, Int) -> Int, target: Int, previousTotal: Int): Int {
    val fuelExpense = positions.sumOf { fuelCalculation(target, it) }
    return if (fuelExpense > previousTotal) previousTotal
    else getFuelExpense(positions, fuelCalculation, target + 1, fuelExpense)
}

fun main() {
    val positions = File("src/main/kotlin/day07/input.txt")
        .readText()
        .split(",")
        .map { it.toInt() }

    val partOneFuelCalculation = {target:Int, current:Int -> abs(current - target)}
    val partTwoFuelCalculation = {target:Int, current:Int -> (1..abs(current - target)).sum()}

    println("Part One: ${getFuelExpense(positions, partOneFuelCalculation, positions.minOf { it }, Int.MAX_VALUE)}")
    println("Part Two: ${getFuelExpense(positions, partTwoFuelCalculation, positions.minOf { it }, Int.MAX_VALUE)}")

}