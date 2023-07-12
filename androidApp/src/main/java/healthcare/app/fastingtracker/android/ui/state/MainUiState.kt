package healthcare.app.fastingtracker.android.ui.state

import healthcare.app.fastingtracker.domain.model.FastingResult

/**
 * @author Jonathan YEUNG
 * @since 2023-04-30
 * Main UI State Flow
 */
sealed class MainUiState {
    // Beginning State
    object Empty: MainUiState()
    // TODO: Rework to check on initial data
    object Loading: MainUiState()
    // Start State
    class Start(val data: FastingResult?): MainUiState()
    // Return from Start
    object Fasting: MainUiState()
    // Stop State
    object Stop: MainUiState()
    // Any error will be handled here
    class Error(val message: String): MainUiState()
}
