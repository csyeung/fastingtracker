package healthcare.app.fastingtracker

import platform.Foundation.NSDate
import platform.Foundation.now
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val currentTime: String = NSDate.now().toString()
}

actual fun getPlatform(): Platform = IOSPlatform()