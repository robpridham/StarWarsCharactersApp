package com.robpridham.starwarschars.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robpridham.starwarschars.R
import com.robpridham.starwarschars.data.PersonData
import kotlinx.android.synthetic.main.cell_person.view.*
import java.lang.IllegalArgumentException

interface RecyclerCellViewBinder<in T: RecyclerView.ViewHolder> {
    fun onBind(holder: T)
}

class PeopleRVAdapter(
    initialPersonList: List<PersonData>,
    initialCanLoadMore: Boolean,
    private val loadMoreCallback: ()->Unit,
    private val itemPressedCallback: ((PersonData)->Unit)?): RecyclerView.Adapter<PersonViewHolder>() {

    private val personList = mutableListOf<PersonData>()
    private var loadingMore = false
    var canLoadMore = initialCanLoadMore

    companion object {
        private const val ITEM_TYPE_CELL = 1
        private const val ITEM_TYPE_LOADING = 2
    }

    init {
        personList.addAll(initialPersonList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            ITEM_TYPE_CELL -> inflater.inflate(R.layout.cell_person, parent, false)
            ITEM_TYPE_LOADING -> inflater.inflate(R.layout.cell_loading, parent, false)
            else -> throw IllegalArgumentException("Unhandled view type")
        }
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size + if (loadingMore) 1 else 0
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        if (!loadingMore && position == itemCount - 1) {
            loadMoreCallback()
        }

        if (position < personList.size) {
            val binder = PeopleRVCellBinder(personList[position], itemPressedCallback)
            binder.onBind(holder)
        }
    }

    fun addExtraItems(newItems: List<PersonData>) {
        val initialSize = itemCount
        personList.addAll(newItems)
        notifyItemRangeInserted(initialSize, newItems.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (loadingMore && position == itemCount - 1) {
            ITEM_TYPE_LOADING
        } else ITEM_TYPE_CELL
    }

    fun addLoadingMoreCell() {
        if (!loadingMore) {
            loadingMore = true
            notifyItemInserted(itemCount-1)
        }
    }

    fun removeLoadingMoreCell() {
        if (loadingMore) {
            val loadingCellPosition = itemCount-1
            loadingMore = false
            notifyItemRemoved(loadingCellPosition)
        }
    }
}

class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val nameField: TextView = itemView.field_name
}

class PeopleRVCellBinder(private val person: PersonData, private val onClickListener: ((PersonData)->Unit)?): RecyclerCellViewBinder<PersonViewHolder> {
    override fun onBind(holder: PersonViewHolder) {
        holder.nameField.text = person.name
        holder.itemView.setOnClickListener{
            onClickListener?.invoke(person)
        }
    }
}