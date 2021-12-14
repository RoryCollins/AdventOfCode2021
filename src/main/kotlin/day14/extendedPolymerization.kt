package day14

import java.io.File

fun main() {
    val input = File("src/main/kotlin/day14/input.txt")
        .readText()
        .split("\n\n", "\r\n\r\n")

    val polymerTemplate = input.first()
    val pairInsertionRules = input.last()
        .split("\n", "\r\n")
        .map { pair -> pair.split(" -> ") }
        .associate { it.first() to it.last() }

    val tenStepPolymerComposition = getPolymerComposition(pairInsertionRules, polymerTemplate, 10)
    val fortyStepPolymerComposition = getPolymerComposition(pairInsertionRules, polymerTemplate, 40)

    println("Part One: ${score(tenStepPolymerComposition)}")
    println("Part Two: ${score(fortyStepPolymerComposition)}")
}

fun getPolymerComposition(rules: Map<String, String>, polymerSequence: String, steps: Int): Map<Char, Long> {
    val initialMap = (polymerSequence
        .windowed(2)
        .groupBy { it }
        .map { it.key to it.value.size }
        .associate { it.first to it.second.toLong() })

    val result = (0 until steps).fold(initialMap){acc, _ -> insertElements(rules, acc) }

    val elementCount = mutableMapOf<Char, Long>()

    result.map {
        elementCount[it.key.first()] = elementCount.getOrDefault(it.key.first(), 0) + it.value
        elementCount[it.key.last()] = elementCount.getOrDefault(it.key.last(), 0) + it.value
    }

    return elementCount.map{it.key to (it.value+1)/2}.toMap()
}

fun insertElements(rules: Map<String, String>, current: Map<String, Long>): Map<String, Long>{
    val next = mutableMapOf<String, Long>()
    current.map {
        val first = it.key.first()+rules[it.key]!!
        val second = rules[it.key]!! + it.key.last()
        next[first] = next.getOrDefault(first, 0) + it.value
        next[second] = next.getOrDefault(second, 0) + it.value
    }
    return next
}

fun score(polymerComposition: Map<Char, Long>): Long{
    val elementCounts = polymerComposition.values.sorted()
    return elementCounts.last() - elementCounts.first()
}