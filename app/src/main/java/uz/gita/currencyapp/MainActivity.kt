package uz.gita.currencyapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btnConvert.setOnClickListener {
            binding.apply {
                viewModel.convertMoney(spinFrom.selectedItem.toString(), spinTo.selectedItem.toString(), amount.text.toString().toInt())
            }
        }
        viewModel.getList.observe(this) {
                binding.progressBar.isVisible = true
            if (it !is CurrencyEvent.Loading) {
                // progress bar ochiladi
            }
            when (it) {
                is CurrencyEvent.Failure -> {
                    Toast.makeText(this, it.errorText, Toast.LENGTH_SHORT).show()
                    // fail bo'ldi
                }
                is CurrencyEvent.Loading -> {
                }
                is CurrencyEvent.Success -> {
                    binding.progressBar.isVisible = false
                    binding.result.text =
                        "${binding.amount.text} ${binding.spinFrom.selectedItem} = ${it.resultText} ${binding.spinTo.selectedItem}"
                }

                else -> {
                }
            }
        }

    }
}