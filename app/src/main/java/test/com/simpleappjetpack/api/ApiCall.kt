package test.com.simpleappjetpack.api

import retrofit2.Call
import retrofit2.http.*
import test.com.simpleappjetpack.models.ResponseWeatherNextDays
import test.com.simpleappjetpack.models.WeatherCityResponse

interface ApiCall {
    @Headers("Content-Type: application/json")
    @GET("data/2.5/weather")
    fun getWeather(@Query("id") id : Int, @Query("appid") appId : String, @Query("units") units : String): Call<WeatherCityResponse.WeatherCity>

    @Headers("Content-Type: application/json")
    @GET("data/2.5/forecast")
    fun getWeatherNextDays(@Query("id") id : Int, @Query("appid") appId : String, @Query("units") units : String): Call<ResponseWeatherNextDays>
}