package com.robpridham.starwarschars.data

import com.robpridham.starwarschars.config.LocalConfig
import com.robpridham.starwarschars.json.JsonParser
import com.robpridham.starwarschars.network.NetworkService
import com.robpridham.starwarschars.util.Failure
import com.robpridham.starwarschars.util.Result
import com.robpridham.starwarschars.util.Success

interface StarWarsService {
    fun getAllPeopleInitialData(onResult: (Result<MultiPersonResponse, Error>) -> Unit)
    fun getAllPeopleMoreData(nextUrl: String, onResult: (Result<MultiPersonResponse, Error>) -> Unit)
}

class SwapiDotCoStarWarsService(
    private val networkService: NetworkService,
    private val jsonParser: JsonParser): StarWarsService {

    override fun getAllPeopleInitialData(onResult: (Result<MultiPersonResponse, Error>) -> Unit) {
        val url = "${LocalConfig.SWC_SERVER_PRIMARY_URL}${LocalConfig.SWC_ALL_PEOPLE_ENDPOINT}"

        networkService.getRequest(url) {
            when (it) {
                is Success -> parseMultiPersonResponse(it.payload, onResult)
                is Failure -> onResult(Failure(Error()))
            }
        }
    }

    override fun getAllPeopleMoreData(nextUrl: String, onResult: (Result<MultiPersonResponse, Error>) -> Unit) {
        networkService.getRequest(nextUrl) {
            when (it) {
                is Success -> parseMultiPersonResponse(it.payload, onResult)
                is Failure -> onResult(Failure(Error()))
            }
        }
    }

    private fun parseMultiPersonResponse(json: String, onResult: (Result<MultiPersonResponse, Error>) ->Unit){
        try {
            val response = jsonParser.fromJson(json, MultiPersonResponse::class.java)
            onResult(Success(response))
        }
        //TODO: tighten exception types, e.g. JsonProcessingException, IllegalArgument
        catch (e: Exception) {
            onResult(Failure(Error(e.message)))
        }
    }
}