package com.helloumi.weatherapplication.ui.feature.addcity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.helloumi.weatherapplication.BuildConfig
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.databinding.ActivityAddCityBinding
import com.helloumi.weatherapplication.ui.feature.main.MainActivity


class AddCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val color = ContextCompat.getColor(this, R.color.purple_40)
        window.statusBarColor = color // Set color of system statusBar same as ActionBar

        binding.toolbar.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        if (!Places.isInitialized()) {
            Places.initialize(this, BuildConfig.API_KEY)
        }
        setupAutoCompletePlace()
    }

    private fun setupAutoCompletePlace() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("TAG", "Place: ${place.name}, ${place.id}")
                //place.id?.let { place.name?.let { it1 -> viewModel.setCity(it, it1) } }
                // clearText(autocompleteFragment)
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }
}