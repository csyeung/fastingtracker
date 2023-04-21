package healthcare.app.fastingtracker

interface Platform {
    val name: String
    val currentTime: String
}

expect fun getPlatform(): Platform