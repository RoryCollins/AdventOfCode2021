package day08

import java.io.File
import java.util.*
import kotlin.math.sign

val entries = File("src/main/kotlin/day08/input.txt")
    .readLines()
    .map { entry ->
        entry.split("|")
            .map {
                it.trim()
                    .split(" ")
            }
    }

fun decodeSignalInput(signals: List<String>): Map<SortedSet<Char>, Int> {
    val decodedSignals = mutableMapOf<SortedSet<Char>, Int>()

    val one = signals.single { it.length == 2 }
    val seven = signals.single { it.length == 3 }
    val four = signals.single { it.length == 4 }
    val eight = signals.single { it.length == 7 }
    val nine = signals.single { signal ->
        signal.length == 6 && four.all { signal.contains(it) } && seven.all { signal.contains(it) }
    }
    val six = signals.single { signal -> signal.length == 6 && one.any { !signal.contains(it) } }
    val zero = signals.single { it.length == 6 && it != six && it != nine }
    val topRight = eight.single { !six.contains(it) }
    val three = signals.single { signal -> signal.length == 5 && one.all { signal.contains(it) } }
    val five = signals.single { it.length == 5 && !it.contains(topRight) }
    val two = signals.single { it.length == 5 && it != three && it != five }

    decodedSignals[zero.toSortedSet()] = 0
    decodedSignals[one.toSortedSet()] = 1
    decodedSignals[two.toSortedSet()] = 2
    decodedSignals[three.toSortedSet()] = 3
    decodedSignals[four.toSortedSet()] = 4
    decodedSignals[five.toSortedSet()] = 5
    decodedSignals[six.toSortedSet()] = 6
    decodedSignals[seven.toSortedSet()] = 7
    decodedSignals[eight.toSortedSet()] = 8
    decodedSignals[nine.toSortedSet()] = 9

    return decodedSignals
}

fun decodeAndGenerateOutput(signalPattern: List<String>, output: List<String>): Int {
    val map = decodeSignalInput(signalPattern)
    return output.map {
        map[it.toSortedSet()]
    }.joinToString("").toInt()
}

fun main() {
    println(
        "Part One: ${
            entries.sumOf { (_, output) ->
                output.count { listOf(2, 3, 4, 7).contains(it.length) }
            }
        }"
    )
    println(
        "Part Two: ${
            entries.sumOf {
                decodeAndGenerateOutput(it.first(), it.last())
            }
        }"
    )
}