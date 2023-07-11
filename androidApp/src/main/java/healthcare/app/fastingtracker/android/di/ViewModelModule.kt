package healthcare.app.fastingtracker.android.di

import healthcare.app.fastingtracker.android.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}

