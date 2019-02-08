package test.com.simpleappjetpack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_weather_fragment.*
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.models.WeatherCityResponse
import test.com.simpleappjetpack.utils.Constants
import test.com.simpleappjetpack.utils.Engine

class DetailWeatherFragment : Fragment() {
    private lateinit var viewModel: DeatilWeatherViewModel
    private lateinit var imgIconWeather : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_weather_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgIconWeather = view.findViewById(R.id.imagIconWeather)

                viewModel = ViewModelProviders.of(this).get(DeatilWeatherViewModel::class.java)

        val cityId_: Int = arguments!!.getInt("cityId")

        viewModel.getWeatherResponse(activity!!, cityId_, Constants.appId).observe(this.activity!!, object : Observer<WeatherCityResponse.WeatherCity> {
            override fun onChanged(t: WeatherCityResponse.WeatherCity?) {
                txtCity.text = t?.name
                txtCountry.text = t?.sys!!.country
                txtGeoLoc.text = getString(R.string.WeatherDesc, t?.coord.lat.toString(), t?.coord.lon.toString())

                txtDetailDesc.text = getString(R.string.WeatherDetailDesc, convertFahrenheitToCelsius(t?.main.temp).toString(), convertFahrenheitToCelsius(t?.main.temp_min).toString(), convertFahrenheitToCelsius(t?.main.temp_max).toString(),
                        t?.wind.speed.toString(), t?.main.pressure.toString())
                val countryName = t?.sys!!.country
                val path = Constants.iconCountry + countryName.toLowerCase() + ".png"
                Picasso.with(context).load(path).error(R.mipmap.ic_launcher).into(imgIconCountry)
                doDownloadImg(t!!.weather!!)
            }

        })

        btnShowMore.setOnClickListener {
            if(cityId_ == 0)
                Engine.newInstance().showMsgDialog("", "Try again!", activity!!)
            else {
                val cityToSend = Bundle()
                cityToSend.putInt("cityId", cityId_)
                it.findNavController().navigate(R.id.action_detailWeatherFragment_to_weatherNextDaysFragment, cityToSend)
            }
        }
    }

     fun convertFahrenheitToCelsius(fahrenheit: Float): Int {
         var temp0= fahrenheit.toInt()
         return temp0
    }
    private fun doDownloadImg(weather: List<WeatherCityResponse.Weather>) {
        for (i in 0 until weather.size){
            txtDesc.text = weather[i].description
            val icon = weather[i].icon
            val path = Constants.iconWeather + icon + ".png"
            Picasso.with(context).load(path).error(R.drawable.image).into(imgIconWeather)
        }
    }
  private class ImageDownloader() : Worker(){
        override fun doWork(): Result {

            Log.d("Start", "Start")
            return Result.SUCCESS
        }


    }
}