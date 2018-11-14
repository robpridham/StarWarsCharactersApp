package com.robpridham.starwarschars.ui.view

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.robpridham.starwarschars.data.PersonData
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeScreenView(private val view: View) {

    var retryListener: (()->Unit)? = null

    var loadMoreRequestListener: (()->Unit)? = null

    var itemPressedListener: ((PersonData)->Unit)? = null

    private enum class LoadingState {
        LOADING,
        LOADED,
        FAILED
    }

    private var loadingState = LoadingState.LOADING

    init {
        val rvLayoutManager = LinearLayoutManager(view.context)

        view.home_person_list.apply {
            layoutManager = rvLayoutManager
            val dividerItemDecoration = DividerItemDecoration(
                context,
                rvLayoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)
        }
        view.btn_retry.setOnClickListener {
            retryListener?.invoke()
        }
    }

    fun showRetry() {
        loadingState = LoadingState.FAILED
        updateLoadingState()
    }

    fun showContent(retrievedPeople: List<PersonData>, canLoadMore: Boolean) {
        val adapter = PeopleRVAdapter(
            retrievedPeople,
            canLoadMore,
            { requestMoreContent()},
            itemPressedListener)
        view.home_person_list.adapter = adapter
        loadingState = LoadingState.LOADED
        updateLoadingState()
    }

    private fun requestMoreContent() = loadMoreRequestListener?.invoke()

    fun showLoadingSpinner() {
        loadingState = LoadingState.LOADING
        updateLoadingState()
    }

    private fun updateLoadingState() {
        view.layout_loading.visibility = if (loadingState == LoadingState.LOADING) View.VISIBLE else View.GONE
        view.home_person_list.visibility = if (loadingState == LoadingState.LOADED) View.VISIBLE else View.GONE
        view.layout_retry.visibility = if (loadingState == LoadingState.FAILED) View.VISIBLE else View.GONE
    }

    fun updateContent(retrievedPeople: List<PersonData>, canLoadMore: Boolean) {
        view.post {
            val adapter = view.home_person_list.adapter as? PeopleRVAdapter
            adapter?.apply {
                removeLoadingMoreCell()
                addExtraItems(retrievedPeople.subList(adapter.itemCount, retrievedPeople.size))
                this.canLoadMore = canLoadMore
            }
        }
    }

    fun showLoadingMoreInList() {
        val adapter = view.home_person_list.adapter as? PeopleRVAdapter
        view.home_person_list.post {
            adapter?.addLoadingMoreCell()
        }
    }

    fun onRemove() {
        view.home_person_list.adapter = null
    }
}

