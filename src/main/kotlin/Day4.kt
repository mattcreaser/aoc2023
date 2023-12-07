import kotlin.math.pow

class Day4(input: String? = null) : AdventOfCodeDay(input) {
    override fun part1() = input.sumOf { line ->
        2f.pow(line.countWins() - 1).toInt()
    }

    override fun part2(): Any {
        val cardInstances = Array(lines.size) { 1 }
        lines.forEachIndexed { index, line ->
            for (i in 1..line.countWins()) {
                cardInstances[index + i] += cardInstances[index]
            }
        }
        return cardInstances.sum()
    }

    private fun String.countWins() = cardNums().count { winningNums().contains(it) }
    private fun String.winningNums() = Regex.integer.findAll(substringBefore('|'), 9).map { it.value.toInt() }
    private fun String.cardNums() = Regex.integer.findAll(substringAfter('|')).map { it.value.toInt() }
}

fun main() = Day4().run()