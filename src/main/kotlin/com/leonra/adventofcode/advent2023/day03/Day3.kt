package com.leonra.adventofcode.advent2023.day03

import com.leonra.adventofcode.shared.readResource
import kotlin.math.max
import kotlin.math.min

/** https://adventofcode.com/2023/day/3 */
private object Day3 {

    fun partOne(): Int {
        val schematic = mutableListOf<List<Char>>()
        readResource("/2023/day03/part1.txt") { schematic.add(it.toCharArray().asList()) }

        fun isLinkedToPart(inRow: Int, colStart: Int, colEnd: Int): Boolean {
            val rowRange = max(0, inRow - 1)..min(schematic.size - 1, inRow + 1)
            val colRange = max(0, colStart - 1)..min(schematic[inRow].size - 1, colEnd + 1)
            for (row in rowRange) {
                for (col in colRange) {
                    val cell = schematic[row][col]
                    if (!cell.isDigit() && cell != NOT_A_PART) {
                        return true
                    }
                }
            }
            return false
        }

        var sum = 0
        for (row in schematic.indices) {
            var possiblePartNumber = ""
            var possibleStart = UNDEFINED_START
            for (col in schematic[row].indices) {
                val cell = schematic[row][col]
                if (cell.isDigit()) {
                    possiblePartNumber += cell
                    if (possibleStart == UNDEFINED_START) {
                        possibleStart = col
                    }
                }
                if ((!cell.isDigit() || col == schematic[row].lastIndex) && possiblePartNumber.isNotEmpty()) {
                    if (isLinkedToPart(row, possibleStart, col - 1)) {
                        sum += possiblePartNumber.toInt()
                    }
                    possiblePartNumber = ""
                    possibleStart = UNDEFINED_START
                }
            }
        }

        return sum
    }

    fun partTwo(): Int {
        val schematic = mutableListOf<List<Char>>()
        readResource("/2023/day03/part1.txt") { schematic.add(it.toCharArray().asList()) }

        fun getLinkedGear(inRow: Int, colStart: Int, colEnd: Int): Pair<Int, Int>? {
            val rowRange = max(0, inRow - 1)..min(schematic.size - 1, inRow + 1)
            val colRange = max(0, colStart - 1)..min(schematic[inRow].size - 1, colEnd + 1)
            for (row in rowRange) {
                for (col in colRange) {
                    val cell = schematic[row][col]
                    if (!cell.isDigit() && cell == GEAR) {
                        return row to col
                    }
                }
            }
            return null
        }

        val possibleGears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        for (row in schematic.indices) {
            var possibleGearNumber = ""
            var possibleStart = UNDEFINED_START
            for (col in schematic[row].indices) {
                val cell = schematic[row][col]
                if (cell.isDigit()) {
                    possibleGearNumber += cell
                    if (possibleStart == UNDEFINED_START) {
                        possibleStart = col
                    }
                }
                if ((!cell.isDigit() || col == schematic[row].lastIndex) && possibleGearNumber.isNotEmpty()) {
                    val linkedGear = getLinkedGear(row, possibleStart, col - 1)
                    if (linkedGear != null) {
                        val possibleRatios = possibleGears[linkedGear] ?: mutableListOf()
                        possibleRatios.add(possibleGearNumber.toInt())
                        possibleGears[linkedGear] = possibleRatios
                    }
                    possibleGearNumber = ""
                    possibleStart = UNDEFINED_START
                }
            }
        }

        return possibleGears
            .filterValues { it.size == 2 }
            .map { it.value[0] * it.value[1] }
            .sum()
    }

    private const val UNDEFINED_START = Int.MIN_VALUE
    private const val NOT_A_PART = '.'
    private const val GEAR = '*'
}

private fun main() {
    println("Part 1 sum: ${Day3.partOne()}")
    println("Part 2 sum: ${Day3.partTwo()}")
}
