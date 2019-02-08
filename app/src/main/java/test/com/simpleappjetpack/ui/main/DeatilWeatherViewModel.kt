package test.com.simpleappjetpack.ui.main

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.api.ApiCall
import test.com.simpleappjetpack.api.GetRetrofit
import test.com.simpleappjetpack.models.WeatherCityResponse
import test.com.simpleappjetpack.utils.Engine

class DeatilWeatherViewModel : ViewModel() {

    private var weatherResponse : MutableLiveData<WeatherCityResponse.WeatherCity>? = null

    fun getWeatherResponse(ctx: Activity, cityId: Int, appId: String) : LiveData<WeatherCityResponse.WeatherCity> {
        weatherResponse = MutableLiveData()

        loadWeatherApiRequest(ctx, cityId, appId)
        return weatherResponse as MutableLiveData<WeatherCityResponse.WeatherCity>
    }
    fun requestWeatherApi(context: Context, id : Int, appId : String) : Call<WeatherCityResponse.WeatherCity> {
        val retrofit : Retrofit = GetRetrofit().getRetro()
       val apiCall : ApiCall= retrofit.create(ApiCall ::class.java)
        return apiCall.getWeather(id, appId, "metric");
    }
    private fun loadWeatherApiRequest(ctx: Activity, cityId: Int, appId: String) {

        requestWeatherApi(ctx, cityId, appId).enqueue(object : Callback<WeatherCityResponse.WeatherCity> {
            override fun onFailure(call: Call<WeatherCityResponse.WeatherCity>, t: Throwable) {
                Engine.newInstance().showMsgDialog("", ctx.getString(R.string.ErrorMsg), ctx)
            }

            override fun onResponse(call: Call<WeatherCityResponse.WeatherCity>, response: Response<WeatherCityResponse.WeatherCity>) {
                if(response.errorBody() != null)
                    Engine.newInstance().handleServerError(ctx, response.errorBody()!!.string())
                else
                    weatherResponse!!.value = response.body()
            }

        })
    }
}