package com.robpridham.starwarschars.config

class LocalConfig {
    //TODO: would be better as remote config with app bootstrap etc
    //TODO: failing that, local resources e.g. XML

    companion object {
        const val SWC_SERVER_PRIMARY_URL = "https://swapi.co/api"
        const val SWC_ALL_PEOPLE_ENDPOINT = "/people"
    }
}