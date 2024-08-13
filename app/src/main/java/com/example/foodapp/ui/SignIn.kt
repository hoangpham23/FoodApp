package com.example.foodapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputLayout

class SignIn : AppCompatActivity() {

    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }


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

        binding.inputEmail.doOnTextChanged { text, _, _, _ ->
            binding.inputLayoutEmail.helperText = ""
            binding.inputLayoutEmail.isHelperTextEnabled = false

            if (!isValidEmail(text.toString())) {
                binding.inputLayoutEmail.error = "Invalid email"
            } else {
                binding.inputLayoutEmail.error = null
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            Intent(this,ForgotPassword::class.java).also {
                startActivity(it)
            }
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val layoutEmail = binding.inputLayoutEmail
            val password = binding.inputPassword.text.toString()
            val layoutPassword = binding.inputLayoutPassword

            val isEmailValid = validateField(layoutEmail, email)
            val isPasswordValid = validateField(layoutPassword, password)

//            if (!isEmailValid || !isPasswordValid) {
//                if (!isEmailValid) binding.inputEmail.requestFocus()
//                else if (!isPasswordValid) binding.inputPassword.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (!(email == "pcm230304@gmail.com" && password == "1234")) {
//                AlertDialog.Builder(this)
//                    .setTitle("Sign In Error")
//                    .setMessage("Invalid email or password. Please try again.")
//                    .setPositiveButton("OK", null)
//                    .show()
//                return@setOnClickListener
//            }

            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
                return@setOnClickListener
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

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}