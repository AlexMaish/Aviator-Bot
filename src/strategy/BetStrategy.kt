package strategy

import bet.BetActive

data class Results(
    val stake: MutableList<Int>,
    val win: MutableList<Double>,
    val accumulation: MutableList<Double>,
    val profit: MutableList<Double>
)
class BetStrategy(betActive: BetActive) {
    var stakeIndex = 0
    var seriesEnded = false
    private val maxRound = 10

    private val baseAmount: Int = betActive.amount

    /**
     * Return fibonacci multipliers starting from [1,2,3,5,...]
     * so stakes will be baseAmount * 1, baseAmount * 2, baseAmount * 3, ...
     */
    private fun fibonacciSeries(): List<Int> {
        val fibonacciList = mutableListOf(1, 2) // start at 1,2
        while (fibonacciList.size < maxRound) {
            val last = fibonacciList[fibonacciList.lastIndex]
            val prev = fibonacciList[fibonacciList.lastIndex - 1]
            fibonacciList.add(last + prev)
        }
        return fibonacciList
    }

    /**
     * Compute stake on demand using baseAmount * fib(index).
     * Also handle maxRound reset logic here.
     */
    fun getAmount(betActive: BetActive): BetActive {
        if (stakeIndex >= maxRound) {
            // reached max, stop playing and mark series ended
            betActive.playing = false
            stakeIndex = 0
            seriesEnded = true
        }
        val fib = fibonacciSeries()
        val idx = stakeIndex.coerceIn(0, fib.lastIndex)
        val stake = baseAmount * fib[idx]
        betActive.amount = stake
        return betActive
    }
}

//class BetStrategy(betActive: BetActive) {
//    var stakeIndex = 0
//    var seriesEnded = false
//    private val maxRound = 10
//    private val results = strategy(betActive)
//
//    private fun fibonacciSeries(): MutableList<Int> {
//        val fibonacciList = mutableListOf(0, 1)
//        val startIndex = 2
//        val localMaxRound = maxRound + startIndex
//        for (i in startIndex..localMaxRound) {
//            val next = fibonacciList[i - 1] + fibonacciList[i - 2]
//            fibonacciList.add(next)
//        }
//        return fibonacciList.subList(startIndex, localMaxRound)
//    }
//
//    private fun strategy(betActive: BetActive): Results {
//        val results = Results(
//            stake = mutableListOf(),
//            win = mutableListOf(),
//            accumulation = mutableListOf(),
//            profit = mutableListOf()
//        )
//        val fib = fibonacciSeries()
//        for (value in fib) {
//            val stake = betActive.amount * value
//            results.stake.add(stake)
//            val win = betActive.target * stake
//            results.win.add(win)
//            val accumulation = if (results.accumulation.size == 0) {
//                stake.toDouble()
//            } else {
//                val lastAccumulation = results.accumulation.last()
//                lastAccumulation + stake
//            }
//            results.accumulation.add(accumulation)
//            val profit = win - accumulation
//            results.profit.add(profit)
//        }
//        return results
//    }
//
//    fun getAmount(betActive: BetActive): BetActive {
//        if (stakeIndex == maxRound) {
//            betActive.playing = false
//            stakeIndex = 0
//            seriesEnded = true
//        }
//        betActive.amount = results.stake[stakeIndex]
//        return betActive
//    }
//}