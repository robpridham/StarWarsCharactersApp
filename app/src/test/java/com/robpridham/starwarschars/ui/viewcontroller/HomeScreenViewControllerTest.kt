package com.robpridham.starwarschars.ui.viewcontroller

import com.nhaarman.mockitokotlin2.*
import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.ui.view.HomeScreenView
import com.robpridham.starwarschars.ui.viewmodel.HomeScreenViewModel
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Result
import com.robpridham.starwarschars.util.Success
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeScreenViewControllerTest {

    @Mock private lateinit var mockHomeView: HomeScreenView
    @Mock private lateinit var mockViewModel: HomeScreenViewModel
    @Mock private lateinit var mockShowSelectedCharacterScreenCallback: (PersonData)->Unit

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `initialising UI causes request for data and shows loading spinner`() {
        HomeScreenViewController(mockViewModel, mockHomeView, mockShowSelectedCharacterScreenCallback)
        verify(mockViewModel).requestAllPeopleInitialLoad(any())
        verify(mockHomeView).showLoadingSpinner()
    }

    @Test
    fun `failed request for data shows retry UI`() {
        HomeScreenViewController(mockViewModel, mockHomeView, mockShowSelectedCharacterScreenCallback)

        val dataRequestCaptor = argumentCaptor<(Result<Unit, Error>)->Unit>()
        verify(mockViewModel).requestAllPeopleInitialLoad(dataRequestCaptor.capture())

        clearInvocations(mockHomeView)

        dataRequestCaptor.firstValue.invoke(Failure(Error()))
        verify(mockHomeView).showRetry()
        verify(mockHomeView, never()).showContent(any(), any())
        verify(mockHomeView, never()).showLoadingSpinner()
    }

    @Test
    fun `successful request for data shows content UI`() {
        HomeScreenViewController(mockViewModel, mockHomeView, mockShowSelectedCharacterScreenCallback)

        val dataRequestCaptor = argumentCaptor<(Result<Unit, Error>)->Unit>()
        verify(mockViewModel).requestAllPeopleInitialLoad(dataRequestCaptor.capture())

        clearInvocations(mockHomeView)
        val mockPersonListFromModel = mock<List<PersonData>>()
        whenever(mockViewModel.retrievedPeople).thenReturn(mockPersonListFromModel)

        dataRequestCaptor.firstValue.invoke(Success(Unit))
        verify(mockHomeView).showContent(mockPersonListFromModel, false)
        verify(mockHomeView, never()).showRetry()
        verify(mockHomeView, never()).showLoadingSpinner()
    }
}