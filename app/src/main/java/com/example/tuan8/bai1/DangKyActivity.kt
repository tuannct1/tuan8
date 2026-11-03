package com.example.tuan8.bai1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tuan8.R
import com.example.tuan8.databinding.ActivityDangKiBinding

class DangKyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDangKiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDangKiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent()
            intent.putExtra("email", binding.edtEmail.text.toString())
            intent.putExtra("password", binding.edtPassword.text.toString())
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}