data class Rule(val entry: Int, val pageRestriction: List<Int>) : Comparable<Rule> {
    /**
     *
     *     Zero if the two arguments are equal
     *     A negative number if the first argument is less than the second
     *     A positive number if the first argument is greater than the second
     *
     */
    override fun compareTo(other: Rule): Int {
        if (pageRestriction.contains(other.entry)) return -1
        if (other.pageRestriction.contains(entry)) return 1
        return 0
    }
}

fun main() {
    fun isInRightOrder(update: List<Int>, rules: Map<Int, List<Int>>): Boolean {
        val printed = mutableListOf<Int>()
        for (page in update) {
            printed.add(page)
            val rule = rules[page]
            if (rule != null) {
                if (rule.any { printed.contains(it) }) {
                    return false
                }
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val dividerIndex = input.mapIndexed { index, line -> index to line }.first { it.second.isEmpty() }.first
        val rules = input.subList(0, dividerIndex)
        val associate = rules.groupBy({ ruleString ->
            val number = ruleString.split("|")
            number[0].toInt()
        }, { ruleString ->
            ruleString.split("|")[1].toInt()
        })

        val updates = input.subList(dividerIndex + 1, input.size)
        return updates.map(String::toNumberList)
            .filter { update ->
                isInRightOrder(update, associate)
            }
            .sumOf { pages -> pages[pages.lastIndex / 2] }
    }

    fun part2(input: List<String>): Int {
        val dividerIndex = input.mapIndexed { index, line -> index to line }.first { it.second.isEmpty() }.first
        val rules = input.subList(0, dividerIndex)
            .groupBy(
                { ruleString ->
                    val number = ruleString.split("|")
                    number[0].toInt()
                }, { ruleString ->
                    ruleString.split("|")[1].toInt()
                })

        val updates = input.subList(dividerIndex + 1, input.size)

        return updates.map(String::toNumberList)
            .filter { update ->
                !isInRightOrder(update, rules)
            }
            .map { update ->
                update.map { page ->
                    if (rules.containsKey(page))
                        Rule(page, rules[page]!!)
                    else
                        Rule(page, emptyList())
                }.sorted().map { it.entry }

            }
            .sumOf { pages -> pages[pages.lastIndex / 2] }
    }

    // Test if implementation meets criteria from the description, like:
    val testResult = part1(listOf("1|2", "1|3", "", "1,2,3", "2,1,3"))
    check(testResult == 2) { "Test implementation returned $testResult" }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 143) { "Test data returned $testOutPutPart1" }
    val testOutPutPart2 = part2(testInput)
    check(testOutPutPart2 == 123) { "Test data 2 returned $testOutPutPart2" }

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    "Part1: ${part1(input)}".println()
    check(part2(input) < 5769) { "Part2: ${part2(input)}" }
    "Part2: ${part2(input)}".println()
}
