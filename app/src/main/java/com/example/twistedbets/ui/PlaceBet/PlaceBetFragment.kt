package com.example.twistedbets.ui.PlaceBet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.twistedbets.R
import com.example.twistedbets.vm.SummonerViewModel

class PlaceBetFragment : Fragment() {

    private lateinit var placeBetViewModel: PlaceBetViewModel
    private val viewModel: SummonerViewModel by viewModels()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        placeBetViewModel =
                ViewModelProviders.of(this).get(PlaceBetViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_place_bets, container, false)

        placeBetViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val spinner: Spinner = view.findViewById(R.id.regionSpinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.regions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        view.findViewById<Button>(R.id.btnFind).setOnClickListener {
            viewModel.getSummonerByName("beckoninghook")
        }

    }
}