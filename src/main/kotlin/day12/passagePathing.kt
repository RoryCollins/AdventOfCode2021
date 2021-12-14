package day12

import java.io.File

fun main() {
    val connections = mutableMapOf<String, Set<String>>()
    File("src/main/kotlin/day12/input.txt")
        .readLines()
        .map { line -> line.split("-") }
        .map {
            connections[it.first()] = connections.getOrDefault(it.first(), emptySet()) + it.last()
            connections[it.last()] = connections.getOrDefault(it.last(), emptySet()) + it.first()
        }

    println(
        "Part One: ${
            createPaths(connections, { connectionMap, candidate, visitedLocations ->
                partOneAvailability(
                    connectionMap,
                    candidate,
                    visitedLocations
                )
            }, "start", listOf("start"))
        }"
    )

    println(
        "Part Two: ${
            createPaths(connections, { connectionMap, candidate, visitedLocations ->
                partTwoAvailability(
                    connectionMap,
                    candidate,
                    visitedLocations
                )
            }, "start", listOf("start"))
        }"
    )
}

fun createPaths(
    connectionMap: Map<String, Set<String>>,
    availabilityDeterminant: (Map<String, Set<String>>, String, List<String>) -> Boolean,
    currentLocation: String,
    currentPath: List<String>
): Int {
    if (currentLocation == "end") return 1
    val availableLocations = connectionMap[currentLocation]
        ?.filter { it != "start" }
        ?.filter { availabilityDeterminant(connectionMap, it, currentPath) }
        ?: error("$currentLocation not found in map!")
    if (availableLocations.isEmpty()) return 0
    return availableLocations.sumOf { createPaths(connectionMap, availabilityDeterminant, it, currentPath + it) }
}

fun partOneAvailability(
    connectionMap: Map<String, Set<String>>,
    candidate: String,
    visitedLocations: List<String>
): Boolean =
    !("""[a-z]*""".toRegex().matches(candidate) && visitedLocations.contains(candidate))

fun partTwoAvailability(
    connectionMap: Map<String, Set<String>>,
    candidate: String,
    visitedLocations: List<String>
): Boolean =
    !("""[a-z]*""".toRegex().matches(candidate) && if (ableToVisitSmallCaveTwice(
            connectionMap,
            visitedLocations
        )
    ) visitedLocations.count { it == candidate } == 2 else visitedLocations.contains(candidate))


fun ableToVisitSmallCaveTwice(connectionMap: Map<String, Set<String>>, currentPath: List<String>): Boolean {
    return currentPath.filter { """[a-z]*""".toRegex().matches(it) }.groupBy { it }.none { it.value.size == 2 }
}
