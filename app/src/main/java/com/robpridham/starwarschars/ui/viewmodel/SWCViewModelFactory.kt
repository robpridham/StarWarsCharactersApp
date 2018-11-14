package com.robpridham.starwarschars.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robpridham.starwarschars.ServiceContainer

class SWCViewModelFactory(private val serviceContainer: ServiceContainer): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            HomeScreenViewModel::class.java -> return HomeScreenViewModel(serviceContainer.starWarsService) as T
            DetailScreenViewModel::class.java -> return DetailScreenViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class ${modelClass.name}")
    }
}