import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun String.toNumberList(): List<Int> {
    val numberRegEx = "(\\d+)".toRegex()
    return numberRegEx.findAll(this).map { match -> match.value.toInt() }.toList()
}

fun String.toLongNumberList(): List<Long> {
    val numberRegEx = "(\\d+)".toRegex()
    return numberRegEx.findAll(this).map { match -> match.value.toLong() }.toList()
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

typealias Position = Pair<Int, Int>

operator fun Position.plus(position: Position): Position {
    return Position(this.first + position.first, this.second + position.second)
}

operator fun Position.minus(position: Position): Position {
    return Position(this.first - position.first, this.second - position.second)
}