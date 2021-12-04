package day04

import java.io.File

val input = File("src/main/kotlin/day04/input.txt")
    .readText()
    .split("\n\n", "\r\n\r\n")

private fun parseBoard(board: String): List<List<Int>> {
    return board.split("\n")
        .map { it.trim() }
        .map { it.split("""\s+""".toRegex()) }
        .map { list -> list.map { it.toInt() } }
}

private fun winningBoard(board: List<List<Int>>, sequence: List<Int>): Boolean {
    return (containsWinningRow(board, sequence) || containsWinningColumn(board, sequence))
}

private fun containsWinningColumn(board: List<List<Int>>, sequence: List<Int>): Boolean {
    return board.first().indices.any { x -> board.indices.all { y -> sequence.contains(board[y][x]) } }
}

private fun containsWinningRow(board: List<List<Int>>, sequence: List<Int>): Boolean {
    return board.any { row -> row.all { sequence.contains(it) } }
}

private fun getScore(board: List<List<Int>>, sequence: List<Int>): Int {
    return board.sumOf { row ->
        row.sumOf { if (sequence.contains(it)) 0 else it }
    } * sequence.last()
}

fun main() {
    val sequence = input[0].split(",").map { it.toInt() }
    val boards = input.slice(1 until input.size).map { parseBoard(it) }

    val completeBoards = mutableListOf<Pair<List<List<Int>>, Int>>()

    sequence.indices.map { index ->
        completeBoards.addAll(
            getWinningBoards(
                boards.filter { b -> completeBoards.none { it.first == b } },
                sequence.slice(0..index)
            ))
    }
    println("Part One: ${completeBoards.first().second}")
    println("Part Two: ${completeBoards.last().second}")
}

fun getWinningBoards(boards: List<List<List<Int>>>, sequence: List<Int>): List<Pair<List<List<Int>>, Int>>{
    return boards.filter { winningBoard(it, sequence) }.map { it to getScore(it, sequence) }
}

//class Board(rows: List<List<Int>>) {
//    fun parse(input: String): Board {
//        return Board(input.split("\n")
//            .map { it.trim() }
//            .map { it.split("""\s+""".toRegex()) }
//            .map { list -> list.map { it.toInt() } })
//    }
//}