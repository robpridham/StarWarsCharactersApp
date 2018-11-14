package com.robpridham.starwarschars

import android.app.Application
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.robpridham.starwarschars.ui.viewmodel.SWCViewModelFactory

class StarWarsCharactersApp: Application() {

    private val serviceContainer = ServiceContainer(Handler())

    private val viewModelFactory = SWCViewModelFactory(serviceContainer)

    inline fun <reified T: ViewModel> getActivityScopedViewModel(activity: FragmentActivity): T {
        val getViewModelProviderForActivity = { viewModelFactory: SWCViewModelFactory ->
            ViewModelProviders.of(activity, viewModelFactory)
        }
        return getViewModel(T::class.java, getViewModelProviderForActivity)
    }

    inline fun <reified T : ViewModel> getFragmentScopedViewModel(fragment: Fragment): T {
        val getViewModelProviderForFragment = { viewModelFactory: SWCViewModelFactory ->
            ViewModelProviders.of(fragment, viewModelFactory)
        }
        return getViewModel(T::class.java, getViewModelProviderForFragment)
    }

    fun <T: ViewModel> getViewModel(modelClass: Class<T>,
                                    getViewModelProvider: (SWCViewModelFactory) -> ViewModelProvider): T {
        val viewModelFactory = viewModelFactory
        val viewModelProvider = getViewModelProvider(viewModelFactory)
        return viewModelProvider.get(modelClass)
    }
}