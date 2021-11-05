package com.lisko.factoryww.viewmodel

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lisko.factoryww.R
import com.lisko.factoryww.model.CurrentModel
import com.lisko.factoryww.model.ForecastModel
import com.lisko.factoryww.model.weather.WeatherService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {


    private val weatherAPIService = WeatherService()
    private val compositeDisposable= CompositeDisposable()

    val loadWeather = MutableLiveData<Boolean>()
    val weatherForecastResponse= MutableLiveData<ForecastModel.Response>()
    val weatherCurrentResponse= MutableLiveData<CurrentModel.Response>()
    val weatherLoadingError= MutableLiveData<Boolean>()
    private var city: String= ""


    fun getCurrentFromAPI(){
        if(city!= "") {
            loadWeather.value = true

            compositeDisposable.add(
                weatherAPIService.getCurrent(city)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<CurrentModel.Response>() {
                        override fun onSuccess(value: CurrentModel.Response) {
                            loadWeather.value = false
                            weatherLoadingError.value = false
                            weatherCurrentResponse.value = value
                        }

                        override fun onError(e: Throwable) {
                            loadWeather.value = false
                            weatherLoadingError.value = true
                            Log.e("greska", e.message!!)

                        }
                    })
            )
        }
    }

    fun getForecastFromApi(){
        if(city!= "") {
        loadWeather.value= true

        compositeDisposable.add(
            weatherAPIService.getForecast(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ForecastModel.Response>() {
                    override fun onSuccess(value: ForecastModel.Response) {
                        loadWeather.value= false
                        weatherLoadingError.value= false
                        weatherForecastResponse.value= value
                    }

                    override fun onError(e: Throwable) {
                        loadWeather.value= false
                        weatherLoadingError.value= true
                        Log.e("greska", e.message!!)

                    }
                })
        )

        }
    }

    fun setCity(newCity: String){
        city= newCity
    }

    fun getCity(): String{
        return city
    }
}