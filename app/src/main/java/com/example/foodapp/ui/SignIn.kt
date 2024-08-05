package com.example.foodapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.transition.Visibility
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class SignIn : AppCompatActivity() {

    private val binding : ActivitySignInBinding by lazy{
        ActivitySignInBinding.inflate(layoutInflater)
    }

    private lateinit var btnSignIn: Button
    private var lastToastTime: Long = 0
    private val toastDelayMillis: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvSignUp.setOnClickListener {
            Intent(this, SignUp::class.java).also {
                startActivity(it)
            }
        }

        binding.inputEmail.doOnTextChanged { text, start, before, count ->
            if (!isValidEmail(binding.inputEmail.text.toString())){
                binding.inputLayoutEmail.error = "Invalid email"
            }else{
                binding.inputLayoutEmail.error = null
            }
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val layoutEmail = binding.inputLayoutEmail
            val password = binding.inputPassword.text.toString()
            val layoutPassword = binding.inputLayoutPassword

            val isEmailValid = validateField(layoutEmail, email)
            val isPasswordValid = validateField(layoutPassword, password)

            if (!isEmailValid || !isPasswordValid){
                if (!isEmailValid) binding.inputEmail.requestFocus()
                else if (!isPasswordValid) binding.inputPassword.requestFocus()
            }

            val currentTime = SystemClock.elapsedRealtime()

            if (currentTime - lastToastTime >= toastDelayMillis){
                Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()

                // Update the last toast time to the current time
                lastToastTime = currentTime
            }

        }
    }

    private fun validateField(layout: TextInputLayout, text: String): Boolean {
        if (text.isEmpty()) {
            // string.required = "Required*"
            layout.helperText = getString(R.string.required)
            layout.isHelperTextEnabled = true
            return false
        } else {
            layout.helperText = ""
            layout.isHelperTextEnabled = false
            return true
        }
    }

    private fun isValidEmail(email : String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}