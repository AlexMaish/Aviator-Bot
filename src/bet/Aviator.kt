package bet

import org.openqa.selenium.chrome.ChromeDriver

class Aviator(
    val phoneNumber: String,
    val password: String,
    val targetMultiplier: Double
): Bridge() {
    private val webLink: String = "https://mozzartbet.co.ke/en#/betting"

    private fun login(bot: ChromeDriver): Boolean {
        var timer = 0
        var loggedIn = false
        while (timer < 10) {
            try {
                loginForm(bot, phoneNumber, password)
                loggedIn = true
                break
            } catch (e: Exception) {
                println("Login failed")
                timer += 1
                println("[TIMER]: $timer")
                Thread.sleep(100000)
            }
        }
        if (loggedIn) {
            println("[Logged In]: successfully")
            return true
        } else {
            return false
        }
    }

    fun connect() {
        try {
            val bot: ChromeDriver = startBot(webLink, targetMultiplier)
            println("[CHROME CONNECTED]: successfully")
            val canceled = pop.newsAlert(bot)
            if (!canceled) {
                bot.close()
                this.connect()
            }
            val loggedIn = login(bot)
            if (!loggedIn) {
                bot.close()
                this.connect()
            }
            Thread.sleep(5000)
            val linked = aviatorLink(bot)
            if (!linked) {
                bot.close()
                this.connect()
            }
            Thread.sleep(30000)
            val switched = switchAviatorFrame(bot)
            if (!switched) {
                bot.close()
                this.connect()
            }
            val autoCashSet = autoCashOutConfiguration(bot)
            if (!autoCashSet) {
                bot.close()
                this.connect()
            }
            Thread.sleep(1000)
            val restart = placingBets(bot)
            bot.close()
            if (restart) {
                println("[PROGRAM RESTART]")
                this.connect()
            }
        } catch (e: Exception) {
            println("[CHROME CONNECTION ERROR]: ${e.message}")
            return
        }
    }
}
