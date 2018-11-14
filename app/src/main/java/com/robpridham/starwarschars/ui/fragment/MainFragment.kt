package com.robpridham.starwarschars.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.robpridham.starwarschars.R
import com.robpridham.starwarschars.StarWarsCharactersApp
import com.robpridham.starwarschars.data.PersonData
import com.robpridham.starwarschars.ui.view.HomeScreenView
import com.robpridham.starwarschars.ui.viewcontroller.HomeScreenViewController
import com.robpridham.starwarschars.ui.viewmodel.HomeScreenViewModel

class MainFragment: Fragment() {

    private lateinit var controller: HomeScreenViewController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = this.activity?.application as? StarWarsCharactersApp
        app?.let {
            val wrappedView = HomeScreenView(view)
            this.activity?.let { activity ->
                val viewModel = app.getActivityScopedViewModel<HomeScreenViewModel>(activity)
                this.controller = HomeScreenViewController(
                    viewModel,
                    wrappedView
                ) { person -> showDetailFragment(person) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        controller.onRemove()
    }

    private fun showDetailFragment(personData: PersonData) {
        fragmentManager?.let { fMgr ->
            val ft = fMgr.beginTransaction()
            ft.remove(this)

            val newFragment = DetailFragment()

            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.BUNDLE_KEY_PERSON, personData)
            newFragment.arguments = bundle

            ft.replace(R.id.content_fragment, newFragment, "DETAIL")
            ft.addToBackStack(null)
            ft.commit()
        }
    }
}