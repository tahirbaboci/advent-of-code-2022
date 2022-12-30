package day02
import java.io.File
class Day02 {
    private val movePoints = mapOf(
        Move.X to 1,
        Move.Y to 2,
        Move.Z to 3
    )
    private val gamePoints = mapOf(
        Game.Win to 6,
        Game.Draw to 3,
        Game.Lost to 0
    )

    private val rulesPart2 = mapOf(
        (Move.X to Move.A) to (Move.Z to Game.Lost),
        (Move.X to Move.B) to (Move.X to Game.Lost),
        (Move.X to Move.C) to (Move.Y to Game.Lost),
        (Move.Y to Move.A) to (Move.X to Game.Draw),
        (Move.Y to Move.B) to (Move.Y to Game.Draw),
        (Move.Y to Move.C) to (Move.Z to Game.Draw),
        (Move.Z to Move.A) to (Move.Y to Game.Win),
        (Move.Z to Move.B) to (Move.Z to Game.Win),
        (Move.Z to Move.C) to (Move.X to Game.Win),
    )

    private val rules = mapOf(
        (Move.X to Move.A) to Game.Draw,
        (Move.X to Move.B) to Game.Lost,
        (Move.X to Move.C) to Game.Win,
        (Move.Y to Move.A) to Game.Win,
        (Move.Y to Move.B) to Game.Draw,
        (Move.Y to Move.C) to Game.Lost,
        (Move.Z to Move.A) to Game.Lost,
        (Move.Z to Move.B) to Game.Win,
        (Move.Z to Move.C) to Game.Draw,
    )

    fun play(lines: List<String>): Int {
        val listOfMovePairs = lines.map { l ->
            val moves = l.split(" ")
            val theMoves = moves.map { m ->
                when (m) {
                    "X" -> Move.X
                    "Y" -> Move.Y
                    "Z" -> Move.Z
                    "A" -> Move.A
                    "B" -> Move.B
                    "C" -> Move.C
                    else -> Move.NONE
                }
            }
            Pair(theMoves[0], theMoves[1])
        }
        return calculatePointsPart2(listOfMovePairs)
    }
    private fun calculatePointsPart1(moves: List<Pair<Move, Move>>): Int {
        // Part 1
        val gp = moves.map { m ->
            val rule = rules[(m.second to m.first)]
           gamePoints[rule] ?: 0
        }.reduce { acc, i -> acc.plus(i) }

        val mp = moves.map { m ->
            movePoints[m.second] ?: 0
        }.reduce { acc, i -> acc.plus(i) }

        return gp.plus(mp)
    }

    private fun calculatePointsPart2(moves: List<Pair<Move, Move>>): Int {
        // Part 2
        val gpPart2 = moves.map { m ->
            val rule = rulesPart2[(m.second to m.first)]
            val a = gamePoints[rule?.second] ?: 0
            a
        }.reduce { acc, i -> acc.plus(i) }

        val mp2 = moves.map { m ->
            val rule = rulesPart2[(m.second to m.first)]
            movePoints[rule?.first] ?: 0
        }.reduce { acc, i -> acc.plus(i) }

        return gpPart2.plus(mp2)
    }

}
sealed class Game {
    object Lost: Game()
    object Draw: Game()
    object Win: Game()
}
sealed class Move {
    object X: Move() { override fun toString(): String = "X" }
    object Y: Move() { override fun toString(): String = "Y" }
    object Z: Move() { override fun toString(): String = "Z" }
    object A: Move() { override fun toString(): String = "A" }
    object B: Move() { override fun toString(): String = "B" }
    object C: Move() { override fun toString(): String = "C" }
    object NONE: Move() { override fun toString(): String = "NONE" }
}
fun main() {
    val lines: List<String> = File("inputs/day02.txt").readLines()
    println(Day02().play(lines))
}