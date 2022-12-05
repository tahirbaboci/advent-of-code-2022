import java.io.File

class Day05 {

    fun numberOfStacks(lines: List<String>): Int {
        return lines
            .dropWhile { it.contains("[") }
            .first()
            .split(" ")
            .filter { it.isNotBlank() }
            .maxOf { it.toInt() }
    }

    fun populateStack(lines: List<String>, onCharFound: (Int, Char) -> Unit) {
        lines
            .filter { it.contains("[") }
            .map{line ->
                line.mapIndexed { index, char  ->
                    if(char.isLetter()) {
                        val stackNumber = index / 4
                        val value = line[index]
                        onCharFound(stackNumber, value)
                    }
                }
            }
    }

    fun moveCarters(moves: MutableList<Move>, stack: List<ArrayDeque<Char>>): List<ArrayDeque<Char>> {
        moves.map { move ->
            (1 .. move.quantity).forEach{ _ ->
                val removedValue = stack[move.source].removeFirst()
                stack[move.target].addFirst(removedValue)
            }
        }
        return stack
    }

    fun getLastCarters(stack: List<ArrayDeque<Char>>): String {
        return stack.map { it.first() }.toString()
    }
}
data class Move(val quantity: Int, val source: Int, val target: Int) {
    companion object {
        fun of(line: String): Move {
            return line
                .split(" ")
                .map { l ->
                    l.filter { it.isDigit() }
                }
                .filter { it.isNotBlank() }
                .map{ it.toInt() }
                .let { Move(it[0], it[1] - 1, it[2] - 1) }
        }
    }
}

fun main() {
    val moves = mutableListOf<Move>()
    val lines: List<String> = File("inputs/day05.txt").readLines()
    val day05 = Day05()

    val numberOfStacks = day05.numberOfStacks(lines)
    val stacks = List(numberOfStacks) { ArrayDeque<Char>() }


    day05.populateStack(lines) { stackNumber, value ->
        stacks[stackNumber].addLast(value)
    }


    lines.filter { it.contains("move") }.map { moves.add(Move.of(it)) }

//    moves.forEach { println(it) }
//    stacks.forEach { println(it) }

    val newStack = day05.moveCarters(moves, stacks)

//    newStack.forEach{ println(it) }

    println(day05.getLastCarters(newStack))
}