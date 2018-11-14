package com.robpridham.starwarschars.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.robpridham.starwarschars.data.MultiPersonResponse
import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.data.StarWarsService
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Result
import com.robpridham.starwarschars.util.Success

class HomeScreenViewModel(
    private val starWarsService: StarWarsService): ViewModel() {

    private var allRetrievedPeople: MutableList<PersonData> = mutableListOf()

    val retrievedPeople: List<PersonData> get() = allRetrievedPeople.toList()

    private var loadMoreUrl: String? = null

    val canLoadMorePeople: Boolean get() { return loadMoreUrl != null }

    fun requestAllPeopleInitialLoad(onResult: (Result<Unit, Error>)->Unit) {
        starWarsService.getAllPeopleInitialData { result ->
            when (result) {
                is Success -> {
                    processMultiPersonResponse(result.payload, false)
                    onResult(Success(Unit))
                }
                is Failure -> onResult(Failure(result.error))
            }
        }
    }

    fun requestAllPeopleMoreData(onResult: (Result<Boolean, Error>)->Unit) {
        loadMoreUrl?.let { url ->
            starWarsService.getAllPeopleMoreData(url) { result ->
                when (result) {
                    is Success -> {
                        processMultiPersonResponse(result.payload, true)
                        onResult(Success(loadMoreUrl != null))
                    }
                    is Failure -> onResult(Failure(result.error))
                }
            }
        } ?: onResult(Failure(Error("No more data")))
    }

    private fun processMultiPersonResponse(response: MultiPersonResponse, appendToExisting: Boolean) {
        if (appendToExisting) {
            allRetrievedPeople.addAll(response.results)
        } else {
            allRetrievedPeople = response.results.toMutableList()
        }
        loadMoreUrl = response.next
    }
}