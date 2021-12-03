package day03

import java.io.File

private fun getDecimal(binary: List<Int>): Int {
    return binary.joinToString("").toInt(2)
}

private fun List<List<Int>>.mostCommon() = first().indices
    .map { index -> count { it[index] == 1 } }
    .map { if (it >= size - it) 1 else 0 }

private fun getRating(diagnostics: List<List<Int>>, index: Int, selector:(List<List<Int>>, Int)-> Int): List<Int> {
    if (diagnostics.size == 1) return diagnostics.first()
    return getRating(diagnostics.filter { it[index] == selector(diagnostics, index) }, index + 1, selector)
}

fun main() {
    val diagnostics = File("src/main/kotlin/day03/input.txt")
        .readLines()
        .map { it.toCharArray() }
        .map { it.map { c -> Character.getNumericValue(c) } }

    val gammaRate = diagnostics.mostCommon()
    val epsilonRate = gammaRate.map { it xor 1 }
    val oxygenGeneratorRating = getRating(diagnostics, 0) { lst, idx -> lst.mostCommon()[idx] }
    val cO2ScrubberRating = getRating(diagnostics, 0) { lst, idx -> lst.mostCommon()[idx] xor 1}

    println("Part One: " + getDecimal(gammaRate) * getDecimal(epsilonRate))
    println("Part Two: " + getDecimal(oxygenGeneratorRating) * getDecimal(cO2ScrubberRating))
}