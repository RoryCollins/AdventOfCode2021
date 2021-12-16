package shared

open class Grid(private val rows: List<List<Int>>) {
    protected open fun getNeighbours(coordinate: Coordinate): List<Coordinate>{
        return getAdjacentNeighbours(coordinate)
    }

    protected fun getAdjacentNeighbours(coordinate: Coordinate): List<Coordinate>{
        return listOf(
            Coordinate(0,1),
            Coordinate(0,-1),
            Coordinate(1,0),
            Coordinate(-1,0)).map{coordinate.plus(it)}
            .filter{!isOutOfBounds(it)}
    }

    protected fun getDiagonalNeighbours(coordinate: Coordinate): List<Coordinate>{
        return listOf(
            Coordinate(1,1),
            Coordinate(1,-1),
            Coordinate(-1,1),
            Coordinate(-1,-1)).map{coordinate.plus(it)}
            .filter{!isOutOfBounds(it)}
    }

    private fun isOutOfBounds(f: Coordinate): Boolean {
        return f.x < 0 || f.y < 0 || f.x >= rows.first().size || f.y >= rows.size
    }
}