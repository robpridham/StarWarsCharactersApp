package com.robpridham.starwarschars.ui.viewcontroller

import com.robpridham.starwarschars.ui.view.DetailScreenView
import com.robpridham.starwarschars.ui.viewmodel.DetailScreenViewModel

class DetailScreenViewController(
    viewModel: DetailScreenViewModel,
    view: DetailScreenView
) {
    init {
        view.setName(viewModel.name)
        view.setHeight(viewModel.height)
        view.setMass(viewModel.mass)
        view.setCreatedAt(viewModel.created)
    }
}