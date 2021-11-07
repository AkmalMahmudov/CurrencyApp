package uz.gita.currencyapp.model.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import uz.gita.currencyapp.model.data.ApiService
import uz.gita.currencyapp.model.data.MoneyData
import uz.gita.currencyapp.util.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: ApiService
) {
   lateinit var data: List<MoneyData>

    suspend fun getRates(): Resource<List<MoneyData>> {
        return try {
            val response=service.getRates()
            val result=response.body()
            if(response.isSuccessful && result !=null){
                Resource.Success(result)
            }
            else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message?:"error")
        }
    }
}