package healthcare.app.fastingtracker

import healthcare.app.fastingtracker.domain.usecase.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Hello"), "Check 'Hello' is mentioned")
    }
}