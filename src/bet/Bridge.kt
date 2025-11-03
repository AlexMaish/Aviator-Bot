package bet

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver

open class BridgeLeft {
    fun leftInputBox(bot: ChromeDriver, betAmount: Int) {
        try {
            val amount = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/div[1]/app-spinner/div/div[2]/input"
            ))
            amount.sendKeys(Keys.CONTROL, "a")
            amount.sendKeys(Keys.DELETE)
            amount.sendKeys(betAmount.toString())
            amount.sendKeys(Keys.RETURN)
            Thread.sleep(2000)
        } catch (e: Exception) {
            return
        }
    }

    fun leftButton(bot: ChromeDriver) {
        try {
            val button = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/div[2]/button"
            ))
            button.click()
            Thread.sleep(2000)
        } catch (e: Exception) {
            return
        }
    }

    fun leftBetSlotText(bot: ChromeDriver): String {
        try {
            val slot = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/div[2]/button/span/label[1]"
            ))
            return slot.text.lowercase()
        } catch (e: Exception) {
            return ""
        }
    }

    fun leftBetSlotWaitingNextRound(bot: ChromeDriver): Boolean {
        try {
            bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button/label"
            ))
            return true
        } catch (e: Exception) {
            return leftBetSlotText(bot) == "cancel"
        }
    }
}

open class BridgeText: BridgeLeft() {
    fun multiplierAmount(bot: ChromeDriver): Double {
        try {
            val amount = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button/span/label[2]/span[1]"
            ))
            val amountText = amount.text
            return amountText.toDouble()
        } catch (e: Exception) {
            return 0.0
        }
    }

    fun multiplierValue(bot: ChromeDriver): Double {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/div[1]"
                ))
                val multiplierText = aviator.text
                val multiplierTextStrip = multiplierText.removeSuffix("x")
                return multiplierTextStrip.toDouble()
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return 0.0
    }

    fun playerNumber(bot: ChromeDriver): Int {
        return try {
            val players = bot.findElement(By.xpath("//span[contains(@class, 'bets-count')]"))
            val countText = players.text
            val totalPlayers = countText.split("/")[1].toInt()
            totalPlayers
        } catch (e: Exception) {
            println(e.message)
            0
        }
    }

    fun balanceValue(bot: ChromeDriver): Double {
        try {
            val amount = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[1]/app-header/div/div[2]/div[1]/span[1]"
            ))
            val amountText = amount.text
            val amountStr = amountText.replace(",", "")
            return amountStr.toDouble()
        } catch (e: Exception) {
            return 0.0
        }
    }

    fun autoCashOutState(bot: ChromeDriver): Boolean {
        try {
            val cashOut = bot.findElement(By.xpath(
                "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div/div[2]/div[1]/app-ui-switcher/div"
            ))
            val cashOutClass = cashOut.getDomAttribute("class") ?: ""
            return cashOutClass == "input-switch"
        } catch (e: Exception) {
            return false
        }
    }
}

open class BridgeClicks: BridgeText() {
    fun aviatorLink(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/div[1]/div[1]/div/header/section[1]/article[2]/a[4]"
                ))
                aviator.click()
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }

    fun autoCashOutTab(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[1]/app-navigation-switcher/div/button[2]"
                ))
                aviator.click()
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }

    fun autoCashOut(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div/div[2]/div[1]/app-ui-switcher/div/span"
                ))
                aviator.click()
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }
}

open class BridgeInputs: BridgeClicks() {
    fun loginForm(bot: ChromeDriver, phoneNumber: String, password: String) {
        val phoneInput = bot.findElement(By.xpath(
            "/html/body/div[1]/div[1]/div/header/section[1]/article[3]/section/article/div[1]/form/input[1]"
        ))
        phoneInput.sendKeys(phoneNumber)
        val passwordInput = bot.findElement(By.xpath(
            "/html/body/div[1]/div[1]/div/header/section[1]/article[3]/section/article/div[1]/form/input[2]"
        ))
        passwordInput.sendKeys(password)
        passwordInput.sendKeys(Keys.RETURN)
    }

    fun autoCashOutInput(bot: ChromeDriver, targetMultiplier: Double): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div/div[2]/div[2]/div/app-spinner/div/div[2]/input"
                ))
                aviator.sendKeys(Keys.CONTROL, "a")
                aviator.sendKeys(Keys.DELETE)
                aviator.sendKeys(targetMultiplier.toString())
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }
}

open class Bridge: BridgeInputs() {
    val pop = PopupsHandler()
    private var betting = true
    private var lastMultiplier = 0.0
    private val betManager = BetManager()
    private var startBetting = false
    private var targetMultiplier = 0.0
    private var lastBalanceBeforeBet = 0.0
    private var currentBetAmount = 0
    private var stageTimeoutDetected = false
    private var retrySameBet = false // New flag to track if we need to retry the same bet

    fun startBot(link: String, targetMultiplier: Double): ChromeDriver {
        val bot = ChromeDriver()
        bot.manage().window().maximize()
        bot.get(link)
        this.targetMultiplier = targetMultiplier
        return bot
    }

    fun placingBets(bot: ChromeDriver): Boolean {
        println("[LIVE]: betting started...")
        while (betting) {
            val multiplier = multiplierValue(bot)
            if (multiplier == 0.0) {
                betting = false
            } else {
                if (multiplier != lastMultiplier) {
                    println("[MULTIPLIER]: $multiplier")
                    lastMultiplier = multiplier
                    betManager.placeBet(playerNumber(bot))
                    if (insufficientBalance(bot)) {
                        return balanceValue(bot) > 10.0
                    }
                    betPreparation(bot)
                }
            }
        }
        return false
    }

    fun autoCashOutConfiguration(bot: ChromeDriver): Boolean {
        val cashedOutTab = autoCashOutTab(bot)
        if (!cashedOutTab) {
            return false
        }
        Thread.sleep(1000)
        val cashedOut = autoCashOut(bot)
        if (!cashedOut) {
            return false
        }
        Thread.sleep(1000)
        val cashedOutInput = autoCashOutInput(bot, targetMultiplier)
        return cashedOutInput
    }

    private fun detectStageTimeout(bot: ChromeDriver): Boolean {
        val currentBalance = balanceValue(bot)

        // If balance didn't change after placing bet, stage timeout occurred
        if (currentBalance >= lastBalanceBeforeBet) {
            println("[STAGE TIMEOUT DETECTED]: Balance didn't reduce after placing bet")
            println("[BALANCE CHECK]: Before: $lastBalanceBeforeBet, After: $currentBalance, Bet Amount: $currentBetAmount")
            return true
        }

        return false
    }

    private fun handleStageTimeout(bot: ChromeDriver, currentMultiplier: Double) {
        println("[HANDLING STAGE TIMEOUT]: Multiplier = $currentMultiplier")

        if (currentMultiplier < 3.0) {
            // Multiplier < 3.0: Bet the same amount again without incrementing series
            println("[STAGE TIMEOUT STRATEGY]: Multiplier < 3.0 - Will retry same bet amount: $currentBetAmount")
            retrySameBet = true
            // Don't change stake index - we'll bet the same amount again
        } else {
            // Multiplier >= 3.0: Reset the series
            println("[STAGE TIMEOUT STRATEGY]: Multiplier >= 3.0 - Resetting series")
            betManager.resetStakeIndex()
            retrySameBet = false
        }

        stageTimeoutDetected = true
    }

    private fun betConfirmationResults(bot: ChromeDriver) {
        while (multiplierValue(bot) == lastMultiplier) {
            continue
        }

        val currentMultiplier = multiplierValue(bot)

        // Check for stage timeout first
        if (stageTimeoutDetected) {
            println("[STAGE TIMEOUT RESULT]: Bet was not placed in previous round")
            stageTimeoutDetected = false

            // If we're retrying the same bet, don't change anything - we'll use the same amount
            if (retrySameBet) {
                println("[RETRYING SAME BET]: Will use same amount in next round")
                // Stake index remains the same for the retry
            }

            betManager.betActive.active = false
            return
        }

        // If we placed a bet (betActive.active was true), then we evaluate the result
        if (betManager.betActive.active) {
            if (currentMultiplier < targetMultiplier) {
                // Loss - multiplier didn't reach target
                println("=======You have lost bet======" +
                        "\n| Target multiplier: ${betManager.betActive.target} |" +
                        "\n| Max multiplier: $currentMultiplier |" +
                        "\n| Bet Amount: KSh ${betManager.betActive.amount} |" +
                        "\n| Balance: KSh ${balanceValue(bot)} |")

                // Only increment if we're not in retry mode
                if (!retrySameBet) {
                    betManager.incrementStakeIndex()
                } else {
                    println("[RETRY LOST]: Now continuing with normal series progression")
                    retrySameBet = false
                    betManager.incrementStakeIndex()
                }
            } else {
                // Win - multiplier reached or exceeded target
                println("=======You have won bet======" +
                        "\n| Target multiplier: ${betManager.betActive.target} |" +
                        "\n| Max multiplier: $currentMultiplier |" +
                        "\n| Bet Amount: KSh ${betManager.betActive.amount} |" +
                        "\n| Balance: KSh ${balanceValue(bot)} |")
                betManager.resetStakeIndex()
                retrySameBet = false
            }
        } else {
            // This is a genuine stage timeout - no bet was placed for this round
            println("=======Stage timeout - No bet placed======" +
                    "\n| Target multiplier: ${betManager.betActive.target} |" +
                    "\n| Max multiplier: $currentMultiplier |" +
                    "\n| Balance: KSh ${balanceValue(bot)} |")
        }
    }

    private fun betSlotRunning(bot: ChromeDriver) {
        var betStarted = false
        while (leftBetSlotText(bot) == "cash out" || leftBetSlotWaitingNextRound(bot)) {
            if (leftBetSlotText(bot) == "cash out") {
                if (!betStarted) {
                    println("[BET STARTED]: Bet is now active in the current round")
                    startBetting = true
                    betStarted = true

                    // Check for stage timeout after bet should have been placed
                    if (detectStageTimeout(bot)) {
                        handleStageTimeout(bot, lastMultiplier)
                        return
                    }
                }

                if (startBetting) {
                    val expectedIncome = betManager.betActive.amount * targetMultiplier
                    if (multiplierAmount(bot) > expectedIncome) {
                        if (!autoCashOutState(bot)) {
                            println("[LATE CASH OUT]: auto cash out took too long, cashing out manually")
                            leftButton(bot)
                        }
                    }
                }
            }
        }
    }

    private fun leftBet(bot: ChromeDriver) {
        // Store balance and bet amount before placing bet for stage timeout detection
        lastBalanceBeforeBet = balanceValue(bot)

        // If we're retrying the same bet, use the stored amount instead of getting new amount from strategy
        if (retrySameBet) {
            betManager.betActive.amount = currentBetAmount
            println("[RETRYING BET]: Using same amount: KSh $currentBetAmount")
        } else {
            currentBetAmount = betManager.betActive.amount
        }

        leftInputBox(bot, currentBetAmount)
        leftButton(bot)

        println("[BET PLACED]: " +
                "\n| Target ${betManager.betActive.target} |" +
                "\n| Amount: KSh $currentBetAmount |" +
                "\n| Balance Before: KSh $lastBalanceBeforeBet |" +
                "\n| Current Balance: KSh ${balanceValue(bot)} |")

        betSlotRunning(bot)

        // If stage timeout was detected during betSlotRunning, exit early
        if (stageTimeoutDetected) {
            return
        }

        while (leftBetSlotText(bot) == "cash out") {
            println("[RECONNECTING]: the program broke before cash out, reconnecting...")
            betSlotRunning(bot)
        }

        betConfirmationResults(bot)
        betManager.betActive.active = false
        startBetting = false
    }

    private fun betPreparation(bot: ChromeDriver) {
        if (betManager.betActive.active) {
            betManager.betActive.balance = balanceValue(bot)
            while (!autoCashOutState(bot)) {
                println("[AUTO CASH OUT OFF]: switching auto cash out back on")
                val status = autoCashOutConfiguration(bot)
                if (status) {
                    continue
                }
                return
            }
            if (autoCashOutState(bot)) {
                println("[AUTO CASH OUT ON]: placing bet")
            }
            leftBet(bot)
        }
    }

    private fun insufficientBalance(bot: ChromeDriver): Boolean {
        val currentBalance = balanceValue(bot)
        println("[CURRENT BALANCE]: KSh $currentBalance")
        return betManager.betActive.amount > currentBalance
    }

    private fun switchOuterFrame(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.xpath(
                    "/html/body/div[1]/div[1]/section/section/section/section/article[2]/iframe"
                ))
                bot.switchTo().frame(aviator)
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }

    private fun switchInnerFrame(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            try {
                val aviator = bot.findElement(By.id(
                    "gameFrame"
                ))
                bot.switchTo().frame(aviator)
                return true
            } catch (e: Exception) {
                timer++
                Thread.sleep(1000)
            }
        }
        return false
    }

    fun switchAviatorFrame(bot: ChromeDriver): Boolean {
        if (!switchOuterFrame(bot)) {
            return false
        }
        if (!switchInnerFrame(bot)) {
            return false
        }
        return true
    }
}