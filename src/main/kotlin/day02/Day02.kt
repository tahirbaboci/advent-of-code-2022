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
        return calculatePoints(listOfMovePairs)
    }
    private fun calculatePoints(moves: List<Pair<Move, Move>>): Int {
        val gp = moves.map { m ->
            val rule = rules[(m.second to m.first)]
           gamePoints[rule] ?: 0
        }.reduce { acc, i -> acc.plus(i) }
        val mp = moves.map { m ->
            movePoints[m.second] ?: 0
        }.reduce { acc, i -> acc.plus(i) }
        return gp.plus(mp)
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