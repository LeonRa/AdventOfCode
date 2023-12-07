package com.leonra.adventofcode.advent2023.day07

import com.leonra.adventofcode.shared.readResource

/** https://adventofcode.com/2023/day/7 */
private object Day7 {

    data class Game(val hand: String, val bid: Int)

    fun partOne(): Int {
        class CardComparator: Comparator<Char> {

            val valueMap = mapOf(
                'A' to 13,
                'K' to 12,
                'Q' to 11,
                'J' to 10,
                'T' to 9,
                '9' to 8,
                '8' to 7,
                '7' to 6,
                '6' to 5,
                '5' to 4,
                '4' to 3,
                '3' to 2,
                '2' to 1,
            )

            override fun compare(card1: Char, card2: Char): Int =
                checkNotNull(valueMap[card1]).compareTo(checkNotNull(valueMap[card2]))
        }

        class HandComparator: Comparator<String> {

            val cardComparator = CardComparator()

            override fun compare(hand1: String, hand2: String): Int {
                val hand1Type = hand1.type
                val hand2Type = hand2.type
                if (hand1Type == hand2Type) {
                    for (index in hand1.indices) {
                        val cardCompare = cardComparator.compare(hand1[index], hand2[index])
                        if (cardCompare == 0) {
                            continue
                        }
                        return cardCompare
                    }
                    return 0
                }
                return hand1Type - hand2Type
            }

            val String.type: Int
                get() {
                    val cards = mutableMapOf<Char, Int>()
                    for (card in this) {
                        cards[card] = (cards[card] ?: 0) + 1
                    }
                    val duplicates = cards.maxOf { it.value }
                    val uniques = cards.size
                    return when {
                        duplicates == 5 -> FIVE_OF_A_KIND
                        duplicates == 4 -> FOUR_OF_A_KIND
                        duplicates == 3 && uniques == 2 -> FULL_HOUSE
                        duplicates == 3 && uniques == 3 -> THREE_OF_A_KIND
                        duplicates == 2 && uniques == 3 -> TWO_PAIR
                        duplicates == 2 && uniques == 4 -> ONE_PAIR
                        else -> HIGH_CARD
                    }
                }
        }

        val games = mutableListOf<Game>()

        readResource("/2023/day07/part1.txt") { line ->
            val split = line.split(" ")
            games.add(Game(hand = split[0], bid = split[1].toInt()))
        }

        val handComparator = HandComparator()
        games.sortWith { game1, game2 -> handComparator.compare(game1.hand, game2.hand) }

        var winnings = 0
        games.forEachIndexed { index, game ->
            winnings += (index + 1) * game.bid
        }
        return winnings
    }

    fun partTwo(): Int {
        class CardComparator: Comparator<Char> {

            val valueMap = mapOf(
                'A' to 13,
                'K' to 12,
                'Q' to 11,
                'T' to 10,
                '9' to 9,
                '8' to 8,
                '7' to 7,
                '6' to 6,
                '5' to 5,
                '4' to 4,
                '3' to 3,
                '2' to 2,
                'J' to 1,
            )

            override fun compare(card1: Char, card2: Char): Int =
                checkNotNull(valueMap[card1]).compareTo(checkNotNull(valueMap[card2]))
        }

        class HandComparator: Comparator<String> {

            val cardComparator = CardComparator()

            override fun compare(hand1: String, hand2: String): Int {
                val hand1Type = hand1.strongestType
                val hand2Type = hand2.strongestType
                if (hand1Type == hand2Type) {
                    for (index in hand1.indices) {
                        val cardCompare = cardComparator.compare(hand1[index], hand2[index])
                        if (cardCompare == 0) {
                            continue
                        }
                        return cardCompare
                    }
                    return 0
                }
                return hand1Type - hand2Type
            }

            private val String.strongestType: Int
                get() {
                    val cards = mutableMapOf<Char, Int>()
                    for (card in this) {
                        cards[card] = (cards[card] ?: 0) + 1
                    }
                    val jacks = cards.remove('J') ?: 0
                    val duplicates = cards.maxOfOrNull { it.value } ?: 0
                    val uniques = cards.size
                    return when {
                        duplicates + jacks == 5 -> FIVE_OF_A_KIND
                        duplicates + jacks == 4 -> FOUR_OF_A_KIND
                        duplicates == 3 && uniques == 2 && jacks == 0 -> FULL_HOUSE
                        duplicates == 2 && uniques == 2 && jacks == 1 -> FULL_HOUSE
                        duplicates == 3 && uniques == 3 && jacks == 0 -> THREE_OF_A_KIND
                        duplicates == 2 && uniques == 3 && jacks == 1 -> THREE_OF_A_KIND
                        duplicates == 1 && uniques == 3 && jacks == 2 -> THREE_OF_A_KIND
                        duplicates == 2 && uniques == 3 && jacks == 0 -> TWO_PAIR
                        duplicates == 2 && uniques == 4 && jacks == 0 -> ONE_PAIR
                        duplicates == 1 && uniques == 4 && jacks == 1 -> ONE_PAIR
                        else -> HIGH_CARD
                    }
                }
        }

        val games = mutableListOf<Game>()

        readResource("/2023/day07/part1.txt") { line ->
            val split = line.split(" ")
            games.add(Game(hand = split[0], bid = split[1].toInt()))
        }

        val handComparator = HandComparator()
        games.sortWith { game1, game2 -> handComparator.compare(game1.hand, game2.hand) }

        var winnings = 0
        games.forEachIndexed { index, game ->
            winnings += (index + 1) * game.bid
        }
        return winnings
    }

    private const val FIVE_OF_A_KIND = 6
    private const val FOUR_OF_A_KIND = 5
    private const val FULL_HOUSE = 4
    private const val THREE_OF_A_KIND = 3
    private const val TWO_PAIR = 2
    private const val ONE_PAIR = 1
    private const val HIGH_CARD = 0
}

private fun main() {
     println("Part 1 winnings: ${Day7.partOne()}")
     println("Part 2 winnings: ${Day7.partTwo()}")
}
