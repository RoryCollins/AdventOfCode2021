package day11

import java.io.File

fun main() {
    val octopusGrid = OctopusGrid(File("src/main/kotlin/day11/input.txt")
        .readLines()
        .map { line ->
            line.map {
                Character.getNumericValue(it)
            }
        }, 0)

    println("Part One: ${(0 until 100).fold(octopusGrid){acc, _ -> acc.getNext()}.cumulativeFlashCount}")
    println("Part Two: ${findAllFlashes(octopusGrid, 0)}")
}

fun findAllFlashes(octopusGrid: OctopusGrid, steps: Int): Int{
    return if(octopusGrid.allFlashes()) steps
    else findAllFlashes(octopusGrid.getNext(), steps+1)
}

class OctopusGrid(private val rows: List<List<Int>>, val cumulativeFlashCount: Int) {
    fun print() {
        rows.map{ println(it) }
    }

    fun getNext(): OctopusGrid {
        return incrementAllValues().incrementFlasherNeighbours()
    }

    private fun incrementAllValues(): OctopusGrid {
        return OctopusGrid(rows.map { line -> line.map { it + 1 } }, cumulativeFlashCount)
    }

    private fun incrementFlasherNeighbours(): OctopusGrid {
        val nextRows: MutableList<MutableList<Int>> = rows.map{it.toMutableList()}.toMutableList()
        var readyToFlash = flashingOctopodes()
        var flashes = cumulativeFlashCount
        do  {
            readyToFlash.map{
                flashes++
                nextRows[it.Y][it.X] = 0
                getNeighbours(it).filter{nextRows[it.Y][it.X] != 0}.map {n -> nextRows[n.Y][n.X] += 1 }
            }
            readyToFlash = OctopusGrid(nextRows, flashes).flashingOctopodes()
        }while(readyToFlash.isNotEmpty())
        return OctopusGrid(nextRows, flashes)
    }

    private fun flashingOctopodes(): List<Coordinate> {
        return rows.indices.flatMap { y -> rows.first().indices.filter{x -> rows[y][x] >= 10}.map{x -> Coordinate(x, y)} }
    }

    private fun getNeighbours(coordinate: Coordinate): List<Coordinate> {
        return listOf(
            coordinate.up(),
            coordinate.upLeft(),
            coordinate.upRight(),
            coordinate.down(),
            coordinate.downLeft(),
            coordinate.downRight(),
            coordinate.left(),
            coordinate.right()
        ).filter { !isOutOfBounds(it) }
    }

    private fun isOutOfBounds(coordinate: Coordinate): Boolean {
        return coordinate.X < 0 || coordinate.Y < 0 || coordinate.X >= rows.first().size || coordinate.Y >= rows.size
    }

    fun allFlashes(): Boolean = rows.sumOf { it.sum() } == 0
}

data class Coordinate(val X: Int, val Y: Int) {
    fun up(): Coordinate = Coordinate(X, Y - 1)
    fun upLeft(): Coordinate = Coordinate(X - 1, Y - 1)
    fun upRight(): Coordinate = Coordinate(X + 1, Y - 1)
    fun down(): Coordinate = Coordinate(X, Y + 1)
    fun downLeft(): Coordinate = Coordinate(X - 1, Y + 1)
    fun downRight(): Coordinate = Coordinate(X + 1, Y + 1)
    fun right(): Coordinate = Coordinate(X + 1, Y)
    fun left(): Coordinate = Coordinate(X - 1, Y)
}
