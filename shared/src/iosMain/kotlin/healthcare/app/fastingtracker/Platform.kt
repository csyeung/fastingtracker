package healthcare.app.fastingtracker

import platform.Foundation.NSDate
import platform.Foundation.now

actual class Platform actual constructor() {
    actual val currentTime: String = NSDate.now().toString()
}
