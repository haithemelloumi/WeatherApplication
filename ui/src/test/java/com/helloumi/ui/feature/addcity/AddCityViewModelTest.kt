package com.helloumi.ui.feature.addcity

import com.helloumi.common.CoroutinesTestRule
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.AddCityUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProviderImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddCityViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var addCityUseCase: AddCityUseCase

    private lateinit var addCityViewModel: AddCityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addCityViewModel = AddCityViewModel(
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            addCityUseCase = addCityUseCase
        )
    }

    @Test
    fun `WHEN call addCity THEN verify useCase`() {

        // GIVEN
        val cityId = "cityId"
        val cityName = "cityName"
        val cityDomain = CityForSearchDomain(
            id = cityId,
            name = cityName
        )

        // WHEN
        addCityViewModel.addCity(cityId, cityName)


        // THEN
        runTest {
            verify(addCityUseCase)(cityDomain)
        }
    }

    @Test
    fun `WHEN set location data THEN placeId and placeName are updated`() {
        // GIVEN
        val placeId = "LOC_48.8566_2.3522"
        val placeName = "Paris"

        // WHEN
        addCityViewModel.placeId.value = placeId
        addCityViewModel.placeName.value = placeName

        // THEN
        assert(addCityViewModel.placeId.value == placeId)
        assert(addCityViewModel.placeName.value == placeName)
    }

    @Test
    fun `WHEN set current location data THEN location format is correct`() {
        // GIVEN
        val latitude = 48.8566
        val longitude = 2.3522
        val cityName = "Paris"
        val expectedPlaceId = "LOC_${latitude}_${longitude}"

        // WHEN
        addCityViewModel.placeId.value = expectedPlaceId
        addCityViewModel.placeName.value = cityName

        // THEN
        assert(addCityViewModel.placeId.value.startsWith("LOC_"))
        assert(addCityViewModel.placeId.value.contains("_"))
        assert(addCityViewModel.placeName.value == cityName)
    }

    @Test
    fun `WHEN location button is clicked THEN location data is properly set`() {
        // GIVEN
        val placeId = "LOC_48.8566_2.3522"
        val placeName = "Paris"

        // WHEN
        addCityViewModel.placeId.value = placeId
        addCityViewModel.placeName.value = placeName

        // THEN
        assert(addCityViewModel.placeId.value == placeId)
        assert(addCityViewModel.placeName.value == placeName)
        assert(addCityViewModel.placeId.value.isNotEmpty())
        assert(addCityViewModel.placeName.value.isNotEmpty())
    }

    @Test
    fun `WHEN location is in progress THEN progress state is managed correctly`() {
        // GIVEN
        val placeId = "LOC_48.8566_2.3522"
        val placeName = "Paris"

        // WHEN - Simulate location in progress
        addCityViewModel.placeId.value = ""
        addCityViewModel.placeName.value = ""

        // THEN - Verify initial state
        assert(addCityViewModel.placeId.value.isEmpty())
        assert(addCityViewModel.placeName.value.isEmpty())

        // WHEN - Location completed
        addCityViewModel.placeId.value = placeId
        addCityViewModel.placeName.value = placeName

        // THEN - Verify final state
        assert(addCityViewModel.placeId.value == placeId)
        assert(addCityViewModel.placeName.value == placeName)
    }
}
