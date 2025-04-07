package com.example.kanopys.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kanopys.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}