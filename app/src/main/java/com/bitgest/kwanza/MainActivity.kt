package com.bitgest.kwanza

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bitgest.kwanza.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.financa) {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            else{
                binding.bottomNavigation.visibility = View.GONE
            }
            if (destination.id == R.id.solucoes){
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            if (destination.id == R.id.planejamentoFragment){
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            if (destination.id == R.id.planoFragment){
                binding.bottomNavigation.visibility = View.VISIBLE
            }


        }

    }

}
