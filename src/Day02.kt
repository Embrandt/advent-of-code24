import kotlin.math.abs

data class Report(val levels: List<Int>) {
    fun isSafe(): Boolean {
        return isSafe(levels)
    }

    fun isSafeWithDampening(): Boolean {
        if (isSafe(levels)) {
            return true
        }
        for (dampenerIndex in levels.indices) {
            val dampenedLevels = levels.filterIndexed { index, _ -> index != dampenerIndex }
            if (isSafe(dampenedLevels)) {
                return true
            }
        }
        return false
    }

    private fun isSafe(dampenedLevels: List<Int>): Boolean {
        if (dampenedLevels[0] == dampenedLevels[1]) {
            return false
        }
        val increasing = dampenedLevels[1] > dampenedLevels[0]
        dampenedLevels.windowed(2, 1)
            .forEach { pair ->
                val difference = abs(pair[0] - pair[1])
                if (difference > 3 || difference < 1) {
                    return false
                }
                if (increasing) {
                    if (pair[1] - pair[0] < 0)
                        return false
                } else {
                    if (pair[0] - pair[1] < 0) {
                        return false
                    }
                }
            }
        return true
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> Report(line.split(" ").map { it.toInt() }) }
            .filter { it.isSafe() }
            .size
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> Report(line.split(" ").map { it.toInt() }) }
            .filter { it.isSafeWithDampening() }
            .size
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("1 5", "1 2", "3 2")) == 2)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
