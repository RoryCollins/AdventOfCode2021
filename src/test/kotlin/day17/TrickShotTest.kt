package day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Solution
import java.io.File

internal class TrickShotTest {

    @Test
    fun solve() {
        val testInput = File("test/main/kotlin/day17/simple_input.txt").readText()
        assertEquals(Solution(45, 112), solve(testInput))
    }
}