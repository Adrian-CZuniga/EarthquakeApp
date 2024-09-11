package com.aprendo.earthquake_2.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.aprendo.earthquake_2.api.ApiResponseStatus
import com.aprendo.earthquake_2.database.getDataBase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application): AndroidViewModel(application) {
    private val database = getDataBase(application.applicationContext)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    val eqList = repository.eqList
    init {
         viewModelScope.launch {
             try {
                 _status.value = ApiResponseStatus.LOADING
                 repository.fetchEarthquakes()
                 _status.value = ApiResponseStatus.DONE
             } catch (e: UnknownHostException){
                 Log.d(TAG, "No internet conection: ${e}")
                 Toast.makeText(application.applicationContext, "No hay internet para descargar nuevos datos.", Toast.LENGTH_SHORT).show()
                 _status.value = ApiResponseStatus.ERROR
             }
        }
    }
}