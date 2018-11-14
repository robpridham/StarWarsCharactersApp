package com.robpridham.starwarschars.ui.viewmodel

import com.nhaarman.mockitokotlin2.*
import com.robpridham.starwarschars.data.MultiPersonResponse
import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.data.StarWarsService
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Result
import com.robpridham.starwarschars.util.Success
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeScreenViewModelTest {

    companion object {
        private val EXAMPLE_SOURCE_LIST = listOf(
            PersonData("name1", "height1", "mass1", "created1"),
            PersonData("name2", "height2", "mass2", "created2"),
            PersonData("name3", "height3", "mass3", "created3")
        )
    }

    @Mock private lateinit var mockStarWarsService: StarWarsService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `initial state is empty`() {
        val model = HomeScreenViewModel(mockStarWarsService)
        assertTrue(model.retrievedPeople.isEmpty())
    }

    @Test
    fun `loading initial data makes correct call to network, handles failure result, notifies caller`() {
        val model = HomeScreenViewModel(mockStarWarsService)

        var failureCalledOnCaller = false
        model.requestAllPeopleInitialLoad {
            if (it is Failure) {
                failureCalledOnCaller = true
            }
        }

        val networkCaptor = argumentCaptor<(Result<MultiPersonResponse, Error>)->Unit>()
        verify(mockStarWarsService).getAllPeopleInitialData(networkCaptor.capture())
        networkCaptor.firstValue.invoke(Failure(Error()))

        assertTrue(failureCalledOnCaller)
        assertTrue(model.retrievedPeople.isEmpty())
    }

    @Test
    fun `loading initial data makes correct call to network, handles success result, notifies caller`() {
        val model = HomeScreenViewModel(mockStarWarsService)
        val mockNetworkResponse = mock<MultiPersonResponse>()
        whenever(mockNetworkResponse.results).thenReturn(EXAMPLE_SOURCE_LIST)

        var successCalledOnCaller = false
        model.requestAllPeopleInitialLoad {
            if (it is Success) {
                successCalledOnCaller = true
            }
        }

        val networkCaptor = argumentCaptor<(Result<MultiPersonResponse, Error>)->Unit>()
        verify(mockStarWarsService).getAllPeopleInitialData(networkCaptor.capture())
        networkCaptor.firstValue.invoke(Success(mockNetworkResponse))

        assertTrue(successCalledOnCaller)

        assertEquals(EXAMPLE_SOURCE_LIST, model.retrievedPeople)
    }
}