package test.com.simpleappjetpack.ui.main

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.api.ApiCall
import test.com.simpleappjetpack.api.GetRetrofit
import test.com.simpleappjetpack.models.ResponseWeatherNextDays
import test.com.simpleappjetpack.utils.Engine

class WeatherNextDaysViewModel : ViewModel() {

    private var weatherResponse : MutableLiveData<ResponseWeatherNextDays>? = null

    fun getWeatherResponse(ctx: Activity, cityId: Int, appId: String) : LiveData<ResponseWeatherNextDays> {
        weatherResponse = MutableLiveData()

        loadWeatherApiRequest(ctx, cityId, appId)
        return weatherResponse as MutableLiveData<ResponseWeatherNextDays>
    }

    fun requestWeatherNextDays(id : Int, appId : String): Call<ResponseWeatherNextDays>{
        val retrofit : Retrofit = GetRetrofit().getRetro()
        val apiCall : ApiCall = retrofit.create(ApiCall ::class.java)
        return apiCall.getWeatherNextDays(id, appId, "metric")
    }

    private fun loadWeatherApiRequest(ctx: Activity, cityId: Int, appId: String) {

        requestWeatherNextDays(cityId, appId).enqueue(object : Callback<ResponseWeatherNextDays> {
            override fun onFailure(call: Call<ResponseWeatherNextDays>, t: Throwable) {
                Engine.newInstance().showMsgDialog("", ctx.getString(R.string.ErrorMsg), ctx)
            }

            override fun onResponse(call: Call<ResponseWeatherNextDays>, response: Response<ResponseWeatherNextDays>) {
                if(response.errorBody() != null)
                    Engine.newInstance().handleServerError(ctx, response.errorBody()!!.string())
                else
                    weatherResponse!!.value = response.body()
            }

        })
    }
}