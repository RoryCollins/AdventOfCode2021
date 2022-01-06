package day19

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import shared.Solution
import java.io.File

internal class BeaconScannerTest {
    @Test
    fun solve(){
        val input = File("src/test/kotlin/day19/complex_input.txt").readText()
        val expected = Solution(79,0)
        assertEquals(expected, solve(input))
    }
}