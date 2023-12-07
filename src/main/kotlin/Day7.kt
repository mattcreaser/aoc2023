import kotlin.math.pow

class Day7 : AdventOfCodeDay(7) {
    override fun part1() = run("23456789TJQKA") { value, _ -> value }
    override fun part2() = run("J23456789TQKA") { value, jokers ->
        when (jokers) {
            1 -> when (value) {
                1, 2, 3 -> value + 2
                else -> value + 1
            }
            2 -> when (value) {
                1, 2, 4 -> value + 2
                else -> value
            }
            3 -> value + 2
            4 -> 6
            5 -> 6
            else -> value
        }
    }

    private fun run(cardValue: String, sortValue: (Int, Int) -> Int) = input.map { parse(it, cardValue, sortValue) }
        .sorted()
        .foldIndexed(0) { i, sum, card -> sum + (i + 1) * card.bid }

    private fun parse(line: String, cardValue: String, adjust: (Int, Int) -> Int): Hand {
        val (cards, bid) = line.split(" ")
        var handValue = 0
        val counts = IntArray(13)
        val tiebreak = cards.foldIndexed(0) { i, acc, char ->
            val value = cardValue.indexOf(char)
            val count = ++counts[value]
            when (count) {
                2, 5 -> handValue++
                3, 4 -> handValue += 2
            }
            acc + value * 13f.pow(4 - i).toInt()
        }
        return Hand(cards, adjust(handValue, counts[0]) * 1_000_000 + tiebreak, bid.toInt())
    }

    private data class Hand(val cards: String, val sortValue: Int, val bid: Int) : Comparable<Hand> {
        override fun compareTo(other: Hand) = sortValue - other.sortValue
    }
}

fun main() = Day7().run()