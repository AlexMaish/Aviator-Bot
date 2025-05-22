package bet

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver

class PopupsHandler {
    private fun newsCanceled(bot: ChromeDriver): Boolean {
        try {
            val cancelButton = bot.findElement(By.id("onesignal-slidedown-cancel-button"))
            cancelButton.click()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun newsAlert(bot: ChromeDriver): Boolean {
        var timer = 0
        while (timer < 10) {
            if (newsCanceled(bot)) {
                return true
            }
            timer += 1
            Thread.sleep(1000)
        }
        return false
    }
}