package healthcare.app.fastingtracker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual class Platform actual constructor() {
    companion object {
        private const val DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm"
    }

    actual val currentTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
        DEFAULT_FORMATTER))
}

actual typealias CommonParcelize = Parcelize
actual typealias CommonParcelable = Parcelable