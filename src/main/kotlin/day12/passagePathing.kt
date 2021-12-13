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

    val result = createPaths(connections, "start", listOf("start"))

    println(result)
}

fun ableToVisitSmallCaveTwice(connectionMap: Map<String, Set<String>>, currentPath: List<String>): Boolean {
    return currentPath.filter { """[a-z]*""".toRegex().matches(it) }.groupBy { it }.none { it.value.size == 2 }
}

//
fun createPaths(connectionMap: Map<String, Set<String>>, currentLocation: String, currentPath: List<String>): Int {
    if (currentLocation == "end") return 1
    val availableLocations = connectionMap[currentLocation]!!
        .filter { it != "start" }
        .filter { candidate ->
            !("""[a-z]*""".toRegex().matches(candidate) && if (ableToVisitSmallCaveTwice(
                    connectionMap,
                    currentPath
                )
            ) currentPath.count { it == candidate } == 2 else currentPath.contains(candidate))
        }
    if (availableLocations.isEmpty()) return 0
    return availableLocations.sumOf { createPaths(connectionMap, it, currentPath + it) }
}