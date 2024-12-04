import kotlin.math.abs

fun main() {
    fun getLocationLists(input: List<String>): Pair<ArrayList<Int>, ArrayList<Int>> {
        val firstList = arrayListOf<Int>()
        val secondList = arrayListOf<Int>()
        input.forEach { line ->
            val ids = line.split("   ")
            val firstId = ids[0].toInt()
            val secondId = ids[1].toInt()
            firstList.add(firstId)
            secondList.add(secondId)
        }
        return Pair(firstList, secondList)
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = getLocationLists(input)
        firstList.sort()
        secondList.sort()

        return firstList.mapIndexed { index, id -> abs(id - secondList[index]) }.sum()

    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = getLocationLists(input)
        val occurrenceInSecondList = secondList.groupingBy { it }.eachCount()
        return firstList.sumOf { id -> occurrenceInSecondList.getOrDefault(id,0) * id }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("0   1", "2   3")) == 2)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
