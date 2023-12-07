package com.leonra.adventofcode.advent2023.day05

import com.leonra.adventofcode.shared.readResource

/** https://adventofcode.com/2023/day/5 */
private object Day5 {

    data class Mapping(val source: LongRange, val diff: Long)

    fun partOne(): Long {
        val mapped = mutableSetOf<Long>()
        val ranges = mutableListOf<Mapping>()

        readResource("/2023/day05/part1.txt") { line ->
            if (line.isBlank()) {
                return@readResource
            }

            val seedMatch = SEEDS.find(line)
            val mappingMatch = MAPPINGS.find(line)

            if (seedMatch != null) {
                requireNotNull(seedMatch.groups[1])
                    .value
                    .split(" ")
                    .map { it.toLong() }
                    .forEach { mapped.add(it) }
            } else if (mappingMatch != null) {
                if (ranges.isNotEmpty()) {
                    val toMap = mapped.toSet()
                    mapped.clear()
                    toMap.forEach { input ->
                        val output = ranges
                            .firstOrNull { it.source.contains(input) }
                            ?.let { input - it.diff }
                            ?: input
                        mapped.add(output)
                    }
                }
                ranges.clear()
            } else {
                val values = line.split(" ").map { it.toLong() }
                ranges.add(Mapping(source = values[1]until values[1] + values[2], diff = values[1] - values[0]))
            }
        }

        val toMap = mapped.toSet()
        mapped.clear()
        toMap.forEach { input ->
            val output = ranges
                .firstOrNull { it.source.contains(input) }
                ?.let { input - it.diff }
                ?: input
            mapped.add(output)
        }

        return mapped.min()
    }

    fun partTwo(): Long {
        val seeds = mutableListOf<LongRange>()

        var mappingIndex = 0
        val mappings = mutableListOf<MutableSet<Mapping>>()

        readResource("/2023/day05/part1.txt") { line ->
            if (line.isBlank()) {
                return@readResource
            }

            val seedMatch = SEEDS.find(line)
            val mappingMatch = MAPPINGS.find(line)

            if (seedMatch != null) {
                requireNotNull(seedMatch.groups[1])
                    .value
                    .split(" ")
                    .map { it.toLong() }
                    .chunked(2)
                    .map { it[0] until it[0] + it[1] }
                    .forEach { seeds.add(it) }
            } else if (mappingMatch != null) {
                mappingIndex++
            } else {
                val values = line.split(" ").map { it.toLong() }
                if (mappingIndex > mappings.size) {
                    mappings.add(mutableSetOf())
                }
                mappings[mappingIndex - 1]
                    .add(Mapping(source = values[1]until values[1] + values[2], diff = values[1] - values[0]))
            }
        }

        var min = Long.MAX_VALUE
        for (seedGroup in seeds) {
            for (seed in seedGroup.iterator()) {
                mappingIndex = 0
                var input = seed
                while (mappingIndex < mappings.size) {
                    input = mappings[mappingIndex]
                        .firstOrNull { it.source.contains(input) }
                        ?.let { input - it.diff }
                        ?: input
                    mappingIndex++
                }
                if (input < min) {
                    min = input
                }
            }
        }

        return min
    }

    private val SEEDS = Regex("seeds: (.+)")
    private val MAPPINGS = Regex(".+ map:")
}

private fun main() {
    println("Part 1 smallest location number: ${Day5.partOne()}")
    println("Part 2 smallest location number: ${Day5.partTwo()}")
}
