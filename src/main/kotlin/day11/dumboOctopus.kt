package day11

import shared.Coordinate
import shared.Grid
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

class OctopusGrid(private val rows: List<List<Int>>, val cumulativeFlashCount: Int) : Grid(rows) {
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
                nextRows[it.y][it.x] = 0
                getNeighbours(it).filter{nextRows[it.y][it.x] != 0}.map { n -> nextRows[n.y][n.x] += 1 }
            }
            readyToFlash = OctopusGrid(nextRows, flashes).flashingOctopodes()
        }while(readyToFlash.isNotEmpty())
        return OctopusGrid(nextRows, flashes)
    }

    private fun flashingOctopodes(): List<Coordinate> {
        return rows.indices.flatMap { y -> rows.first().indices.filter{x -> rows[y][x] >= 10}.map{x -> Coordinate(x, y)} }
    }

    override fun getNeighbours(coordinate: Coordinate): List<Coordinate> {
        return getAdjacentNeighbours(coordinate) + getDiagonalNeighbours(coordinate)
    }

    fun allFlashes(): Boolean = rows.sumOf { it.sum() } == 0
}
