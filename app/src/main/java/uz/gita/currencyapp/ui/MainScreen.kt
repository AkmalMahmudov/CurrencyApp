package uz.gita.currencyapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.currencyapp.R
import uz.gita.currencyapp.databinding.ScreenMainBinding
import uz.gita.currencyapp.util.CurrencyEvent
import uz.gita.currencyapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var running = false
        binding.apply {
            btnConvert.setOnClickListener {
                if (binding.amount.text.toString().isNotBlank()) {
                    binding.apply {
                        viewModel.convertMoney(spinFrom.selectedItem.toString(), spinTo.selectedItem.toString(), amount.text.toString().toFloat())
                    }
                } else {
                    Snackbar.make(binding.root, "Enter the amount of money", Snackbar.LENGTH_SHORT).show()
                }
            }
            changer.setOnClickListener {
                if (!running) {
                    running = true
                    val spin = spinFrom.selectedItemPosition
                    lifecycleScope.launch {
                        spinFrom.animate().translationXBy(100F).start()
                        spinTo.animate().translationXBy(-100F).start()

                        delay(300)
                        spinFrom.setSelection(spinTo.selectedItemPosition)
                        spinTo.setSelection(spin)

                        spinFrom.animate().translationXBy(-100F).start()
                        spinTo.animate().translationXBy(100F).start()
                        delay(300)
                        running = false
                    }
                }
            }
        }
        viewModel.getList.observe(viewLifecycleOwner) {
            when (it) {
                is CurrencyEvent.Failure -> {
                    Snackbar.make(binding.root, it.errorText, Snackbar.LENGTH_SHORT).show()
                }
                is CurrencyEvent.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.result.isVisible = false
                }
                is CurrencyEvent.Success -> {
                    binding.progressBar.isVisible = false
                    binding.result.isVisible = true
                    binding.result.text =
                        "${binding.amount.text} ${binding.spinFrom.selectedItem} = ${it.resultText} ${binding.spinTo.selectedItem}"
                }
                else -> {
                }
            }
        }
    }
}