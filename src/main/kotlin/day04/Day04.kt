package day04

import java.io.File
class Day04 {
    fun printAssignments(lines: List<String>) {
        val maxNumberOfAssignments = lines.flatMap { l ->
            l.split(",")
                .flatMap { p ->
                    p.split("-").map { it.toInt() }
                }
        }.maxOf { it }

        val a = lines.map { l ->
            l.split(",").joinToString { p ->
                val a = p.split("-").map { it.toInt() }.toIntArray()
                val b = (a[0] .. a[1]).map { it }
                (1..maxNumberOfAssignments).joinToString {
                    if (b.contains(it)) {
                        "$it"
                    } else {
                        "."
                    }
                }.plus("\t$p").plus("\n")
            }.replace(",", "").replace(" ", "")
        }
        a.forEach { println(it) }
    }

    fun extractAssignmentsForEachPairPart1(lines: List<String>) {
        val numberOfAssignmentsThatFullyIntersect = lines.map { line ->
            val listOfAssignments = line.split(",").map { p ->
                val assignments = p.split("-").map { it.toInt() }
                (assignments.first() .. assignments.last()).map { it }
            }
            val checkIfSecondAssignmentContainsAll = listOfAssignments.first().all { listOfAssignments.last().contains(it) }
            val checkIfFirstAssignmentContainsAll = listOfAssignments.last().all { listOfAssignments.first().contains(it) }

            checkIfSecondAssignmentContainsAll || checkIfFirstAssignmentContainsAll
        }.filter { it }.size

        println(numberOfAssignmentsThatFullyIntersect)
    }
    fun extractNumberOfAllPairsThatOverlapPart2(lines: List<String>) {
        val numberOfAssignmentsThatFullyIntersect = lines.map { line ->
            val listOfAssignments = line.split(",").map { p ->
                val assignments = p.split("-").map { it.toInt() }
                (assignments.first() .. assignments.last()).map { it }
            }
            val checkIfSecondAssignmentContainsAll = listOfAssignments.first().intersect(listOfAssignments.last().toSet())

            checkIfSecondAssignmentContainsAll.isNotEmpty()
        }.filter { it }.size

        println(numberOfAssignmentsThatFullyIntersect)
    }
}

fun main() {
    val lines: List<String> = File("inputs/day04.txt").readLines()
    Day04().extractNumberOfAllPairsThatOverlapPart2(lines)
}