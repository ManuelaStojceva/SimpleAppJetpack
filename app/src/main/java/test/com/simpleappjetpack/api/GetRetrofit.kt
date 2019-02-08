package test.com.simpleappjetpack.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import test.com.simpleappjetpack.BuildConfig
import test.com.simpleappjetpack.utils.Constants

class GetRetrofit {
    lateinit var retrofit : Retrofit

    fun getRetro() : Retrofit{
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }
}