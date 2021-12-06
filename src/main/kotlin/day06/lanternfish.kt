package day06

import java.io.File

val lanternfish = File("src/main/kotlin/day06/input.txt")
    .readText()
    .split(",")
    .map { it.toInt() }

private fun advanceDay(population: Map<Int, Long>): Map<Int, Long> {
    val pop = mutableMapOf<Int, Long>()
    for (fish in population) {
        if (fish.key == 0) {
            pop[8] = fish.value
            pop[6] = pop.getOrDefault(6, 0) + fish.value
        } else pop[fish.key - 1] = pop.getOrDefault(fish.key - 1, 0) + fish.value
    }
    return pop
}

private fun getPopulationSizeAfterDays(initialPopulation: Map<Int, Long>, days: Int) =
    (0 until days).fold(initialPopulation) { x, _ -> advanceDay(x) }.values.sum()

fun main() {
    val population = mutableMapOf<Int, Long>()
    for (x in lanternfish) {
        population[x] = population.getOrDefault(x, 0) + 1
    }
    println("Part One: ${getPopulationSizeAfterDays(population, 80)}")
    println("Part Two: ${getPopulationSizeAfterDays(population, 256)}")
}
