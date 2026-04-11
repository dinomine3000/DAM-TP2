package dam_A51728.coolweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val day = isDayTheme()
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                if (day) {
                    setTheme(R.style.Theme_Day)
                } else {
                    setTheme(R.style.Theme_Night)
                }
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                if (day) {
                    setTheme(R.style.Theme_Day_Land)
                } else {
                    setTheme(R.style.Theme_Night_Land)
                }
            }
        }
        setContentView(R.layout.activity_main)
        val viewModel: WeatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        if(viewModel.weatherData == null)
            getDeviceLocationValues()
        val updateButton: Button = findViewById(R.id.updateButton)
        val clickListener: View.OnClickListener = View.OnClickListener {
            fetchWeatherData(getLatitudeValue(), getLongitudeValue(), viewModel).start()
        }
        updateButton.setOnClickListener(clickListener)

        if(viewModel.weatherData == null)
            fetchWeatherData(getLatitudeValue(), getLongitudeValue(), viewModel).start()
        else updateUI(viewModel.weatherData!!)
    }

    private fun getDeviceLocationValues(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            findViewById<EditText>(R.id.latitudeInput).setText(location.latitude.toString())
                            findViewById<EditText>(R.id.longitudeInput).setText(location.longitude.toString())
                        } else {
                            findViewById<EditText>(R.id.longitudeInput).setText("38.75")
                            findViewById<EditText>(R.id.latitudeInput).setText("-9.125")
                        }
                    }
                }
            } else {
                findViewById<EditText>(R.id.longitudeInput).setText("38.75")
                findViewById<EditText>(R.id.latitudeInput).setText("-9.125")
            }
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun WeatherAPI_Call(lat: Float, long: Float): WeatherData {
        val reqString = buildString{
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            append("current=temperature_2m,is_day,wind_speed_10m,wind_direction_10m,weather_code,pressure_msl")
            append("&forecast_days=1")
        }
        val url = URL(reqString)
        url.openStream().use{
            val request = Gson().fromJson(InputStreamReader(it, "UTF-8"), WeatherData::class.java)
            return request
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float, weatherViewModel: WeatherViewModel): Thread{
        return Thread{
            val weather = WeatherAPI_Call(lat, long)
            weatherViewModel.weatherData = weather
            updateUI(weather)
        }
    }

    private fun getLatitudeValue(): Float{
        var latInput: String = findViewById<EditText>(R.id.latitudeInput).text.toString()
        if(latInput.isEmpty() || latInput.isBlank()) latInput = "0"
        return latInput.toFloatOrNull() ?: 0f
    }

    private fun getLongitudeValue(): Float{
        var longInput: String = findViewById<EditText>(R.id.longitudeInput).text.toString()
        if(longInput.isEmpty() || longInput.isBlank()) longInput = "0"
        return longInput.toFloatOrNull() ?: 0f
    }

    private fun updateUI(request: WeatherData){
        runOnUiThread{
            val weatherImage: ImageView = findViewById(R.id.weatherImage)
            val windDirection: TextView = findViewById(R.id.windDirValue)
            val windSpeed: TextView = findViewById(R.id.windSpeedValue)
            val temperature: TextView = findViewById(R.id.temperatureValue)
            val pressure: TextView = findViewById(R.id.pressureValue)
            val time: TextView = findViewById(R.id.timeValue)
            val timezone: TextView = findViewById(R.id.timezoneValue)

            windDirection.text = buildString {
                append(request.current.wind_direction_10m.toString())
                append(" ")
                append(request.current_units.wind_direction_10m)
            }
            windSpeed.text = buildString {
                append(request.current.wind_speed_10m.toString())
                append(" ")
                append(request.current_units.wind_speed_10m)
            }
            temperature.text = buildString {
                append(request.current.temperature_2m.toString())
                append(" ")
                append(request.current_units.temperature_2m)
            }
            pressure.text = buildString {
                append(request.current.pressure_msl.toString())
                append(" ")
                append(request.current_units.pressure_msl)
            }
            time.text = request.current.time
            timezone.text = request.timezone

            val isDay: Boolean = request.current.is_day == 1
            val mapt = getWeatherCodeMap()
            val wImage = when(val wCode = mapt[request.current.weather_code]){
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY -> if(isDay) wCode.image +"day" else wCode.image+"night"
                else -> wCode?.image
            }
            val res = getResources()
            weatherImage.setImageResource(R.drawable.fog)
            val resId = res.getIdentifier(wImage, "drawable", getPackageName());
            val drawable = this.getDrawable(resId)
            weatherImage.setImageDrawable(drawable)

            if (isDay != isDayTheme()) {
                saveTheme(isDay)
                recreate() //this destroys and restarts the activity to update the themes
            }
        }

    }

    private fun saveTheme(isDay: Boolean) {
        val sharedPref = getSharedPreferences("WeatherPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("IS_DAY", isDay)
            apply()
        }
    }

    private fun isDayTheme(): Boolean {
        val sharedPref = getSharedPreferences("WeatherPrefs", MODE_PRIVATE)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val defaultDay = hour in 6..18
        return sharedPref.getBoolean("IS_DAY", defaultDay) // Default to true (Day)
    }
}
