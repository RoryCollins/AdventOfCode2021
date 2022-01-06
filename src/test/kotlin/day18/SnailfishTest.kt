package day18

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import shared.Solution
import java.io.File

internal class SnailfishTest {
    @Test
    fun addSnailfishNumbers() {
        val oneOne = "[1,1]"
        val twoTwo = "[2,2]"
        val expected = "[[1,1],[2,2]]"
        assertEquals(expected, oneOne.add(twoTwo))
    }

    @Test
    fun explodeMiddleSnailfishNumber() {
        val test = explode("[[[2,[[1,1],2]]]]")
        val expected = "[[[3,[0,3]]]]"
        assertEquals(expected, test)
    }

    @Test
    fun explodeLeftSnailFishNumber() {
        val test = explode("[[[[[1,1],2]]]]")
        val expected = "[[[[0,3]]]]"
        assertEquals(expected, test)
    }

    @Test
    fun explodeRightSnailfishNumber() {
        val test = explode("[[[[2,[1,1]]]]]")
        val expected = "[[[[3,0]]]]"
        assertEquals(expected, test)
    }

    @Test
    fun onlyExplodeSinglePairPerPass() {
        val test = explode("[[[[[1,1],[2,2]]]]]")
        val expected = "[[[[0,[3,2]]]]]"
        assertEquals(expected, test)
    }

    @Test
    fun explodeDoesNothingForSimpleNumber() {
        val test = "[1,1]"
        val expected = ExplosionData(false, 0, test)
        assertEquals(expected, explode(test, 0, 0, ""))
    }

    @Test
    fun explodeWhenAddNumbers() {
        val actual = "[1,1]"
            .add("[2,2]")
            .add("[3,3]")
            .add("[4,4]")
            .add("[5,5]")

        val expected = "[[[[3,0],[5,3]],[4,4]],[5,5]]"
        assertEquals(expected, actual)
    }

    @Test
    fun splitNumber() {
        val test = split("[11,1]")
        val expected = "[[5,6],1]"
    }

    @Test
    fun complexAddition() {
//        val actual = "[[[[4,3],4],4],[7,[[8,4],9]]]".add("[1,1]")
//        val expected = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
//        assertEquals(expected, actual)

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]".add("[[[[4,2],2],6],[8,7]]"))
    }

    @Test
    fun getMagnitude() {
        assertEquals(29, magnitude("[9,1]"))
        assertEquals(129, magnitude("[[9,1],[1,9]]"))
        assertEquals(3488, magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
    }

    @Test
    fun solve() {
        val lines = File("src/test/kotlin/day18/input_test.txt").readLines()

        val solution = solve(lines)
        val expected = Solution(4140, 3993)
        assertEquals(expected, solution)
    }

}