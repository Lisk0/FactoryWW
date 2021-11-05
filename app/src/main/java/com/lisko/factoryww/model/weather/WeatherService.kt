package com.lisko.factoryww.model.weather

import com.lisko.factoryww.model.Constants
import com.lisko.factoryww.model.CurrentModel
import com.lisko.factoryww.model.ForecastModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class WeatherService {
    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getCurrent(city: String): Single<CurrentModel.Response> {
        return api.getCurrent(
            city,
            Constants.API_KEY_VALUE,
            Constants.UNITS_VALUE
        )
    }

    fun getForecast(city: String): Single<ForecastModel.Response> {
        return api.getForecast(
            city,
            Constants.API_KEY_VALUE,
            Constants.UNITS_VALUE
        )
    }
}