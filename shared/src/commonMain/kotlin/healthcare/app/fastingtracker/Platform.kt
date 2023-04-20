package healthcare.app.fastingtracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform