package day18

import shared.Solution
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val input = File("src/main/kotlin/day18/input.txt").readLines()
    solve(input).print()
}

fun solve(input: List<String>): Solution<Int, Int> {
    val init = input.first()
    val rest = input.drop(1)

    val total = rest.fold(init){ acc, s -> acc.add(s) }

    var greatestMagnitude = 0
    for(line in input){
        greatestMagnitude = maxOf(greatestMagnitude, input.filter{it != line}.maxOf{magnitude(line.add(it))})
    }
    return Solution(magnitude(total), greatestMagnitude)
}

fun String.add(other: String) : String{
    val result = "[$this,$other]"
    return reduce(result)
}

private fun reduce(number: String): String{
    var current = ""
    var reduced = number
    while(current != reduced){
        current = reduced
        val result = explode(current, 0, 0, "")
        reduced = result.content.replace("[]", "0")
        if(result.exploded) continue
        reduced = split(current, "")
    }
    return reduced
}

fun explode(number: String): String{
    val explosionData = explode(number, 0, 0, "")
    return explosionData.content.replace("[]", "0")
}

fun explode(number: String, nestLevel: Int, carriedRight: Int, prefix: String, exploding: Boolean = false): ExplosionData{
    if(number.isEmpty()) return ExplosionData(false, 0, prefix)
    if(number.first() == '[') return explode(number.drop(1), nestLevel+1, carriedRight, "$prefix[", exploding)
    if(number.first() == ']') return explode(number.drop(1), nestLevel-1, carriedRight,"$prefix]", exploding)
    if(number.first() == ',') return explode(number.drop(1), nestLevel, carriedRight, "$prefix,", exploding)
    val (leftString, rest) = """^(\d*)(.*)""".toRegex().find(number)?.destructured ?: error("Oh no")
    val left = leftString.toInt()
    if(exploding){
        return ExplosionData(true, 0, "$prefix${left+carriedRight}$rest")
    }
    if (nestLevel > 4){
        val right = """^,(\d*).*""".toRegex().find(rest)?.groups?.get(1)?.value?.toInt() ?: error ("uh oh")
        val number1 = number.drop(number.indexOf(']'))
        val content = explode(number1, nestLevel, right, prefix, true)
        return ExplosionData(true, left, content.content)
    }
    val content = explode(number.drop(leftString.length), nestLevel, carriedRight, "")
    return ExplosionData(content.exploded, 0, "$prefix${left+content.left}${content.content}")
}

fun split(number:String): String{
    return split(number, "")
}
fun split(number: String, result: String): String{
    if(number.isEmpty()) return result
    if("[,]".contains(number.first())) return split(number.drop(1), result + number.first())
    val (numberString, rest) = """^(\d*)(.*)""".toRegex().find(number)?.destructured ?: error("oh no")
    val subject = numberString.toInt()
    if (subject > 9){
        val left = floor(subject/2.0).toInt()
        val right = ceil(subject/2.0).toInt()
        return "$result[$left,$right]$rest"
    }
    return split(rest, result + numberString)
}

fun magnitude(number: String): Int{
    return getMagnitude(number).first
}

private fun getMagnitude(number: String): Pair<Int, String>{
    if(number.isEmpty()) return 0 to ""
    if(number.first() == '[') {
        val (left, rest) = getMagnitude(number.drop(1))
        val (right, _rest) = getMagnitude(rest)
        return (3*left) + (2*right) to _rest
    }
    if(number.first().isDigit()){
        return Character.getNumericValue(number.first()) to number.drop(1)
    }
    return getMagnitude(number.drop(1))
}


data class ExplosionData(val exploded: Boolean, val left: Int, val content: String)