package com.robpridham.starwarschars.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.robpridham.starwarschars.data.PersonData
import java.text.SimpleDateFormat
import java.util.*

class DetailScreenViewModel(
    private val dateFormatter: DetailScreenPersonDateFormatter = DetailScreenPersonDateFormatter()
): ViewModel() {

    data class FormattedPerson(val name: String, val height: String, val mass: String, val created: String)

    fun setPerson(personData: PersonData) {
        formattedPerson = formatPersonData(personData)
    }

    private lateinit var formattedPerson: FormattedPerson

    val name: String get() = formattedPerson.name
    val height: String get() = formattedPerson.height
    val mass: String get() = formattedPerson.mass
    val created: String get() = formattedPerson.created

    private fun formatPersonData(rawPerson: PersonData): FormattedPerson {
        with(rawPerson) {
            return FormattedPerson(name,
                formatHeightForDisplay(height),
                formatMassForDisplay(mass),
                dateFormatter.formatDateForDisplay(created))
        }
    }

    private fun formatMassForDisplay(mass: String): String {
        //TODO: get unit text from resources instead of hardcoding it here
        return when (mass) {
            "unknown" -> "Unknown"
            else -> "${mass}kg"
        }
    }

    private fun formatHeightForDisplay(height: String): String {
        //TODO: get unit text from resources instead of hardcoding it here
        return when (height) {
            "unknown" -> "Unknown"
            else -> "${height}cm"
        }
    }
}

class DetailScreenPersonDateFormatter {
    fun formatDateForDisplay(createdDate: String): String {
        //workaround for bug in SimpleDateFormatter where it can't handle > 3 digit milliseconds
        val fixedDate = createdDate.replace("000Z", "Z")
        val parsedDate = SimpleDateFormat(PersonData.SOURCE_DATE_FORMAT, Locale.getDefault()).parse(fixedDate)
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(parsedDate)
    }
}