package uz.gita.currencyapp.model.data

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.currencyapp.app.App

object ApiClient {
    private val logging =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging)
        .addInterceptor(ChuckInterceptor(App.instance)).build()

    val retrofit: Retrofit =
        Retrofit.Builder().baseUrl("http://cbu.uz/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
}