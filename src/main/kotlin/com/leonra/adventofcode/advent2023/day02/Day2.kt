package com.leonra.adventofcode.advent2023.day02

import com.leonra.adventofcode.shared.readResource
import kotlin.math.max

/** https://adventofcode.com/2023/day/2 */
private object Day2 {

    fun partOne(): Int {
        val redMax = 12
        val greenMax = 13
        val blueMax = 14

        var sum = 0
        readResource("/2023/day02/part1.txt") { line ->
            var isGameValid = true
            val pulls = line.split(DELIMITER)
            for (pull in pulls) {
                val redCount = pull.ballCount(RED_BALLS)
                val greenCount = pull.ballCount(GREEN_BALLS)
                val blueCount = pull.ballCount(BLUE_BALLS)

                if (redCount > redMax || greenCount > greenMax || blueCount > blueMax) {
                    isGameValid = false
                    break
                }
            }
            if (isGameValid) {
                GAME_ID.find(line)?.let { sum += it.groups[1]?.value?.toInt() ?: 0 }
            }
        }
        return sum
    }

    fun partTwo(): Int {
        var sum = 0
        readResource("/2023/day02/part1.txt") { line ->
            var minRed = 0
            var minBlue = 0
            var minGreen = 0

            val pulls = line.split(DELIMITER)
            for (pull in pulls) {
                val redCount = pull.ballCount(RED_BALLS)
                val greenCount = pull.ballCount(GREEN_BALLS)
                val blueCount = pull.ballCount(BLUE_BALLS)

                minRed = max(minRed, redCount)
                minGreen = max(minGreen, greenCount)
                minBlue = max(minBlue, blueCount)
            }
            sum += (minRed * minBlue * minGreen)
        }
        return sum
    }

    private val GAME_ID = Regex("Game (\\d+):")
    private val DELIMITER = Regex("[:;]")

    private val RED_BALLS = Regex("(\\d+) red")
    private val GREEN_BALLS = Regex("(\\d+) green")
    private val BLUE_BALLS = Regex("(\\d+) blue")

    private fun String.ballCount(ballRegex: Regex): Int =
        ballRegex.find(this)?.let { it.groups[1]?.value?.toInt() } ?: 0
}

private fun main() {
    println("Part 1 sum: ${Day2.partOne()}")
    println("Part 2 sum: ${Day2.partTwo()}")
}