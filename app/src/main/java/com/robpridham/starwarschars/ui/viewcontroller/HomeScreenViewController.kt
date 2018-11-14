package com.robpridham.starwarschars.ui.viewcontroller

import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.ui.view.HomeScreenView
import com.robpridham.starwarschars.ui.viewmodel.HomeScreenViewModel
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Success

class HomeScreenViewController(
    private val homeScreenViewModel: HomeScreenViewModel,
    private val view: HomeScreenView,
    showDetailScreenCallback: (PersonData) -> Unit
) {
    init {
        view.retryListener = {
            fetchPeople()
        }
        view.loadMoreRequestListener = {
            if (homeScreenViewModel.canLoadMorePeople) {
                view.showLoadingMoreInList()
                fetchMorePeople()
            }
        }
        view.itemPressedListener = { person ->
            showDetailScreenCallback(person)
        }

        if (homeScreenViewModel.retrievedPeople.isEmpty()) {
            fetchPeople()
        } else {
            view.showContent(homeScreenViewModel.retrievedPeople, homeScreenViewModel.canLoadMorePeople)
        }
    }

    private fun fetchPeople() {
        view.showLoadingSpinner()
        homeScreenViewModel.requestAllPeopleInitialLoad { result ->
            when (result) {
                is Success -> view.showContent(homeScreenViewModel.retrievedPeople, homeScreenViewModel.canLoadMorePeople)
                is Failure -> view.showRetry()
            }
        }
    }

    private fun fetchMorePeople() {
        homeScreenViewModel.requestAllPeopleMoreData { result ->
            when (result) {
                is Success -> {
                    view.updateContent(homeScreenViewModel.retrievedPeople, homeScreenViewModel.canLoadMorePeople)
                }
                is Failure -> {
                    //do nothing
                }
            }
        }
    }

    fun onRemove() {
        view.onRemove()
    }
}