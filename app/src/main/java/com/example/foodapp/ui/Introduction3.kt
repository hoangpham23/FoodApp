package com.example.foodapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityIntroduction3Binding


class Introduction3 : AppCompatActivity() {

    private val binding : ActivityIntroduction3Binding by lazy {
        ActivityIntroduction3Binding.inflate(layoutInflater)
    }

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSkip.setOnClickListener {
            handler.removeCallbacks(runnable)
            Intent(this, SignIn::class.java).also {
                startActivity(it)
                finish()
            }
        }

        runnable = Runnable {
            Intent(this, SignIn::class.java).also {
                startActivity(it)
                finish()
            }
        }

        handler.postDelayed(runnable, 3000)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}