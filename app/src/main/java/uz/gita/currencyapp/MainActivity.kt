package uz.gita.currencyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import uz.gita.currencyapp.databinding.ActivityMainBinding
import uz.gita.currencyapp.util.CurrencyEvent
import uz.gita.currencyapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btnConvert.setOnClickListener {
            binding.apply {
                viewModel.convertMoney(spinFrom.selectedItem.toString(), spinTo.selectedItem.toString(), amount.text.toString().toInt())
            }
        }
        viewModel.getList.observe(this) {
            if (it !is CurrencyEvent.Loading) {
                // progress bar ochiladi
            }
            when (it) {
                is CurrencyEvent.Failure -> {
                    // fail bo'ldi
                }
                is CurrencyEvent.Loading -> {//progress ar yopiladi
                }
                is CurrencyEvent.Success -> {
                    binding.result.text = it.resultText
                    Toast.makeText(this, it.resultText, Toast.LENGTH_SHORT).show()
                }

                else -> {
                }
            }
        }

    }
}