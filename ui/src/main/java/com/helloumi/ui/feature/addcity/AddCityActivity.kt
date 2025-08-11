package com.helloumi.ui.feature.addcity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.helloumi.ui.BuildConfig.PLACES_API_KEY
import com.helloumi.ui.feature.main.MainActivity
import com.helloumi.ui.R
import com.helloumi.ui.databinding.ActivityAddCityBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class AddCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCityBinding

    // viewModels() delegate used to get
    // by view models will automatically construct the viewModels using Hilt
    private val addCityViewModel: AddCityViewModel by viewModels()

    // Initialize the AutocompleteSupportFragment.
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    // Location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    // Permission launcher
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Permission granted, get location and update UI
                getCurrentLocation()
            }

            else -> {
                Toast.makeText(
                    this,
                    getString(R.string.locating_permission_required),
                    Toast.LENGTH_LONG
                ).show()
                // Set hint without location functionality
                setBasicHint()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val color = ContextCompat.getColor(this, R.color.purple_40)
        window.statusBarColor = color // Set color of system statusBar same as ActionBar

        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())

        setupAutoCompletePlace()

        binding.toolbar.setOnClickListener {
            navigateToMainActivity()
        }
        binding.addCityButton.setOnClickListener {
            onAddButtonClick()
        }
        binding.locationButton.setOnClickListener {
            onLocationButtonClick()
        }
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////   internal methods   //////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    private fun setupAutoCompletePlace() {
        if (!Places.isInitialized()) {
            Places.initialize(this, PLACES_API_KEY)
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
                addCityViewModel.placeId.value = place.id as String
                addCityViewModel.placeName.value = place.name as String
                initClearButton(autocompleteFragment)
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })

        // Set initial hint
        setBasicHint()
    }

    private fun onLocationButtonClick() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, get location
                getCurrentLocation()
            }

            else -> {
                // Request permission
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
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
        val placeEditText = autocompleteFragment.view?.findViewById<EditText>(
            com.google.android.libraries.places.R.id.places_autocomplete_search_input
        )
        placeEditText?.setText(null)
        addCityViewModel.placeId.value = ""
        addCityViewModel.placeName.value = ""
    }

    private fun hideClearIcon() {
        val clearButton: ImageView? = autocompleteFragment.view
            ?.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_clear_button)
        clearButton?.isVisible = false
    }

    private fun onAddButtonClick() {
        if (addCityViewModel.placeId.value.isEmpty() || addCityViewModel.placeId.value.isBlank() ||
            addCityViewModel.placeName.value.isEmpty() || addCityViewModel.placeName.value.isBlank()
        ) {
            val message = resources.getString(R.string.add_city_country_required)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            addCityViewModel.addCity(
                addCityViewModel.placeId.value,
                addCityViewModel.placeName.value
            )

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

    /////////////////////////////////////////////////////////////////////////
    /////////////////////   location methods   //////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Show progress and update button text
        showLocationProgress(true)

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                // Hide progress
                showLocationProgress(false)

                location?.let {
                    getAddressFromLocation(it)
                } ?: run {
                    Toast.makeText(
                        this,
                        getString(R.string.unable_to_get_current_location),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                // Hide progress
                showLocationProgress(false)

                Log.e("Location", getString(R.string.unable_to_get_current_location), exception)
                Toast.makeText(
                    this,
                    "${getString(R.string.location_error)}: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun showLocationProgress(show: Boolean) {
        val progressBar = binding.locationProgressBar
        val locationButton = binding.locationButton

        if (show) {
            progressBar.visibility = View.VISIBLE
            locationButton.text = getString(R.string.locating_in_progress)
            locationButton.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            locationButton.text = getString(R.string.locate_me_button)
            locationButton.isEnabled = true
        }
    }

    private fun getAddressFromLocation(location: Location) {
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val cityName =
                    address.locality ?: address.adminArea ?: getString(R.string.current_location)
                val placeId = "LOC_${location.latitude}_${location.longitude}"

                // Update the view model with location data
                addCityViewModel.placeId.value = placeId
                addCityViewModel.placeName.value = cityName

                // Update the autocomplete fragment text to show current location
                updateAutocompleteText("📍 $cityName (${getString(R.string.current_location)})")

                // Show success message
                Toast.makeText(
                    this,
                    "${getString(R.string.location_found)}: $cityName",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.unable_to_get_current_location),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Log.e("Geocoder", getString(R.string.unable_to_get_current_location), e)
            Toast.makeText(
                this,
                "${getString(R.string.unable_to_get_current_location)}: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateAutocompleteText(text: String) {
        val placeEditText = autocompleteFragment.view?.findViewById<EditText>(
            com.google.android.libraries.places.R.id.places_autocomplete_search_input
        )
        placeEditText?.setText(text)
        // Set cursor position at the end
        placeEditText?.setSelection(text.length)
    }

    private fun setBasicHint() {
        val placeEditText = autocompleteFragment.view?.findViewById<EditText>(
            com.google.android.libraries.places.R.id.places_autocomplete_search_input
        )
        placeEditText?.hint = getString(R.string.search_city_hint)
    }
}
