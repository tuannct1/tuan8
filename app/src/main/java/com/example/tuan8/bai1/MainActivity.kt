package com.example.tuan8.bai1

import android.content.Intent
import android.os.Build
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
import com.example.tuan8.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var file: File
    private lateinit var fileRemember : File
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result->
        val intent = result.data
        val email = intent?.getStringExtra("email")?: ""
        val password = intent?.getStringExtra("password")?: ""
        val json = Gson().toJson(User(email, password))
        file.appendText(json)
        file.appendText("\n")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Tạo đường dẫn file
        file = File(this.filesDir, "user.txt")
        //Tạo file remember cho nút nhớ
        fileRemember = File(this.filesDir, "remember.txt")
        //set text cho các ô nhập tài khoản
        if(fileRemember.exists() && fileRemember.readText() != "" ){
            val user = Gson().fromJson(fileRemember.readText(), User::class.java)
            binding.edtEmail.setText(user.email)
            binding.edtPassword.setText(user.password)
            binding.cbRemember.isChecked = true
        }
        //Xử lý nút login
        binding.btnLogin.setOnClickListener {
            if(file.exists()){
                val json = file.readLines()

                for(i in json){
                    val user = Gson().fromJson(i, User::class.java)
                    if(user.email == binding.edtEmail.text.toString() && user.password == binding.edtPassword.text.toString()){
                        Toast.makeText(this, "Chúc mừng bạn đăng nhập thành công", Toast.LENGTH_LONG).show()
                        break
                    }
                }
            }
        }
        //Xử lý nút signup
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, DangKyActivity::class.java)
            launcher.launch(intent)
        }
        //Xử lý nút nhớ
        binding.cbRemember.setOnCheckedChangeListener { button, bool ->
            if(bool){
                val user = User(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
                fileRemember.writeText(Gson().toJson(user))
            }
            else{
                fileRemember.writeText("")
            }
        }
    }
}