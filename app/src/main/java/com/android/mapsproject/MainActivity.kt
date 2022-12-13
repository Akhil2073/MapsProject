package com.android.mapsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MapsFragment>(R.id.fragment_container_view)
        }
        setTitle("Map")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.menu_map) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MapsFragment>(R.id.fragment_container_view)
            }
            setTitle("Map")
            return true
        }
        if (id == R.id.menu_places) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<PlacesFragment>(R.id.fragment_container_view)
            }
            setTitle("NearBy Places")
            return true
        }
        if (id == R.id.menu_email) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SendEmail>(R.id.fragment_container_view)
            }
            setTitle("Email")
            return true
        }
        if (id == R.id.menu_about) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AboutFragment>(R.id.fragment_container_view)
            }
            setTitle("About App")
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}