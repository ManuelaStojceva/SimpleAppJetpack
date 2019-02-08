package test.com.simpleappjetpack.api

import android.content.Context
import retrofit2.Call
import test.com.simpleappjetpack.models.WeatherCityResponse
import test.com.simpleappjetpack.utils.Constants

class Repository {

    lateinit var apiCall: ApiCall
    lateinit var retrofit: GetRetrofit

    fun requestWeatherApi(context: Context, id : Int) : Call<WeatherCityResponse.WeatherCity> {
        apiCall = retrofit.getRetro().create(ApiCall ::class.java)
        return apiCall.getWeather(id, Constants.appId, "metric");
    }
}