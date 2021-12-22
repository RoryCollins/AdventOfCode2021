package day18

import shared.Solution
import java.io.File
import kotlin.reflect.typeOf

fun main() {
    val input = File("src/main/kotlin/day18/input.txt").readLines()
    solve(input).print()
}

fun solve(input: List<String>): Solution<Int, Int> {
    return Solution(0, 0)
}


abstract class SnailfishNumber() {
    abstract fun magnitude(): Int
    abstract fun nest(): SnailfishNumber
    fun plus(other: SnailfishNumber): SnailfishNumber {
        return SnailfishPair(this.nest(), other.nest())
    }

    abstract fun plus(carryOver: CarryOver): SnailfishNumber
    abstract fun plus(number: Int): SnailfishNumber
    abstract fun explode(): Pair<SnailfishPair, CarryOver>
    abstract fun isPair(): Boolean
}

data class SnailfishLiteral(private value: Int) : SnailfishNumber() {
    override fun magnitude(): Int = value
    override fun nest(): SnailfishNumber = this
    override fun plus(carryOver: CarryOver): SnailfishNumber {
        return SnailfishLiteral(value + carryOver.left + carryOver.right)
    }

    override fun plus(number: Int): SnailfishNumber {
        return SnailfishLiteral(value + number)
    }

    override fun explode(): Pair<SnailfishPair, CarryOver> = error("this shouldn't happen")
    override fun isPair(): Boolean = false
}

data class CarryOver(val left:Int, val right:Int)

data class SnailfishPair(
    val left: SnailfishNumber,
    val right: SnailfishNumber,
    private val nestLevel: Int = 0
) : SnailfishNumber() {
    override fun magnitude(): Int {
        return left.magnitude() * 3 + right.magnitude() * 2
    }

    override fun nest() = SnailfishPair(left.nest(), right.nest(), nestLevel + 1)
    override fun plus(carryOver: CarryOver): SnailfishNumber {
        return SnailfishPair(this.left.plus(carryOver.left), this.right.plus(carryOver.right))
    }

    override fun plus(number: Int): SnailfishNumber {
        TODO("Not yet implemented")
    }

    override fun explode(): Pair<SnailfishPair, CarryOver>{
        if(nestLevel==3){
            if(left.isPair()){
                val l = left as SnailfishPair
                val carryOver = CarryOver(l.left.value, l.right)
                right.plus(CarryOver(0, content.second.right))
                return SnailfishPair(SnailfishLiteral(0), right, nestLevel) to CarryOver(0, content.second.right)
            }
            else if (right.isPair()) {
                val content = right.explode()
                left.plus(CarryOver(content.second.left, 0))
                return SnailfishPair(SnailfishLiteral(0), right, nestLevel) to CarryOver(0, content.second.right)
            }
        }
        return this to CarryOver(0,0)
    }

    override fun isPair(): Boolean = true
}