package day15
import shared.Coordinate
import java.io.File

fun main() {
    val grid = Grid(File("src/main/kotlin/day15/input.txt")
        .readLines()
        .map{line -> line.map{Character.getNumericValue(it)}})

    println(grid.getRoute(Coordinate(0,0), 0))
}

class Grid(private val rows: List<List<Int>>){
    private var minimumScore = rows.first().sum() + (1 until rows.size).sumOf{rows[it].last()}
    private fun isOutOfBounds(coordinate: Coordinate): Boolean{
        return     (coordinate.X < 0)
                || (coordinate.Y < 0)
                || (coordinate.X >= rows.first().size)
                || (coordinate.Y >= rows.size)
    }

    fun getRoute(coordinate: Coordinate, cumulativeScore: Int): Int {
        val updatedTotal = if(coordinate == Coordinate(0,0)) 0
            else cumulativeScore+rows[coordinate.Y][coordinate.X]
        if (updatedTotal > minimumScore) return Int.MAX_VALUE
        if(coordinate == Coordinate(rows.first().size-1, rows.size-1)){
            minimumScore = updatedTotal
            return updatedTotal
        }
        return getNeighbours(coordinate).minOf { getRoute(it, updatedTotal) }
    }

    fun getNeighbours(coordinate: Coordinate): List<Coordinate> {
        return listOf(Coordinate(1,0), Coordinate(0,1))
            .map { coordinate.plus(it) }
            .filter { !isOutOfBounds(it) }
    }
}