data class Antenna(val type: Char, val position: Position)
data class FrequencyMap(val input: List<String>) {
    private val antennas = input.flatMapIndexed { y, line ->
        line
            .mapIndexed { x, c -> Antenna(c, Position(x, y)) }
            .filter { it.type != '.' }
    }

    fun getAntiNodes(): Set<Position> {
        return antennas.groupBy(Antenna::type)
            .flatMap { calculateAntiNodes(it.value, ::calculateOnXGrid) }
            .filter(::isOnMap)
            .toSet()
    }

    fun getAntiNodesWithDistance(): Set<Position> {
        return antennas.groupBy(Antenna::type)
            .flatMap { calculateAntiNodes(it.value, ::calculateWithDistance) }
            .filter(::isOnMap)
            .toSet()
    }

    private fun isOnMap(point: Position): Boolean {
        return point.first in 0..(input[0].lastIndex) && point.second in 0..(input.lastIndex)
    }

    private fun calculateAntiNodes(
        antennas: List<Antenna>,
        antiNodeTransformer: (Antenna, Antenna) -> Set<Position>
    ): Set<Position> {
        return antennas.flatMapIndexed { index, antenna ->
            antennas.drop(index + 1)
                .flatMap { subAntenna -> antiNodeTransformer(subAntenna, antenna) }
        }.toSet()
    }

    fun calculateOnXGrid(antennaA: Antenna, antennaB: Antenna): Set<Position> {
        var diff = antennaB.position - antennaA.position
        if (diff.second == 0) {
            diff = Position(1, 0)
        }
        return generateSequence(antennaA.position) { it - diff }.takeWhile { isOnMap(it) }.toSet() +
                generateSequence(antennaA.position) { it + diff }.takeWhile { isOnMap(it) }.toSet()
    }
}

fun calculateWithDistance(antennaA: Antenna, antennaB: Antenna): Set<Position> {
    val diff = antennaA.position - antennaB.position
    val antiNodeA = antennaA.position + diff
    val antiNodeB = antennaB.position - diff
    return setOf(antiNodeA, antiNodeB)
}


fun main() {
    fun part1(input: List<String>): Int {
        val frequencyMap = FrequencyMap(input)
        return frequencyMap.getAntiNodesWithDistance().size
    }

    fun part2(input: List<String>): Int {
        val frequencyMap = FrequencyMap(input)
        return frequencyMap.getAntiNodes().size
    }

    // Test if implementation meets criteria from the description, like:
    var testResult = part1(listOf("..A.A.."))
    check(testResult == 2) { "Test implementation returned $testResult" }
    testResult = part2(listOf("..A.A.."))
    check(testResult == 7) { "Test implementation returned $testResult" }
    testResult = part2(listOf("..A.A..", "...A...", ".......", "......."))
    check(testResult == 12) { "Test implementation returned $testResult" }

    // Or read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    val testOutPutPart1 = part1(testInput)
    check(testOutPutPart1 == 14) { "Test data returned $testOutPutPart1" }
    val testOutPutPart2 = part2(testInput)
    check(testOutPutPart2 == 34) { "Test data 2 returned $testOutPutPart2" }

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    check(part1(input) == 361) { "Wrong again ${part1(input)}" }
    "Part1: ${part1(input)}".println()
    check(part2(input) == 1249) { "Wrong again ${part2(input)}" }
    "Part2: ${part2(input)}".println()
}
