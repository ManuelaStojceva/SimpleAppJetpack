package test.com.simpleappjetpack.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.api.Repository
import test.com.simpleappjetpack.models.CountryObject
import test.com.simpleappjetpack.models.WeatherCityResponse
import test.com.simpleappjetpack.utils.Engine

class MainViewModel : ViewModel() {

    // TODO: Implement the ViewModel
    fun getCountries(ctx : Context):String{
        return Engine.newInstance().readRaw(ctx, R.raw.country_list)
    }

    fun getCountryList(ctx : Context) : List<CountryObject.Data>? {
        val countryList: MutableList<CountryObject.Data> = ArrayList()

        var gson = Gson()
        var countryString = getCountries(ctx)

        if(!countryString.isEmpty()){
            val array = JSONArray(countryString)
            if (array.length() > 0) {
                for (i in 0 until array.length()) {
                    val jsonobject = array.getJSONObject(i)
                    val objectString = jsonobject.toString()
                    var countryObject = gson?.fromJson(objectString, CountryObject.Data ::class.java)
                    countryList.add(countryObject)

                }
            }
        }
        return countryList
    }

    fun getCity(ctx : Context, city : String) :String{
        var countryList = getCountryList(ctx)
        if(countryList!!.size > 0){
            for(i in 0 until countryList.size){
                val data = countryList.get(i)
                val dataCity = data.name
                if(dataCity.contains(city, ignoreCase = true))
                    return dataCity
            }
        }
        return ""
    }

    fun getCityId(ctx : Context, city : String):Int{
        var countryList = getCountryList(ctx)
        if(countryList!!.size > 0){
            for(i in 0 until countryList.size){
                val data = countryList.get(i)
                val dataCity = data.name
                if(dataCity.equals(city, ignoreCase = true))
                    return data.id
            }
        }
        return 0
    }
}
