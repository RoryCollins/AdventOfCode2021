package day09

import java.io.File

data class Coordinate(val X: Int, val Y: Int) {
    fun up(): Coordinate = Coordinate(X, Y - 1)
    fun down(): Coordinate = Coordinate(X, Y + 1)
    fun right(): Coordinate = Coordinate(X + 1, Y)
    fun left(): Coordinate = Coordinate(X - 1, Y)
}

class HeightMap(private val rows: List<List<Int>>) {
    private val lowPoints = rows.indices.flatMap { y ->
        rows.first().indices
            .filter { x -> getNeighbours(Coordinate(x, y)).all { rows[it.Y][it.X] > rows[y][x] } }
            .map { x -> Pair(Coordinate(x, y), rows[y][x] + 1) }
    }

    val lowPointRisk: Int = lowPoints.sumOf { (_, risk) -> risk }

    val basinSizesDescending: List<Int> = lowPoints
        .map{(coordinate, _) -> getBasin(setOf(coordinate)).size}
        .sortedDescending()

    private fun getBasin(current: Set<Coordinate>): Set<Coordinate> {
        val next = current + current.flatMap { getNeighbours(it) }.filter { rows[it.Y][it.X] != 9 }
        return if (next.size == current.size) current
        else getBasin(next)
    }

    private fun getNeighbours(coordinate: Coordinate): List<Coordinate> {
        return listOf(
            coordinate.up(),
            coordinate.down(),
            coordinate.left(),
            coordinate.right()
        ).filter { !isOutOfBounds(it) }
    }

    private fun isOutOfBounds(coordinate: Coordinate): Boolean {
        return coordinate.X < 0 || coordinate.Y < 0 || coordinate.X >= rows.first().size || coordinate.Y >= rows.size
    }
}


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
