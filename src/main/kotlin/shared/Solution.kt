package shared

data class Solution<T1, T2>(val part1: T1, val part2: T2) {
    fun print() {
        println("Part One: $part1")
        println("Part Two: $part2")
    }
}