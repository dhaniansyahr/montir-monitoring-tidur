package com.meone.montir.view.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meone.montir.R
import com.meone.montir.view.viewmodel.MonthlyViewModel

class MonthlyFragment : Fragment() {

    companion object {
        fun newInstance() = MonthlyFragment()
    }

    private val viewModel: MonthlyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_monthly, container, false)
    }
}