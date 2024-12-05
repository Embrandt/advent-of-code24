import kotlin.math.max
import kotlin.math.min

fun main() {
    fun containsHorizontal(currentLine: String): Int {
        var number = 0
        if (currentLine.contains("XMAS"))
            number += 1
        if (currentLine.contains("SAMX"))
            number += 1
        return number
    }

    fun getSubStrings(startX: Int, startY: Int, input: List<String>): List<String> {
        val minX = max(0, startX - 3)
        val maxX = min(startX + 3, input[0].lastIndex)
        val maxY = min(startY + 3, input.lastIndex)
        val minY = max(0, startY - 3)
        val subStrings = mutableListOf<String>()
        // horizontal
        subStrings.add(input[startY].substring(minX, maxX + 1))
        // vertical
        val map = input.subList(minY, maxY + 1).map {
            it[startX]
        }.joinToString("")
        subStrings.add(map)
        // diagonal right
        var diagonalR = ""
        if (minY == startY - 3 && minX == startX - 3) {
            for (i in 3 downTo 1) {
                diagonalR += input[startY - i][startX - i]
            }
        }
        diagonalR += input[startY][startX]
        if (maxY == startY + 3 && maxX == startX + 3) {
            for (i in 1..3) {
                diagonalR += input[startY + i][startX + i]
            }
        }
        subStrings.add(diagonalR)

        // diagonal left
        var diagonalL = ""
        if (maxY == startY + 3 && minX == startX - 3) {
            for (i in 3 downTo 1) {
                diagonalL += input[startY + i][startX - i]
            }
        }
        diagonalL += input[startY][startX]
        if (minY == startY - 3 && maxX == startX + 3) {
            for (i in 1..3) {
                diagonalL += input[startY - i][startX + i]
            }
        }
        subStrings.add(diagonalL)

        return subStrings
    }

    fun isXmas(startX: Int, startY: Int, input: List<String>): Boolean {
        if (startX - 1 < 0 || startX + 1 > input[0].lastIndex)
            return false
        if (startY - 1 < 0 || startY + 1 > input.lastIndex)
            return false
        val diagonalRight = "" + input[startY - 1][startX - 1] + input[startY][startX] + input[startY + 1][startX + 1]
        val diagonalLeft = "" + input[startY + 1][startX - 1] + input[startY][startX] + input[startY - 1][startX + 1]
        if (diagonalLeft != "MAS" && diagonalLeft != "SAM") {
            return false
        }
        if (diagonalRight != "MAS" && diagonalRight != "SAM") {
            return false
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val subStrings =
            input.flatMapIndexed { y, line ->
                line.flatMapIndexed { x, character ->
                    if (character == 'X') {
                        getSubStrings(x, y, input)
                    } else {
                        emptyList()
                    }
                }
            }
        return subStrings.sumOf { substring -> containsHorizontal(substring) }
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { y, line -> line.filterIndexed { x, _ -> isXmas(x, y, input) }.count() }.sum()
    }

    // Test if implementation meets criteria from the description, like:
    val testResult = part1(listOf("XMASkjSAMX"))
    check(testResult == 2) { "Test implementation returned $testResult" }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 18) { "Test data returned $testOutPutPart1" }
    val testOutPutPart2 = part2(testInput)
    check(testOutPutPart2 == 9) { "Test data returned $testOutPutPart2" }

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    "Part1: ${part1(input)}".println()
    "Part2: ${part2(input)}".println()
}
