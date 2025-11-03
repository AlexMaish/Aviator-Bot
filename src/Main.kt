import bet.Aviator

fun main() {
    val aviator = Aviator(
        phoneNumber = "0726155281",
        password = "Cabbages23",
        targetMultiplier = 3.0
    )
    aviator.connect()
}