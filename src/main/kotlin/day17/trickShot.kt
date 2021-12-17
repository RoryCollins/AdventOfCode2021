package day17

import shared.Coordinate
import shared.Solution
import java.io.File
import kotlin.math.sqrt

fun main() {
    val input = File("src/main/kotlin/day17/input.txt").readText()
    solve(input).print()
}

fun solve(input: String): Solution<Int, Int> {
    val targetArea = getTargetArea(input)

    val lowerX = sqrt(targetArea.first.x.toDouble()).toInt()
    val upperX = targetArea.second.x

    val lowerY = targetArea.first.y
    val upperY = (targetArea.first.y + 1) * -1

    val part1 = (upperY * (upperY+1)) / 2
    val part2 = (lowerX .. upperX).sumOf{ x ->
        (lowerY..upperY).count{ y ->
            testTrajectory(Coordinate(x, y), targetArea)
        }
    }
    return Solution(part1, part2)
}

private fun getTargetArea(input: String): Pair<Coordinate, Coordinate> {
    val (x0, x1, y0, y1) = """target area: x=(-?\d*)..(-?\d*), y=(-?\d*)..(-?\d*)""".toRegex().find(input)?.destructured ?: error("bad regex")
    val (a,b,c,d) = listOf(x0, x1, y0, y1).map { it.toInt() }

    return Coordinate(x0.toInt(), y0.toInt()) to Coordinate(x1.toInt(), y1.toInt())
}

private fun testTrajectory(initialTrajectory: Coordinate, targetArea: Pair<Coordinate, Coordinate>): Boolean{
    var trajectory = initialTrajectory
    var coordinate = initialTrajectory
    while(!coordinate.isBeyondArea(targetArea) && !coordinate.isInArea(targetArea)){
        trajectory = trajectory.increment()
        coordinate = coordinate.plus(trajectory)
    }
    return coordinate.isInArea(targetArea)
}

private fun Coordinate.isInArea(targetArea: Pair<Coordinate, Coordinate>): Boolean{
    return (minOf(targetArea.first.x, targetArea.second.x)..maxOf(targetArea.first.x, targetArea.second.x)).contains(x) &&
            (minOf(targetArea.first.y, targetArea.second.y)..maxOf(targetArea.first.y, targetArea.second.y)).contains(y)
}

private fun Coordinate.isBeyondArea(targetArea: Pair<Coordinate, Coordinate>): Boolean{
    return x > maxOf(targetArea.first.x, targetArea.second.x) || y < minOf(targetArea.first.y, targetArea.second.y)
}

private fun Coordinate.increment(): Coordinate {
    return Coordinate(
        when {
            x > 0 -> x - 1
            x < 0 -> x + 1
            else -> 0
        }, y - 1
    )
}