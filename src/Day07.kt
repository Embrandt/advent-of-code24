typealias Operator = (Long, Long) -> Long
typealias Combination = List<Operator>

fun main() {
    fun createOperateList(numberOfOperators: Int): List<Combination> {
        var combinations = listOf<Combination>(listOf())
        repeat(numberOfOperators) {
            combinations =
                combinations.flatMap { combination -> listOf(combination + Long::plus, combination + Long::times) }
        }
        return combinations
    }

    fun canBeValid(equation: List<Long>): Boolean {
        val result = equation.first()
        val operatorList = createOperateList(equation.size - 2)
        for (combination in operatorList) {
            val testResult = equation.drop(1).reduceIndexed { index, acc, l ->
                combination[index-1].invoke(acc, l)

            }
            if (result == testResult) return true
        }
        return false
    }


    fun part1(input: List<String>): Long {
        return input.map { it.toLongNumberList() }
            .filter { canBeValid(it) }
            .sumOf { it[0] }
    }

    fun concatenate(a: Long, b: Long): Long {
        return (a.toString()+b.toString()).toLong()
    }

    fun createOperateList2(numberOfOperators: Int): List<Combination> {
        var combinations = listOf<Combination>(listOf())
        repeat(numberOfOperators) {
            combinations =
                combinations.flatMap { combination ->
                    listOf(
                        combination + Long::plus,
                        combination + Long::times,
                        combination + ::concatenate
                    ) }
        }
        return combinations
    }

    fun canBeValid2(equation: List<Long>): Boolean {
        val result = equation.first()
        val operatorList = createOperateList2(equation.size - 2)
        for (combination in operatorList) {
            val testResult = equation.drop(1).reduceIndexed { index, acc, l ->
                combination[index-1].invoke(acc, l)
            }
            if (result == testResult) return true
        }
        return false
    }
    fun part2(input: List<String>): Long {
        return input.map { it.toLongNumberList() }
            .filter { canBeValid2(it) }
            .sumOf { it[0] }
    }

    // Test if implementation meets criteria from the description, like:
    val testResult = part1(listOf("190: 10 19", "83: 17 5"))
    check(testResult == 190L) { "Test implementation returned $testResult" }
    check(concatenate(13,4) == 134L) { "Test implementation returned wrong" }

    // Or read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 3749L) { "Test data returned $testOutPutPart1" }
    val testOutPutPart2 = part2(testInput)
    check(testOutPutPart2 == 11387L) { "Test data2 returned $testOutPutPart2" }

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    "Part1: ${part1(input)}".println()
    "Part2: ${part2(input)}".println()
}
