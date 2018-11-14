package com.robpridham.starwarschars.ui.viewmodel

import org.junit.Assert.*
import org.junit.Test

class DetailScreenPersonDateFormatterTest {
    @Test fun `test date formatting`() {
        val sampleInput = "2014-12-09T13:50:51.644000Z"
        val expectedOutput = "09/12/2014 13:50"
        val formatter = DetailScreenPersonDateFormatter()
        val actualOutput = formatter.formatDateForDisplay(sampleInput)
        assertEquals(expectedOutput, actualOutput)
    }
}