package dam.a51728.eventLogProcessing

sealed class Event(val username: String, val timestamp: Long){
    override fun toString(): String {
        return when (this){
            is Login -> "Login(username=$username, timestamp=$timestamp)"
            is Logout -> "Logout(username=$username, timestamp=$timestamp)"
            is Purchase -> "Purchase(username=$username, amount=$amount, timestamp=$timestamp)"
        }
    }
    class Login(username: String, timestamp: Long): Event(username, timestamp)
    class Purchase(username: String, val amount: Double, timestamp: Long): Event(username, timestamp)
    class Logout(username: String, timestamp: Long): Event(username, timestamp)
}