package day09

import shared.Coordinate
import shared.Grid
import java.io.File

fun main() {
    val rows = File("src/main/kotlin/day09/input.txt")
        .readLines()
        .map { line -> line.map { Character.getNumericValue(it) } }

    val heightMap = HeightMap(rows)

    println("Part One: ${heightMap.lowPointRisk}")
    println("Part Two: ${heightMap.basinSizesDescending
            .take(3)
            .fold(1) { a, b -> a * b }
    }")
}

class HeightMap(private val rows: List<List<Int>>) : Grid(rows) {
    private val lowPoints = rows.indices.flatMap { y ->
        rows.first().indices
            .filter { x -> getNeighbours(Coordinate(x, y)).all { rows[it.y][it.x] > rows[y][x] } }
            .map { x -> Pair(Coordinate(x, y), rows[y][x] + 1) }
    }

    val lowPointRisk: Int = lowPoints.sumOf { (_, risk) -> risk }

    val basinSizesDescending: List<Int> = lowPoints
        .map{(coordinate, _) -> getBasin(setOf(coordinate)).size}
        .sortedDescending()

    private fun getBasin(current: Set<Coordinate>): Set<Coordinate> {
        val next = current + current.flatMap { getNeighbours(it) }.filter { rows[it.y][it.x] != 9 }
        return if (next.size == current.size) current
        else getBasin(next)
    }
}