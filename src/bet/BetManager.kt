package bet

import strategy.BetStrategy

data class BetActive(
    var active: Boolean,
    var amount: Int,
    val target: Double,
    var playing: Boolean,
    var balance: Double
)

class BetManager {
    var betActive = BetActive(
        active = false,
        amount = 5,
        target = 3.0,
        playing = false,
        balance = 0.0
    )
    private val idealPlayers: MutableList<Int> = mutableListOf()
    private val idealPlayersRoundsTarget = 3
    private val idealPlayersTarget = 5000
    private val betStrategy = BetStrategy(betActive)

    private fun idealBetNumbers(players: Int) {
        if (idealPlayers.size == idealPlayersRoundsTarget) {
            if (betActive.playing) {
                if (betStrategy.seriesEnded) {
                    betActive.playing = false
                    betStrategy.seriesEnded = false
                } else if (betStrategy.stakeIndex == 0) {
                    betActive.playing = false
                }
            }
            if (!betActive.playing) {
                val totalPlayers = idealPlayers.reduce { acc, i -> acc + i }
                println("[TOTAL PLAYERS]: in the last 5 rounds: $totalPlayers")
                val average = totalPlayers / idealPlayersRoundsTarget
                println("[AVERAGE PLAYERS]: in the last 5 rounds: $average")
                if (average >= idealPlayersTarget) {
                    println("[IDEAL PLAYERS]: ideal target players of 5000 reached")
                    println("playing started...")
                    betActive.playing = true
                }
            }
            idealPlayers.removeFirst()
        }
        idealPlayers.add(players)
        println("[PLAYERS]: in current round: $players")
    }

    fun incrementStakeIndex() {
        betStrategy.stakeIndex++
    }

    fun resetStakeIndex() {
        betStrategy.stakeIndex = 0
    }

    fun placeBet(players: Int) {
        idealBetNumbers(players)
        if (betActive.playing && !betActive.active) {
            betActive = betStrategy.getAmount(betActive)
            betActive.active = true
        }
    }
}