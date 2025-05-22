import bet.Aviator

fun main() {
    val aviator = Aviator(
        phoneNumber = "0795301955",
        password = "50286186!",
        targetMultiplier = 3.0
    )
    aviator.connect()
}