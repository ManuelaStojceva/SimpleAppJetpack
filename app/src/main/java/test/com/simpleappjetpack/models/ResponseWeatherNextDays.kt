package test.com.simpleappjetpack.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseWeatherNextDays(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Double
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class X(
    val clouds: Clouds,
    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val snow: Snow,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Sys(
    val pod: String
)

data class Wind(
    val deg: Double,
    val speed: Double
)

data class Clouds(
    val all: Int
)

data class Main(
    val grnd_level: Double,
    val humidity: Int,
    val pressure: Double,
    val sea_level: Double,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Snow(
        @SerializedName("\\3h")
        @Expose
        val `3h`: Double? = 0.0

)