package com.leonra.adventofcode.advent2023.day06

import com.leonra.adventofcode.shared.readResource

/** https://adventofcode.com/2023/day/6 */
private object Day6 {

    fun partOne(): Int {
        val times = mutableListOf<Int>()
        val records = mutableListOf<Int>()

        fun parseInputs(data: String): List<Int> =
            data.split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .toList()

        readResource("/2023/day06/part1.txt") { line ->
            val timeMatch = TIME.find(line)
            val distanceMatch = RECORD.find(line)

            if (timeMatch != null) {
                times.addAll(parseInputs(requireNotNull(timeMatch.groups[1]).value))
            } else if (distanceMatch != null) {
                records.addAll(parseInputs(requireNotNull(distanceMatch.groups[1]).value))
            }
        }

        var margin = 1
        for (index in times.indices) {
            val time = times[index]
            val record = records[index]
            var ways = 0

            for (hold in 1 until time) {
                val distance = hold * (time - hold)
                if (distance > record) {
                    ways++
                }
            }

            margin *= ways
        }

        return margin
    }

    fun partTwo(): Long {
        var time = 0L
        var record = 0L

        fun parseInput(data: String): Long =
            data.split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .fold("") { previous, next -> previous + next }
                .toLong()

        readResource("/2023/day06/part1.txt") { line ->
            val timeMatch = TIME.find(line)
            val distanceMatch = RECORD.find(line)

            if (timeMatch != null) {
                time = parseInput(requireNotNull(timeMatch.groups[1]).value)
            } else if (distanceMatch != null) {
                record = parseInput(requireNotNull(distanceMatch.groups[1]).value)
            }
        }

        var ways = 0L
        for (hold in 1 until time) {
            val distance = hold * (time - hold)
            if (distance > record) {
                ways++
            }
        }

        return ways
    }

    private val TIME = Regex("Time: (.+)")
    private val RECORD = Regex("Distance: (.+)")
}

private fun main() {
    println("Part 1 margin of error: ${Day6.partOne()}")
    println("Part 2 ways: ${Day6.partTwo()}")
}
