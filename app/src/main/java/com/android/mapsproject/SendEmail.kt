package com.android.mapsproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.SharedPreferences
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class SendEmail : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationObj: Location
    private lateinit var email: EditText
    private lateinit var send: Button
    val sharedPref = "email"

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_send_email, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Activity)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                locationObj=location
            }
        email=view.findViewById(R.id.email)
        send=view.findViewById(R.id.send)

        val sharedPreferences: SharedPreferences = (context as Activity).getSharedPreferences(sharedPref,0)
        val emailS = sharedPreferences.getString("email","")
        email.setText(emailS)
        send.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("email",email.text.toString())
                editor.apply()

                val geoCoder = Geocoder(
                    context,
                    Locale.getDefault()
                )
                var result: String = null.toString()
                val addressList = geoCoder.getFromLocation(
                    locationObj.latitude, locationObj.longitude, 1
                )
                if ((addressList != null && addressList.size > 0)) {
                    val address = addressList.get(0)
                    val sb = StringBuilder()
                    for (i in 0 until address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append("\n")
                    }
                    sb.append(address.locality).append("\n")
                    sb.append(address.postalCode).append("\n")
                    sb.append(address.countryName)
                    result = sb.toString()
                }


                val uriText = "mailto:"+email.text+
                        " ?subject=" + "your subject here" +
                        "&body=Lat : " + locationObj.latitude+"\nLong : "+locationObj.longitude+"\nAddress : "+result
                val uri = Uri.parse(uriText)
                val sendIntent = Intent(Intent.ACTION_SENDTO)
                sendIntent.data = uri
                startActivity(Intent.createChooser(sendIntent, "Send Email").addFlags(FLAG_ACTIVITY_NEW_TASK))
            }

        })

        return view
    }
}