package healthcare.app.fastingtracker

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    override val currentTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

actual fun getPlatform(): Platform = AndroidPlatform()