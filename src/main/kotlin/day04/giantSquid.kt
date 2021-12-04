package day04

import java.io.File

val input = File("src/main/kotlin/day04/input.txt")
    .readText()
    .split("\n\n", "\r\n\r\n")

fun main() {
    val sequence = input.first().split(",").map { it.toInt() }
    val boards = input.drop(1).map(Board::parse)
    val completeBoards = mutableListOf<Pair<Board, Int>>()

    sequence.indices.map { index ->
        completeBoards.addAll(
            getWinningBoards(
                boards.filter { b -> completeBoards.none { it.first == b } },
                sequence.slice(0..index)
            )
        )
    }

    println("Part One: ${completeBoards.first().second}")
    println("Part Two: ${completeBoards.last().second}")
}

fun getWinningBoards(boards: List<Board>, sequence: List<Int>): List<Pair<Board, Int>> {
    return boards.filter { it.won(sequence) }.map { it to it.score(sequence) }
}

class Board(private val rows: List<List<Int>>) {
    fun score(sequence: List<Int>): Int {
        return rows.sumOf { row ->
            row.sumOf { if (sequence.contains(it)) 0 else it }
        } * sequence.last()
    }

    fun won(sequence: List<Int>): Boolean {
        return (containsWinningRow(sequence) || containsWinningColumn(sequence))
    }

    private fun containsWinningColumn(sequence: List<Int>): Boolean {
        return rows.first().indices.any { x -> rows.indices.all { y -> sequence.contains(rows[y][x]) } }
    }

    private fun containsWinningRow(sequence: List<Int>): Boolean {
        return rows.any { row -> row.all { sequence.contains(it) } }
    }

    companion object {
        fun parse(input: String): Board {
            return Board(input.split("\n")
                .map { it.trim() }
                .map { it.split("""\s+""".toRegex()) }
                .map { list -> list.map { it.toInt() } })
        }
    }
}