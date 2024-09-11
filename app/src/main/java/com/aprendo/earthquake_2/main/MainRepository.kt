package com.aprendo.earthquake_2.main

import androidx.lifecycle.LiveData
import com.aprendo.earthquake_2.Earthquake
import com.aprendo.earthquake_2.api.EqJsonResponse
import com.aprendo.earthquake_2.api.service
import com.aprendo.earthquake_2.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val dataBase: EqDataBase) {
    val eqList: LiveData<MutableList<Earthquake>> = dataBase.eqDao.getEarthquakes()

    suspend fun fetchEarthquakes() {
        return withContext(Dispatchers.IO) {
            val eqJsonResponse = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqJsonResponse)

            dataBase.eqDao.insertAll(eqList)
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features

        for (feature in featureList){
            val id = feature.id
            val properties = feature.properties
            val mag = properties.mag
            val place = properties.place
            val time = properties.time
            val geometry = feature.geometry
            val latitude = geometry.latitude
            val longitude = geometry.longitude
            val earthquake = Earthquake(id, place, mag, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}