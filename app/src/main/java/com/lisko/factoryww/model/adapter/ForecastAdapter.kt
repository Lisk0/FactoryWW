package com.lisko.factoryww.model.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lisko.factoryww.databinding.AdapterForecastBinding
import com.lisko.factoryww.model.ForecastModel
import java.util.*
import kotlin.math.roundToInt
import com.squareup.picasso.Picasso

class ForecastAdapter (private val act: AppCompatActivity): RecyclerView.Adapter<ForecastAdapter.ViewHolder>(){

    private var mForecastList: List<ForecastModel.Days> = listOf()

    class ViewHolder (view: AdapterForecastBinding): RecyclerView.ViewHolder(view.root){
        val weatherDate= view.weatherDate
        val weatherTime= view.weatherTime
        val weatherTemp= view.weatherTemp
        val iconForecast= view.ivIconForecast
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val binding= AdapterForecastBinding.inflate(act.layoutInflater)
        return ForecastAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val dataDaily = mForecastList[position]
        val temp= dataDaily.main.temp.roundToInt().toString() + " °C"
        holder.weatherTemp.text= temp
        val date= Date(dataDaily.dt.toLong()*1000)

        val splited= date.toString().split(" ")
        Log.i("date", date.toString())
        val formatedDate= splited[0] +" "+ splited[1]+" "+ splited[2]+" "+ splited.last()
        val formatedTime= splited[3].substring(0,5) + " h"
        holder.weatherDate.text= formatedDate
        holder.weatherTime.text= formatedTime

        val imgUrl: String= "https://openweathermap.org/img/wn/"+ dataDaily.weather.first().icon+ "@2x.png"


        Picasso.get().load(imgUrl).into(holder.iconForecast)

    }

    override fun getItemCount(): Int {
        return mForecastList.size
    }

    fun setForecast(list: List<ForecastModel.Days>){
        mForecastList= list
        notifyDataSetChanged()
    }
}