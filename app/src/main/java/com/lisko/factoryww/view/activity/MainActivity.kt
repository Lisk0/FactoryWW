package com.lisko.factoryww.view.activity

import android.content.DialogInterface
import android.graphics.Typeface.DEFAULT_BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.lisko.factoryww.databinding.ActivityMainBinding
import com.lisko.factoryww.viewmodel.MainActivityViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisko.factoryww.R
import com.lisko.factoryww.model.adapter.ForecastAdapter
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private val adapter= ForecastAdapter(this@MainActivity)
    private lateinit var dialog: AlertDialog.Builder
    private var shown= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.btnLoad.setOnClickListener {
            viewModel.setCity(binding.etCity.text.toString())
            viewModel.getCurrentFromAPI()
            viewModel.getForecastFromApi()
        }
        viewModelObservers()

        binding.srlMainActivity.setOnRefreshListener {
            if(viewModel.getCity().isNotBlank()){
                viewModel.getCurrentFromAPI()
                viewModel.getForecastFromApi()
            }
            else{
                binding.srlMainActivity.isRefreshing= false
            }

        }
        dialog= AlertDialog.Builder(this).setTitle(getString(
            R.string.error))
            .setMessage(getString(R.string.error_loading))
            .setPositiveButton(
                "Ok"
            ) { _, _ ->
                shown = false
            }

    }

    private fun viewModelObservers(){
        viewModel.weatherForecastResponse.observe(this){
            binding.rvForecast.layoutManager= LinearLayoutManager(this,
             RecyclerView.HORIZONTAL, false)
            adapter.setForecast(it.list)
            Log.i("listSIZE", it.list.size.toString())
            binding.rvForecast.adapter= adapter

            if(binding.srlMainActivity.isRefreshing){
                binding.srlMainActivity.isRefreshing= false
            }

        }

        viewModel.weatherCurrentResponse.observe(this){
            val currentTemp= it.main.temp.roundToInt().toString() + " °C"
            binding.tvCurrentTemp.text= currentTemp
            binding.textView.textSize= 24f
            binding.textView.typeface= DEFAULT_BOLD
            binding.textView.text= it.name


            val imgUrl: String= "https://openweathermap.org/img/wn/"+ it.weather.first().icon + "@2x.png"
            Log.i("url-adapter", imgUrl)

            Picasso.get().load(imgUrl)
                .into(binding.ivCurrent)
        }

        viewModel.weatherLoadingError.observe(this, {
                error -> error?.let {
            Log.e("weather loading error", error.toString())
            if(error){
                if(!shown){
                    shown=true
                    dialog.show()
                }
            }
            else{
                //binding.layoutNotLoaded.visibility= View.GONE

            }
        }
        })

        viewModel.loadWeather.observe(this, {
                loading -> loading?.let {
            Log.i("weather loading", "{$loading}")
            if(!loading) binding.srlMainActivity.isRefreshing= false
        }
        })

    }
}

