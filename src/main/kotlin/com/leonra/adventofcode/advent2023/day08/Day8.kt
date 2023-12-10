package com.leonra.adventofcode.advent2023.day08

import com.leonra.adventofcode.shared.readResource

/** https://adventofcode.com/2023/day/8 */
private object Day8 {

    data class Node(val left: String, val right: String)

    enum class Direction {
        LEFT,
        RIGHT
    }

    fun partOne(): Int {
        val directions = mutableListOf<Direction>()
        val nodes = mutableMapOf<String, Node>()

        readResource("/2023/day08/part1.txt") { line ->
            if (line.isEmpty()) {
                return@readResource
            }

            if (directions.isEmpty()) {
                line.forEach {
                    if (it == 'L') {
                        directions.add(Direction.LEFT)
                    } else {
                        directions.add(Direction.RIGHT)
                    }
                }
            } else {
                val node = NODES.find(line)
                if (node != null) {
                    val id = requireNotNull(node.groups[1]).value
                    val left = requireNotNull(node.groups[2]).value
                    val right = requireNotNull(node.groups[3]).value
                    nodes[id] = Node(left, right)
                }
            }
        }

        var steps = 0
        var next = "AAA"
        while (next != "ZZZ") {
            val direction = directions[steps % directions.size]
            next = when(direction) {
                Direction.LEFT -> requireNotNull(nodes[next]).left
                Direction.RIGHT -> requireNotNull(nodes[next]).right
            }
            steps++
        }
        return steps
    }

    fun partTwo(): Long {
        fun stepsToEnd(directions: List<Direction>, nodes: Map<String, Node>, start: String): Long {
            var steps = 0L
            var next = start
            while (!next.endsWith('Z')) {
                val direction = directions[(steps % directions.size).toInt()]
                next = when(direction) {
                    Direction.LEFT -> requireNotNull(nodes[next]).left
                    Direction.RIGHT -> requireNotNull(nodes[next]).right
                }
                steps++
            }
            return steps
        }

        fun leastCommonMultiple(a: Long, b: Long): Long {
            var ma = a
            var mb = b
            var remainder: Long

            while (mb != 0L) {
                remainder = ma % mb
                ma = mb
                mb = remainder
            }

            return a * b / ma
        }

        val directions = mutableListOf<Direction>()
        val nodes = mutableMapOf<String, Node>()
        val starts = mutableListOf<String>()

        readResource("/2023/day08/part1.txt") { line ->
            if (line.isEmpty()) {
                return@readResource
            }

            if (directions.isEmpty()) {
                line.forEach {
                    if (it == 'L') {
                        directions.add(Direction.LEFT)
                    } else {
                        directions.add(Direction.RIGHT)
                    }
                }
            } else {
                val node = NODES.find(line)
                if (node != null) {
                    val id = requireNotNull(node.groups[1]).value
                    if (id.endsWith('A')) {
                        starts.add(id)
                    }
                    val left = requireNotNull(node.groups[2]).value
                    val right = requireNotNull(node.groups[3]).value
                    nodes[id] = Node(left, right)
                }
            }
        }

        return starts.map { stepsToEnd(directions, nodes, start = it) }
            .reduce { acc, steps -> leastCommonMultiple(acc, steps) }
    }

    private val NODES = Regex("([A-Z0-9]+) = \\(([A-Z0-9]+), ([A-Z0-9]+)\\)")
}

private fun main() {
     println("Part 1 steps: ${Day8.partOne()}")
     println("Part 2 steps: ${Day8.partTwo()}")
}
