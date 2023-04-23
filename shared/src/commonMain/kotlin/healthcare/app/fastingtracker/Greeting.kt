package healthcare.app.fastingtracker

class Greeting(private val platform: Platform) {
    fun greet(): String {
        return platform.currentTime
    }
}