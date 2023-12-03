package com.leonra.adventofcode.advent2023.day01

import com.leonra.adventofcode.shared.readResource

private fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    fun findCalibrationValue(line: String): Int =
        10 * line.first { it.isDigit() }.digitToInt() + line.last { it.isDigit() }.digitToInt()

    var sum = 0
    readResource("/2023/day01/part1.txt") { sum += findCalibrationValue(it) }
    println("Part 1 sum: $sum")
}

private fun partTwo() {
    val numbersAsStrings = Regex("(?=(one|two|three|four|five|six|seven|eight|nine|\\d))")

    fun String.toCalibrationIntString(): String =
        this
            .takeUnless { it.toIntOrNull() != null }
            ?.let {
                when (it) {
                    "one" -> "1"
                    "two" -> "2"
                    "three" -> "3"
                    "four" -> "4"
                    "five" -> "5"
                    "six" -> "6"
                    "seven" -> "7"
                    "eight" -> "8"
                    "nine" -> "9"
                    else -> "0"
                }
            }
            ?: this

    fun findCalibrationValue(line: String): Int {
        val numbers = numbersAsStrings.findAll(line)
            .map { it.groups[1]?.value?.toCalibrationIntString() }
            .filterNotNull()
            .toList()

        return 10 * numbers.first().toInt() + numbers.last().toInt()
    }

    var sum = 0
    readResource("/2023/day01/part2.txt") { sum += findCalibrationValue(it) }
    println("Part 2 sum: $sum")
}
