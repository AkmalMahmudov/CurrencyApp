package uz.gita.currencyapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.currencyapp.R

class ScreenAbout : Fragment(R.layout.screen_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().actionBar?.title = "About Us"
    }
}