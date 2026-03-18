package dam.a51728.eventLogProcessing

import java.math.BigDecimal
import java.math.RoundingMode

fun List<Event>.filterByUser(username: String): List<Event> {
    val res: ArrayList<Event> = ArrayList()
    for(item in this){
        if(item.username == username) res.add(item)
    }
    return res
}

fun List<Event>.totalSpent(username: String): Double {
    return this.filterIsInstance<Event.Purchase>()
            .filter { ev -> ev.username == username }
            .sumOf { purchaseEvent -> purchaseEvent.amount}
}

fun processEvents(events: List<Event>, handler: (Event) -> Unit) {
    for(item in events) handler.invoke(item)
}

fun main(){
    val events = listOf (
        Event.Login ("alice", 1_000 ) ,
        Event.Purchase ("alice", 49.99 , 1_100 ) ,
        Event.Purchase ("bob", 19.99 , 1_200 ) ,
        Event.Login ("bob", 1_050 ) ,
        Event.Purchase ("alice", 15.00 , 1_300 ) ,
        Event.Logout ("alice", 1_400 ) ,
        Event.Logout ("bob", 1_500 )
    )

    println()
    processEvents(events){event ->
        when(event){
            is Event.Login -> println(   "[Login]    ${event.username} logged in at t = ${event.timestamp}")
            is Event.Purchase -> println("[Purchase] ${event.username} made a purchase of $${event.amount} at t = ${event.timestamp}")
            is Event.Logout -> println(  "[Logout]   ${event.username} logged out at t = ${event.timestamp}")
        }
    }
    println()
    println("Total spent by alice:  \$%.2f".format(events.totalSpent("alice")))
    println("Total spent by bob:  \$%.2f".format(events.totalSpent("bob")))
    println()
    println("Events for alice: ")
    for (event in events.filterByUser("alice")){
        println("\t$event")
    }
}