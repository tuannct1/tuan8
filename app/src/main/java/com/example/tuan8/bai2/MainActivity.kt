package com.example.tuan8.bai2

import android.app.appsearch.GlobalSearchSession
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tuan8.R
import com.example.tuan8.User
import com.example.tuan8.bai1.DangKyActivity
import com.example.tuan8.databinding.ActivityMain2Binding
import com.google.gson.Gson
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding

    private lateinit var file : SharedPreferences
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result->
        val intent = result.data
        val email = intent?.getStringExtra("email")?: ""
        val password = intent?.getStringExtra("password")?: ""
        val json = Gson().toJson(User(email, password))
        file.edit {
            putString(email, json)
        }
        Log.d("Tes", file.getString(email, "")?: "")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        file = getSharedPreferences("user", MODE_PRIVATE)

        if(file.contains("remember")){
            val user = Gson().fromJson(file.getString("remember", ""), User::class.java)
            binding.edtEmail.setText(user.email)
            binding.edtPassword.setText(user.password)
            binding.cbRemember.isChecked = true
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            if(file.contains(email)){
                Log.d("Tes", "a")
                val json = file.getString(email,"")
                val user = Gson().fromJson(json, User::class.java)
                if(user.email == binding.edtEmail.text.toString() && user.password == binding.edtPassword.text.toString()){
                    Toast.makeText(this, "Chúc mừng bạn đăng nhập thành công", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, DangKyActivity::class.java)
            launcher.launch(intent)
        }
        binding.cbRemember.setOnCheckedChangeListener { button, bool ->
            if(bool){
                val user = User(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
                file.edit {
                    putString("remember", Gson().toJson(user))
                }
            }
            else{
                file.edit {
                    remove("remember")
                }
            }
        }
    }
}