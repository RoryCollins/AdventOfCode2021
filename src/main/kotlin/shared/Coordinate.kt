package shared

data class Coordinate(val x: Int, val y: Int) {
    fun plus(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)
}


data class CoordinateScore(val coordinate: Coordinate, val score: Int)