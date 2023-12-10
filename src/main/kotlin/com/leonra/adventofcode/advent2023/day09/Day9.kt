package com.leonra.adventofcode.advent2023.day09

import com.leonra.adventofcode.shared.readResource

/** https://adventofcode.com/2023/day/9 */
private object Day9 {

    fun partOne(): Int {
        val histories = mutableListOf<List<Int>>()

        readResource("/2023/day09/part1.txt") { line ->
            val history = line.split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .toList()
            histories.add(history)
        }

        var sum = 0
        for (history in histories) {
            val steps = mutableListOf<List<Int>>()
            steps.add(history)
            while (steps.last().any { it !=  0 }) {
                val nextStep = mutableListOf<Int>()
                for (index in 0 until steps.last().size - 1) {
                    nextStep.add(steps.last()[index + 1] - steps.last()[index])
                }
                steps.add(nextStep)
            }
            var previous = steps.last().last()
            for (index in (0 until steps.size - 1).reversed()) {
                previous += steps[index].last()
            }
            sum += previous
        }

        return sum
    }

    fun partTwo(): Int {
        val histories = mutableListOf<List<Int>>()

        readResource("/2023/day09/part1.txt") { line ->
            val history = line.split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .toList()
            histories.add(history)
        }

        var sum = 0
        for (history in histories) {
            val steps = mutableListOf<List<Int>>()
            steps.add(history)
            while (steps.last().any { it !=  0 }) {
                val nextStep = mutableListOf<Int>()
                for (index in 0 until steps.last().size - 1) {
                    nextStep.add(steps.last()[index + 1] - steps.last()[index])
                }
                steps.add(nextStep)
            }
            var previous = steps.last().first()
            for (index in (0 until steps.size - 1).reversed()) {
                previous = steps[index].first() - previous
            }
            sum += previous
        }

        return sum
    }
}

private fun main() {
     println("Part 1 sum: ${Day9.partOne()}")
     println("Part 2 sum: ${Day9.partTwo()}")
}
