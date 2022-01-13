package day19

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import shared.Solution
import java.io.File

internal class BeaconScannerTest {
    @Test
    fun solve(){
        val input = File("src/test/kotlin/day19/complex_input.txt").readText()
        val scanners = getScannersFromInput(input)
        val expected = Solution(79,0)
        assertEquals(expected, solve(scanners))
    }

    @Test
    fun solveSmallerSet(){
        val input = File("src/test/kotlin/day19/complex_input.txt").readText()
        val scanners = getScannersFromInput(input).take(2)
        val expected = Solution(38,0)
        assertEquals(expected, solve(scanners))
    }

    @Test
    fun countWithOverlappingSignals() {
        val input = File("src/test/kotlin/day19/simple_input.txt").readText()
        val scanners = getScannersFromInput(input)
        assertEquals(3, getAllSignals(scanners, 3).count())
    }

    @Test
    fun determineRotationEquality(){
        val coordinate = Coordinate3D(1,2,3)
        assertEquals(24, coordinate.rotations().count())
    }

    @Test
    fun determineLocationByOverlap() {
        val input = File("src/test/kotlin/day19/complex_input.txt").readText()
        val scanners = getScannersFromInput(input).take(2)
        val distances = scanners.map { getBeaconDistances(it) }
        val mutualCoordinates = mutualCoordinates(distances[0], distances[1])
        val expected = ScannerInformation(Coordinate3D(68,-1246,-43), 8)
        val actual = getLocationByOverlap(mutualCoordinates)
        assertEquals(expected, actual)
    }
}