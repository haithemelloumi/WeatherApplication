package com.helloumi.weatherapplication.ui.feature.addcity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.helloumi.weatherapplication.BuildConfig
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.databinding.ActivityAddCityBinding
import com.helloumi.weatherapplication.ui.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCityBinding

    // viewModels() delegate used to get
    // by view models will automatically construct the viewModels using Hilt
    private val addCityViewModel: AddCityViewModel by viewModels()

    // Initialize the AutocompleteSupportFragment.
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private var placeId = ""
    private var placeName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val color = ContextCompat.getColor(this, R.color.purple_40)
        window.statusBarColor = color // Set color of system statusBar same as ActionBar

        setupAutoCompletePlace()

        binding.toolbar.setOnClickListener {
            navigateToMainActivity()
        }
        binding.addCityButton.setOnClickListener {
            onAddButtonClick()
        }
    }

    private fun setupAutoCompletePlace() {
        if (!Places.isInitialized()) {
            Places.initialize(this, BuildConfig.API_KEY)
        }

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("TAG", "Place: ${place.name}, ${place.id}")
                placeId = place.id as String
                placeName = place.name as String
                initClearButton(autocompleteFragment)
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }

    @SuppressLint("CutPasteId")
    private fun initClearButton(autocompleteFragment: AutocompleteSupportFragment) {
        val clearButton: ImageView? = autocompleteFragment.view
            ?.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_clear_button)
        clearButton?.setOnClickListener {
            clearSearchText()
        }
    }

    private fun clearSearchText() {
        val placeEditText = autocompleteFragment.view
            ?.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input) as EditText
        placeEditText.setText("")
        placeId = ""
        placeName = ""
    }

    private fun hideClearIcon() {
        val clearButton: ImageView? = autocompleteFragment.view
            ?.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_clear_button)
        clearButton?.isVisible = false
    }

    private fun onAddButtonClick() {
        if (placeId.isEmpty() || placeId.isBlank() || placeName.isEmpty() || placeName.isBlank()) {
            val message = resources.getString(R.string.add_city_country_required)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            addCityViewModel.addCity(placeId, placeName)
            val message = resources.getString(R.string.add_city_success)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            clearSearchText()
            hideClearIcon()
        }
    }

    private fun navigateToMainActivity() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}