package day15

import shared.Coordinate
import shared.CoordinateScore
import shared.Grid
import java.io.File
import java.util.*
import kotlin.Comparator

fun main() {
    val cave = Cave(File("src/main/kotlin/day15/input.txt")
        .readLines()
        .map { line -> line.map { Character.getNumericValue(it) } })

    println("Part One: ${cave.dijkstraMinimum()}")
    println("Part Two: ${cave.extend().dijkstraMinimum()}")
}


class Cave(private val rows: List<List<Int>>) : Grid(rows) {
    fun dijkstraMinimum(): Int {
        val priorityQueue = PriorityQueue(CoordinateScoreComparator())
        val visited = mutableSetOf<Coordinate>()
        val totalRisk = mutableMapOf<Coordinate, Int>()

        val origin = Coordinate(0, 0)
        val end = Coordinate(rows.first().size-1, rows.size-1)

        totalRisk[origin] = 0
        priorityQueue.add(CoordinateScore(origin, 0))

        while(priorityQueue.isNotEmpty()){
            val (coordinate, risk) = priorityQueue.remove()
            visited.add(coordinate)

            getNeighbours(coordinate).filter{!visited.contains(it)}.map{
                val newRiskLevel = risk + rows[it.y][it.x]
                if(newRiskLevel < totalRisk.getOrDefault(it, Int.MAX_VALUE)){
                    totalRisk[it] = newRiskLevel
                    priorityQueue.add(CoordinateScore(it, newRiskLevel))
                }
            }
        }
        return totalRisk.getOrDefault(end, Int.MAX_VALUE)
    }

    fun extend(): Cave {
        val newRows = (0..4).flatMap { yIndex ->
            rows.map { row ->
                List(5){row}.flatMapIndexed{xIndex, r -> r.map { if(it + yIndex + xIndex > 9) (it + yIndex + xIndex - 9) else (it + yIndex + xIndex) }}
            }
        }
        return Cave(newRows)
    }
}

private class CoordinateScoreComparator : Comparator<CoordinateScore>{
    override fun compare(first: CoordinateScore, second: CoordinateScore): Int {
        return first.score.compareTo(second.score)
    }
}
