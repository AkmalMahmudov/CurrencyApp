package uz.gita.currencyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.currencyapp.model.data.MoneyData
import uz.gita.currencyapp.model.repository.MainRepository
import uz.gita.currencyapp.util.CurrencyEvent
import uz.gita.currencyapp.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var _getList = MutableLiveData<CurrencyEvent>()
    val getList: LiveData<CurrencyEvent> get() = _getList
    private lateinit var moneyList: List<MoneyData>

    fun convertMoney(fromCcy: String, toCcy: String, amount: Float) {

        CoroutineScope(Dispatchers.IO).launch {
            _getList.postValue(CurrencyEvent.Loading)

            when (val data = repository.getRates()) {
                is Resource.Error -> _getList.postValue(CurrencyEvent.Failure("An error occurred. Please check the connection "/*data.message!!*/))

                is Resource.Success -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        moneyList = data.data!!
                        if (fromCcy == "UZS" && toCcy == "UZS") {
                            _getList.postValue(CurrencyEvent.Success(amount.toString()))
                        } else {
                            if (fromCcy == "UZS") {
                                val to = getRates(toCcy)
                                if (to != null) {
                                    _getList.postValue(CurrencyEvent.Success((amount / to.Rate.toFloat()).toString()))
                                }
                            }
                            if (toCcy == "UZS") {
                                val from = getRates(fromCcy)
                                if (from != null) {
                                    _getList.postValue(CurrencyEvent.Success((from.Rate.toFloat() * amount).toString()))
                                }
                            } else {
                                val from = getRates(fromCcy)
                                val to = getRates(toCcy)
                                if (from != null && to != null) {
                                    _getList.postValue(CurrencyEvent.Success((amount * from.Rate.toFloat() / to.Rate.toFloat()).toString()))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getRates(ccy: String): MoneyData? {
        var money: MoneyData? = null
        for (i in moneyList.indices) {
            if (ccy == moneyList[i].Ccy) {
                money = moneyList[i]
            }
        }
        return money
    }
}