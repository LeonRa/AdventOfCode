package com.leonra.adventofcode.advent2023.day04

import com.leonra.adventofcode.shared.readResource
import kotlin.math.pow

/** https://adventofcode.com/2023/day/4 */
private object Day4 {

    fun partOne(): Int {
        var sum = 0

        readResource("/2023/day04/part1.txt") { line ->
            val winnersAndCard = line.split(": ")[1].split(" | ")
            val winners =
                winnersAndCard[0]
                    .split(" ")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toSet()
            val matches =
                winnersAndCard[1]
                    .split(" ")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .count { it in winners }

            sum +=
                when(matches) {
                    0 -> 0
                    else -> 2.0.pow(matches - 1).toInt()
                }
        }

        return sum
    }

    fun partTwo(): Int {
        var sum = 0
        val extrasToCount = mutableMapOf<Int, Int>()

        readResource("/2023/day04/part1.txt") { line ->
            val metaAndRest = line.split(":")
            val winnersAndCard = metaAndRest[1].split(" | ")

            val cardNumber = metaAndRest[0].split("Card")[1].trim().toInt()
            val winners =
                winnersAndCard[0]
                    .split(" ")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toSet()
            val matches =
                winnersAndCard[1]
                    .split(" ")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .count { it in winners }


            val copiesOfCard = extrasToCount.getOrDefault(cardNumber, 0) + 1

            for (copiedCardNumber in cardNumber + 1 .. cardNumber + matches) {
                extrasToCount[copiedCardNumber] =
                    extrasToCount.getOrDefault(copiedCardNumber, 0) + copiesOfCard
            }

            sum += copiesOfCard
        }

        return sum
    }
}

private fun main() {
    println("Part 1 sum: ${Day4.partOne()}")
    println("Part 2 sum: ${Day4.partTwo()}")
}
