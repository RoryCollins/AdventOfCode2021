package day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Solution

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
        val lines = listOf(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        )

        val solution = solve(lines)
        val expected = Solution(4140, 3993)
        assertEquals(expected, solution)
    }

}