import Direction.NORTH

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    fun stepForward(): Pair<Int, Int> {
        return when (this) {
            NORTH -> Pair(0, -1)
            SOUTH -> Pair(0, 1)
            EAST -> Pair(1, 0)
            WEST -> Pair(-1, 0)
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            NORTH -> EAST
            SOUTH -> WEST
            WEST -> NORTH
            EAST -> SOUTH
        }
    }
}

typealias Position = Pair<Int, Int>

private operator fun Position.plus(position: Position): Position {
    return Position(position.first + this.first, position.second + this.second)
}

fun main() {
    fun getStartingPosition(input: List<String>): Position {
        input.forEachIndexed { y, line ->
            val potentialX = line.indexOf('^')
            if (potentialX != -1) {
                return Position(potentialX, y)
            }
        }
        return Position(0, 0)
    }

    fun part1(input: List<String>): Int {
        val startingPosition = getStartingPosition(input)
        var facingDirection = NORTH

        val positionsVisited = mutableSetOf(startingPosition)
        var currentPosition = startingPosition
        var newPosition = startingPosition + facingDirection.stepForward()
        while (newPosition.second < input.size && newPosition.first < input[0].length) {
            if (input[newPosition.second][newPosition.first] == '#') {
                facingDirection = facingDirection.turnRight()
            } else {
                currentPosition = newPosition
                positionsVisited.add(currentPosition)
            }
            newPosition = currentPosition + facingDirection.stepForward()
        }
        return positionsVisited.size
    }
    fun createsLoop(newObstacle : Position, map: List<String>, startingPosition: Position) : Boolean {
        var facingDirection = NORTH
        val positionsVisited = mutableSetOf(Pair(startingPosition,facingDirection))
        var currentPosition = startingPosition
        var newPosition = startingPosition + facingDirection.stepForward()
        while (newPosition.second < map.size && newPosition.first < map[0].length && newPosition.first >= 0 && newPosition.second >= 0) {
            if (newObstacle == newPosition || map[newPosition.second][newPosition.first] == '#') {
                facingDirection = facingDirection.turnRight()
            } else {
                if (positionsVisited.contains(Pair(newPosition, facingDirection))) {
                    return true
                }
                currentPosition = newPosition
                positionsVisited.add(Pair(currentPosition,facingDirection))
            }
            newPosition = currentPosition + facingDirection.stepForward()
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val startingPosition = getStartingPosition(input)
        return input.mapIndexed{ y, line ->
            line.filterIndexed{x, _ ->
                createsLoop(Position(x,y),input, startingPosition )
            }.count()
        }.sum()
    }

    // Test if implementation meets criteria from the description, like:
    val testResult = part1(listOf("..#..", "..^.."))
    check(testResult == 3) { "Test implementation returned $testResult" }

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 41) { "Test data returned $testOutPutPart1" }
    val testOutPutPart2 = part2(testInput)
    check(testOutPutPart2 == 6) { "Test data returned $testOutPutPart2" }

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    "Part1: ${part1(input)}".println()
    "Part2: ${part2(input)}".println()
}

