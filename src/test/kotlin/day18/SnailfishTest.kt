package day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SnailfishTest {
    private val one = SnailfishLiteral(1)
    private val two = SnailfishLiteral(2)
    private val three = SnailfishLiteral(3)
    private val four = SnailfishLiteral(4)
    private val five = SnailfishLiteral(5)
    private val six = SnailfishLiteral(6)
    private val seven = SnailfishLiteral(7)
    private val eight = SnailfishLiteral(8)
    private val nine = SnailfishLiteral(9)
    private val zero = SnailfishLiteral(0)
    private val nineOne = SnailfishPair(SnailfishLiteral(9), SnailfishLiteral(1))
    private val oneNine = SnailfishPair(SnailfishLiteral(1), SnailfishLiteral(9))

    @Test
    fun simpleMagnitude() {
        assertEquals(29, nineOne.magnitude())
        assertEquals(21, oneNine.magnitude())
    }

    @Test
    fun compoundMagnitude() {
        val compound = SnailfishPair(nineOne, oneNine)
        assertEquals(129, compound.magnitude())

        val eightSeven = SnailfishPair(eight, seven)
        val eightSix = SnailfishPair(eight, six)
        val sixSix = SnailfishPair(six, six)
        val sevenSeven = SnailfishPair(seven, seven)
        val zeroSeven = SnailfishPair(zero, seven)
        val magnitude = SnailfishPair(
            SnailfishPair(
                SnailfishPair(eightSeven, sevenSeven),
                SnailfishPair(eightSix, sevenSeven)
            ),
            SnailfishPair(
                SnailfishPair(zeroSeven, sixSix),
                eightSeven
            )
        ).magnitude()
        assertEquals(3488, magnitude)
    }

    @Test
    fun simpleAddition() {
        assertEquals(oneNine, one.plus(nine))
    }

    @Test
    fun additionWithExplosion() {
        val actual = SnailfishPair(one, one)
            .plus(SnailfishPair(two, two))
            .plus(SnailfishPair(three, three))
            .plus(SnailfishPair(four, four))
            .plus(SnailfishPair(five, five))

        val expected = SnailfishPair(
            SnailfishPair(
                SnailfishPair(three, zero),
                SnailfishPair(five, three)),
            SnailfishPair(
                SnailfishPair(four, four),
                SnailfishPair(five, five)))

        assertEquals(expected, actual)
    }

    @Test
    fun explodeReturnsCorrectCarryOver(){
        val actual = SnailfishPair(nineOne, one,3).explode()
        val expected = SnailfishPair(zero, two, 3) to CarryOver(9, 0)
        assertEquals(expected, actual)
    }
}