package com.lisko.factoryww.model.weather


import com.lisko.factoryww.model.Constants
import com.lisko.factoryww.model.CurrentModel
import com.lisko.factoryww.model.ForecastModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(Constants.CURRENT_ENDPOINT)
    fun getCurrent(
        @Query(Constants.CITY_NAME) city: String,
        @Query(Constants.API_ID) apiKey: String,
        @Query(Constants.UNITS) units: String,
    ): Single<CurrentModel.Response>

    @GET(Constants.FORECAST_ENDPOINT)
    fun getForecast(
        @Query(Constants.CITY_NAME) city: String,
        @Query(Constants.API_ID) apiKey: String,
        @Query(Constants.UNITS) units: String,
    ): Single<ForecastModel.Response>

}