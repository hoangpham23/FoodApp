package com.example.foodapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityForgotPasswordBinding
import com.google.android.material.textfield.TextInputLayout

class ForgotPassword : AppCompatActivity() {

    private val binding : ActivityForgotPasswordBinding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

//    private lateinit var email: String
//    private lateinit var layoutEmail : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.inputEmail.doOnTextChanged { text, _, _, _ ->
            binding.inputLayoutEmail.helperText = ""
            binding.inputLayoutEmail.isHelperTextEnabled = false
            if (!isValidEmail(text.toString())){
                binding.inputLayoutEmail.error = "Invalid email"
            }else{
                binding.inputLayoutEmail.error = null
            }
        }

        binding.iconBack.setOnClickListener {
            Intent(this, SignIn::class.java).also {
                startActivity(it)
            }
        }

        binding.btnSend.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val layoutEmail = binding.inputLayoutEmail

            if(!validateField(layoutEmail, email)){
                binding.inputEmail.requestFocus()
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

    private fun isValidEmail(email : String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}