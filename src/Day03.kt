fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("mul\\([0-9]{1,3}?,[0-9]{1,3}?\\)")
        return input
            .flatMap { regex.findAll(it) }.sumOf { correctCall ->
                correctCall.value.removePrefix("mul(").removeSuffix(")")
                    .split(",")
                    .map { it.toInt() }
                    .reduceRight(Int::times)
            }
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("mul\\([0-9]{1,3}?,[0-9]{1,3}?\\)")
        val conditionRegex = Regex("(do\\(\\))|(don't\\(\\))")
        var currentState = true
        val conditions = input.map { conditionRegex.findAll(it).toList() }
            .map { conditions -> conditions.map { matchResult -> matchResult.range.first to (matchResult.value == "do()") } }
        val flatMapIndexed = input.map { regex.findAll(it) }.flatMapIndexed { index, line ->
            val condition = conditions[index]
            line.filter { match ->
                for (pair in condition) {
                    if (pair.first > match.range.first) {
                        break
                    }
                    currentState = pair.second
                }
                return@filter currentState
            }
        }
        return flatMapIndexed.sumOf { correctCall ->
            correctCall.value.removePrefix("mul(").removeSuffix(")")
                .split(",")
                .map { it.toInt() }
                .reduceRight(Int::times)
        }
    }

    // Test if implementation meets criteria from the description, like:
    val testResult = part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"))
    check(testResult == 161){"Test implementation returned $testResult"}
    val testResult2 = part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
    check(testResult2 == 48){"Test implementation part 2 returned $testResult"}

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 161){"Test data returned $testOutPutPart1"}

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    "Part1: ${part1(input)}".println()
    "Part2: ${part2(input)}".println()
}
