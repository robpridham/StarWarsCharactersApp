package com.robpridham.starwarschars.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.robpridham.starwarschars.R
import com.robpridham.starwarschars.StarWarsCharactersApp
import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.ui.view.DetailScreenView
import com.robpridham.starwarschars.ui.viewcontroller.DetailScreenViewController
import com.robpridham.starwarschars.ui.viewmodel.DetailScreenViewModel

class DetailFragment: Fragment() {

    companion object {
        const val BUNDLE_KEY_PERSON = "person"
    }

    private lateinit var controller: DetailScreenViewController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = this.activity?.application as? StarWarsCharactersApp
        app?.let {
            val person = this.arguments?.getParcelable<PersonData>(BUNDLE_KEY_PERSON)
            person?.let {
                val viewModel = app.getFragmentScopedViewModel<DetailScreenViewModel>(this)
                viewModel.setPerson(person)
                val wrappedView = DetailScreenView(view)
                this.controller = DetailScreenViewController(viewModel, wrappedView)
            }
        }
    }
}