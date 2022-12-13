package com.android.mapsproject

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject

class PlacesFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView

    val list = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_places, container, false)

        recyclerView = view.findViewById(R.id.rv)

        if (ActivityCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Activity)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    val queue: RequestQueue = Volley.newRequestQueue(context)

                    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ location!!.latitude+","+location.longitude+"&radius=1500&key=AIzaSyCfnbmPFocbUdcDmToDDYAydxoMD-Rwa5U"

                    val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

                        try {

                            val array:JSONArray =response.getJSONArray("results")
                            for (i in 0 until array.length()) {
                                list.add(array.getJSONObject(i).getString("name"))
                            }

                            recyclerView.setLayoutManager(LinearLayoutManager(context))
                            recyclerView.adapter = RecyclerAdapter(list)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }, { error ->

                    })

                    queue.add(request)


                }
        }




        return view
    }


}