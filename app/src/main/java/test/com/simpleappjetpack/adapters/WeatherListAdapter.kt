package test.com.simpleappjetpack.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_list_item.view.*
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.models.X
import test.com.simpleappjetpack.utils.Constants
import java.util.*

class WeatherListAdapter(val items : List<X>, val ctx : Context) : RecyclerView.Adapter<WeatherListAdapter.WeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(LayoutInflater.from(ctx).inflate(R.layout.weather_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder?.dataSet.text = getDate(items[position].dt)
        holder?.datatemp.text = ctx.getString(R.string.WeaterListTemp, convertFahrenheitToCelsius(items[position].main.temp).toString())
        holder?.dataDesc.text = items[position].weather[0].description
        doDownloadImg(items[position].weather[0].icon, holder?.icon)
    }

    class WeatherHolder(view : View) : RecyclerView.ViewHolder(view){
        val dataSet = view.data
        val datatemp = view.txtTemp
        val dataDesc = view.txtDesc
        val icon = view.imageView
    }

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = time * 1000L
        return DateFormat.format("MMM dd HH:mm", cal).toString()
    }
    fun convertFahrenheitToCelsius(fahrenheit: Double): Int {
        var temp0= fahrenheit.toInt()
        return temp0
    }
    private fun doDownloadImg(icon: String, icon1: ImageView) {
        val path = Constants.iconWeather + icon + ".png"
        Picasso.with(ctx).load(path).error(R.drawable.image).into(icon1)
    }
}