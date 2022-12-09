package day01

import java.io.File

class Day01 {
    fun arrangeElvesWithItems(lines: List<String>): List<Elves> {
        var elvesId = 0
        val elves = mutableListOf<Elves>()
        val items = mutableListOf<Int>()
        lines.map { l ->
            if(l.isNotBlank()){
                items.add(l.toInt())
            } else {
                elvesId++
                elves.add(Elves(elvesId, items.sum()))
                items.clear()
            }
        }
        if(lines.last().isNotBlank()){
            elvesId++
            elves.add(Elves(elvesId, lines.last().toInt()))
        }
        return elves
    }
}

data class Elves(val id: Int, val totalCalories: Int)



fun main() {

    val lines: List<String> = File("inputs/day01.txt").readLines()
    val elves = Day01().arrangeElvesWithItems(lines)
    elves.forEach { println(it) }
    val max = elves.maxWithOrNull(compareBy { it.totalCalories })
    println(max?.totalCalories)
}