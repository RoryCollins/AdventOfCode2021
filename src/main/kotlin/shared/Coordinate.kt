package shared

data class Coordinate(val X: Int, val Y: Int) {
    fun plus(other: Coordinate): Coordinate = Coordinate(X + other.X, Y + other.Y)
}