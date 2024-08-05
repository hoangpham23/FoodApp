package com.example.foodapp.ui

import android.content.Intent
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
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputLayout


class SignUp : AppCompatActivity() {

    private lateinit var btnSignUp: Button
    private var lastToastTime: Long = 0
    private val toastDelayMillis: Long = 3000
//    private lateinit var toast : CustomToast

    // create
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
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

        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        binding.inputEmail.doOnTextChanged { text, _, _, _ ->
            if (!isValidEmail(text.toString())) {
                binding.inputLayoutEmail.error = "Invalid email"
            } else {
                binding.inputLayoutEmail.error = null
            }
        }

        binding.inputConfirmPassword.doOnTextChanged { text, _, _, _ ->
            if (text.toString() != binding.inputPassword.text.toString()) {
                binding.inputLayoutConfirmPassword.error = "Not match"
            } else {
                binding.inputLayoutConfirmPassword.error = null
            }
        }

        btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener {

            val name = binding.inputName.text.toString()
            val layoutName = binding.inputLayoutName

            val email = binding.inputEmail.text.toString()
            val layoutEmail = binding.inputLayoutEmail

            val password = binding.inputPassword.text.toString()
            val layoutPassword = binding.inputLayoutPassword

            val confirmPassword = binding.inputConfirmPassword.text.toString()
            val layoutConfirmPassword = binding.inputLayoutConfirmPassword


            val isNameValid = validateField(layoutName, name)
            val isEmailValid = validateField(layoutEmail, email)
            val isPasswordValid = validateField(layoutPassword, password)
            val isConfirmPasswordValid = validateField(layoutConfirmPassword, confirmPassword)

//            val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout = inflater.inflate(R.layout.custom_toast, null) as LinearLayoutCompat
//            val toast  = Toast(this)
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.setText("Please fill all required fields!")

            // Proceed only if all fields are valid
            if (!isNameValid || !isEmailValid || !isPasswordValid || !isConfirmPasswordValid) {
                // Proceed with sign up logic
                if (!isNameValid) binding.inputName.requestFocus()
                else if (!isEmailValid) binding.inputEmail.requestFocus()
                else if (!isPasswordValid) binding.inputPassword.requestFocus()
                else if (!isConfirmPasswordValid) binding.inputConfirmPassword.requestFocus()

                val currentTime = SystemClock.elapsedRealtime()

                // Check if enough time has passed since the last toast
                if (currentTime - lastToastTime >= toastDelayMillis) {
                    // Show the toast message
                    Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT)
                        .show()
//                    CustomToast(this, "Please fill all required fields!", Toast.LENGTH_SHORT)
//                    toast.show()
                    // Update the last toast time to the current time
                    lastToastTime = currentTime
                }

                return@setOnClickListener
            }

            if (!isValidEmail(email)) {

                Toast.makeText(this, "Please enter correct format!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Please enter correct confirm password!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            Intent(this, MainActivity::class.java).also {
                startActivity(it)
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