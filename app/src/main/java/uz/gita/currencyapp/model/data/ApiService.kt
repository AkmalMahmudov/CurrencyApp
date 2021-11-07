package uz.gita.currencyapp.model.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("uzc/arkhiv-kursov-valyut/json/")
   suspend fun getRates(): Response<List<MoneyData>>
}