package com.robpridham.starwarschars.data

import com.robpridham.starwarschars.json.JacksonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PeopleParsingTest {

    companion object {
        private const val SINGLE_PERSON_RESPONSE = """
            {
	"count": 1,
	"next": null,
	"previous": null,
	"results": [
		{
			"name": "Luke Skywalker",
			"height": "172",
			"mass": "77",
			"hair_color": "blond",
			"skin_color": "fair",
			"eye_color": "blue",
			"birth_year": "19BBY",
			"gender": "male",
			"homeworld": "https://swapi.co/api/planets/1/",
			"films": [
				"https://swapi.co/api/films/2/",
				"https://swapi.co/api/films/6/",
				"https://swapi.co/api/films/3/",
				"https://swapi.co/api/films/1/",
				"https://swapi.co/api/films/7/"
			],
			"species": [
				"https://swapi.co/api/species/1/"
			],
			"vehicles": [
				"https://swapi.co/api/vehicles/14/",
				"https://swapi.co/api/vehicles/30/"
			],
			"starships": [
				"https://swapi.co/api/starships/12/",
				"https://swapi.co/api/starships/22/"
			],
			"created": "2014-12-09T13:50:51.644000Z",
			"edited": "2014-12-20T21:17:56.891000Z",
			"url": "https://swapi.co/api/people/1/"
		}]
}
        """
    }

    @Test
    fun `parse single person response`() {
        val parser = JacksonParser()
        val response = parser.fromJson(SINGLE_PERSON_RESPONSE, MultiPersonResponse::class.java)
        assertNotNull(response)
        assertEquals(1, response.count)
        assertEquals(null, response.next)
        assertEquals(null, response.previous)
        assertEquals(1, response.results.size)

        val firstPerson = response.results[0]
        assertEquals("Luke Skywalker", firstPerson.name)
        assertEquals(172.toString(), firstPerson.height)
        assertEquals(77.toString(), firstPerson.mass)
        assertEquals("2014-12-09T13:50:51.644000Z", firstPerson.created)
    }
}