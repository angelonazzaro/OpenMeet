package com.openmeet.logic

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.interest.InterestProxyDAO


class Registration2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_2)

        val instrTxt = findViewById<TextView>(R.id.instructionTxt)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val email = intent.getStringExtra("email").toString()

        val snackbarView = findViewById<View>(R.id.auth_reg2_container)

        instrTxt.text = "Aggiungi dai 3 ai 6 interessi."

        //doImageUploadPhase()
        //doInterestPhase()

        val rips = doGetPosition()
        if(rips == null) {
            Snackbar.make(snackbarView, R.string.warn_title, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_dialog) {
                    doGetPosition()
                }
                .show()
        }

        continueBtn.setOnClickListener {
            Toast.makeText(this, getSelectedCheckboxes().toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun doInterestPhase(){

        val snackbarView = findViewById<View>(R.id.auth_reg2_container)

        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)


        Thread {

            val ret = InterestProxyDAO(this).doRetrieveAll()

            if(ret == null)
                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_INDEFINITE).show()
            else{
                runOnUiThread {
                    for (interest in ret){
                        val check = CheckBox(this)
                        check.text = interest.description
                        interestLayout.addView(check)
                    }
                }

            }

        }.start()


        val filterField = findViewById<TextInputLayout>(R.id.filterField)
        filterField.editText?.doOnTextChanged { text, start, before, count ->
            if(text != null){
                for (checkbox in interestLayout.children)
                    if(checkbox is CheckBox) {
                        val textWords = checkbox.text.split(" ")
                        for(word in textWords){
                            if(word.length > text.length) {
                                if (word.substring(0, text.length)
                                        .equals(text.toString(), ignoreCase = true)
                                )
                                    checkbox.visibility = View.VISIBLE
                                else
                                    checkbox.visibility = View.GONE
                            }
                        }
                    }
            }

        }
    }

    fun getSelectedCheckboxes(): MutableList<String>? {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)

        val checkList = mutableListOf<String>()


        for (checkbox in interestLayout.children)
            if(checkbox is CheckBox && checkbox.isChecked)
                checkList.add(checkbox.text.toString())

        return checkList
    }

    fun doBiographyPhase(){

    }

    fun doImageUploadPhase(){

        val imageLayout = findViewById<ImageView>(R.id.imageView2)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                imageLayout.setImageURI(uris[uris.size - 1])
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


    }

    //Da spostare in home. Per ora qui per fare test.
    fun doGetPosition(): String? {

        val snackbarView = findViewById<View>(R.id.auth_reg2_container)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Snackbar.make(snackbarView, "Dai il permesso", Snackbar.LENGTH_SHORT).show()

            //onRequestPermissionsResult(420, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), r )
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 420)
            return ""
        }
        /*else
            Snackbar.make(snackbarView, "Permesso concesso", Snackbar.LENGTH_SHORT).show()*/

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(!checkGPSEnabled(locationManager))
            return null

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude

                //Snackbar.make(snackbarView, "$latitude $longitude", Snackbar.LENGTH_SHORT).show()
                Toast.makeText(this@Registration2Activity, "$latitude $longitude", Toast.LENGTH_SHORT ).show()
                locationManager.removeUpdates(this)

                Toast.makeText(this@Registration2Activity, "Qui ci arrivo2", Toast.LENGTH_SHORT ).show()
                if(Build.VERSION.SDK_INT < 33){

                    val addr = Geocoder(this@Registration2Activity).getFromLocation(latitude, longitude, 1)

                        Snackbar.make(snackbarView, addr.toString(), Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Geocoder(this@Registration2Activity).getFromLocation(latitude, longitude, 1){
                        Snackbar.make(snackbarView, it[0].locality, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            // other overrides
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1f, locationListener)

        return "wow"
    }

    fun checkGPSEnabled(lm: LocationManager): Boolean{

        var gpsEnabled = false

        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)


        if (!gpsEnabled) {
            // notify user
            AlertDialog.Builder(this)
                .setTitle(R.string.warn_title)
                .setMessage(R.string.GPS_disabled_message)
                .setPositiveButton(R.string.positive_dialog
                ) { paramDialogInterface, paramInt -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .show()
        }
        else
            return true

        return false
    }
}