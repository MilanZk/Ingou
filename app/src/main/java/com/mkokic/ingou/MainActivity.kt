package com.mkokic.ingou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

    }

    fun setupViews()
    {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.fragNavHost)

        fab_add_ingou.setOnClickListener {
            navController.navigate(R.id.addIngouTextFragment)
        }

        // Setting Navigation Controller with the BottomNavigationView
     //   bottomNavigationView.setupWithNavController(navController)

        // Setting Up ActionBar with Navigation Controller
  //      setupActionBarWithNavController(navController)
    }
}
