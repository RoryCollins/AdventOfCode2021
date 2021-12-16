package day15

import shared.Coordinate
import java.io.File

fun main() {
    val grid = Grid(File("src/main/kotlin/day15/input_test.txt")
        .readLines()
        .map { line -> line.map { Character.getNumericValue(it) } })

    println("Part One: ${grid.getMinimumRisk()}")
    println("Part Two: ${grid.toBigGrid().getMinimumRisk()}")

    grid.toBigGrid().print()
    grid.dijkstraMinimum().print()
}


class Grid(private val rows: List<List<Int>>) {
    private val numberOfDiagonals = (rows.size - 1) * 2

    fun print() {
        rows.map { println(it.joinToString("")) }
    }

    fun getMinimumRisk(): Int {
        val x: MutableList<MutableList<Int>> = rows.map { it.toMutableList() }.toMutableList()
        (1..numberOfDiagonals).map { diagonal ->
            coordinatesAlongDiagonal(diagonal).map {
                val costs = listOf(Coordinate(-1, 0), Coordinate(0, -1))
                    .map { f -> it.plus(f) }
                    .filter { f -> !isOutOfBounds(f) }

                x[it.Y][it.X] = costs.minOf { c -> x[c.Y][c.X] } + x[it.Y][it.X]
            }
        }
        return x[rows.size - 1][rows.first().size - 1] - x[0][0]
    }

    fun dijkstraMinimum(): Grid {

        val x: MutableList<MutableList<Int>> = (0..rows.size).map { mutableListOf(rows.first().size, Int.MAX_VALUE) }.toMutableList()
        x[0][0] = 0
        return Grid(x)
    }

    fun getNeighbours(coordinate: Coordinate): List<Coordinate>{
        return listOf(
            Coordinate(0,1),
            Coordinate(0,-1),
            Coordinate(1,0),
            Coordinate(-1,0)).map{coordinate.plus(it)}
            .filter{!isOutOfBounds(it)}
    }

    private fun isOutOfBounds(f: Coordinate): Boolean {
        return f.X < 0 || f.Y < 0 || f.X >= rows.first().size || f.Y >= rows.size
    }

    private fun coordinatesAlongDiagonal(n: Int): List<Coordinate> {
        return (0..n).map { x -> Coordinate(x, n - x) }.filter { !isOutOfBounds(it) }
    }

    fun toBigGrid(): Grid {
        val newRows = (0..5).flatMap { yIndex ->
            rows.map { row ->
                List(5){row}.flatMapIndexed{xIndex, r -> r.map { if(it + yIndex + xIndex > 9) (it + yIndex + xIndex - 9) else (it + yIndex + xIndex) }}
            }
        }
        return Grid(newRows)
    }
}