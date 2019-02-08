package test.com.simpleappjetpack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_list.*
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.adapters.WeatherListAdapter
import test.com.simpleappjetpack.models.ResponseWeatherNextDays
import test.com.simpleappjetpack.utils.Constants

class WeatherNextDaysFragment : Fragment() {

    lateinit var viewModel: WeatherNextDaysViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_list, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherNextDaysViewModel ::class.java)
        val cityId_: Int = arguments!!.getInt("cityId")
        viewModel.getWeatherResponse(activity!!, cityId_, Constants.appId).observe(this.activity!!, object : Observer<ResponseWeatherNextDays>{
            override fun onChanged(t: ResponseWeatherNextDays?) {
                txtCityName.text = t?.city?.name
                val weatherList = t?.list
                recycleView.layoutManager = LinearLayoutManager(activity!!)
                recycleView.adapter = WeatherListAdapter(weatherList!!, activity!!)
            }
        })
    }
}