package com.robpridham.starwarschars.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

interface JsonParser {
    fun <T> fromJson(json: String,  clazz: Class<T>): T

    //TODO: inline reified version for improved syntax
}

class JacksonParser: JsonParser {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    override fun <T> fromJson(json: String, clazz: Class<T>): T {
        return mapper.readValue(json, clazz)
    }
}