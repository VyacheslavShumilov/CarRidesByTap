package com.vshum.carbytap2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vshum.carbytap2.databinding.ActivityMainBinding
import com.vshum.carbytap2.ui.FragmentCarTrajectory

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, FragmentCarTrajectory())
            fragmentTransaction.commit()
        }
    }
}