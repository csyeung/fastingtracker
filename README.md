# Fasting Tracker

This is a Kotlin [Multiplatform project](https://kotlinlang.org/docs/reference/multiplatform.html) trying to share as much code as possible between Android and iOS.

In particular, this project uses a Clean Architecture approach, sharing the complete `data` and `domain` layer, as also the `presenter` from the `presentation` layer. Each platform currently only implements the `View` interface and adds a native UI to visualize the data from the `presenter`.

Follow my blog (Japanese) [here](https://jonathan-yeung.hatenablog.com/) for the development story. 
