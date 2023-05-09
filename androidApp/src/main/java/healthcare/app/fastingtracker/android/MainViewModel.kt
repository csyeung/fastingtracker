package healthcare.app.fastingtracker.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import healthcare.app.fastingtracker.android.ui.state.MainUiState
import healthcare.app.fastingtracker.domain.model.FastingRecord
import healthcare.app.fastingtracker.domain.usecase.Greeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val greeting: Greeting) : ViewModel() {
    private var _uiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    internal val uiState: StateFlow<MainUiState>
        get() = _uiState.asStateFlow()

    private var _record = MutableStateFlow<FastingRecord?>(null)
    internal val record: StateFlow<FastingRecord?>
        get() = _record.asStateFlow()

    internal fun fetchData() {
        viewModelScope.launch {
            if (greeting.hasFastingRecord()) {
                _uiState.value = MainUiState.Fasting
                updateFasting()
            } else {
                _uiState.value = MainUiState.Start(null)
            }
        }
    }

    internal fun updateFasting() {
        viewModelScope.launch {
            greeting.updateFastingRecord.collect {
                _record.value = it
            }
        }
    }

    internal fun startFasting() {
        // TODO: Reserve when loading required anytime
//        _uiState.value = MainUiState.Loading

        _uiState.value = MainUiState.Fasting
        greeting.startFasting()
    }

    internal fun stopFasting() {
        // TODO: Change to stop process
        _uiState.value = MainUiState.Start(null)
        greeting.stopFasting()
    }
}
