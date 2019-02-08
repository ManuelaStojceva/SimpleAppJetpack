package test.com.simpleappjetpack.interfaces

import retrofit2.Response
import test.com.simpleappjetpack.models.WeatherCityResponse

interface WeatherApiInterface {
    fun onSuccessWeather(respose : Response<WeatherCityResponse.WeatherCity>)
    fun onFailure()
}