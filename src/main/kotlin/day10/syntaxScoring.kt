package day10

import java.io.File

fun main() {
    val parsedLines = File("src/main/kotlin/day10/input.txt")
        .readLines()
        .map { parse(it) }

    println("Part One: ${parsedLines.filter { !it.first }
        .sumOf { scoreError(it.second.single()) }}")

    println("Part Two: ${parsedLines.filter { it.first }
        .map { scoreAutocomplete(it.second) }
        .sorted()
        .median()}")
}

private fun parse(line: String): Pair<Boolean, String> {
    val closureIndex = line.indexOfFirst { pairs.values.contains(it) }
    return if (closureIndex == -1)
        true to getComplement(line)
    else if (pairs[line[closureIndex - 1]] == line[closureIndex])
        parse(
            line.slice(0 until closureIndex - 1) +
                    line.slice(closureIndex + 1 until line.length)
        )
    else false to "${line[closureIndex]}"
}

private val pairs = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

private fun getComplement(line: String): String {
    return line.reversed().map { c -> pairs[c] }.joinToString("")
}

private fun scoreAutocomplete(line: String): Long {
    val scores = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    return line.fold(0) { acc, char -> acc * 5 + scores.getOrDefault(char, 0) }
}

private fun scoreError(char: Char): Int = when (char) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    else -> 25137
}

private fun <E> List<E>.median(): E {
    return this[(this.size) / 2]
}
