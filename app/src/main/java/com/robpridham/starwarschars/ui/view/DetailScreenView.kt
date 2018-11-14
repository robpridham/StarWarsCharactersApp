package com.robpridham.starwarschars.ui.view

import android.view.View
import kotlinx.android.synthetic.main.fragment_person.view.*

class DetailScreenView(private val view: View) {

    fun setName(name: String) {
        view.field_name.text = name
    }

    fun setHeight(height: String) {
        view.field_height.text = height
    }

    fun setMass(mass: String) {
        view.field_mass.text = mass
    }

    fun setCreatedAt(createdAt: String) {
        view.field_created.text = createdAt
    }
}

