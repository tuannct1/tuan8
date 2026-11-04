package com.example.tuan8.bai3

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tuan8.R
import com.example.tuan8.databinding.ActivityMain3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMain3Binding
    val loaiFile = mutableListOf("txt", "jpg", "jpeg", "png", "webp")
    val result = mutableListOf<File>()
    val adapter = MyAdapter(result)
    @Volatile
    private var paused = true
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.btnTimKiem.setOnClickListener {
            if(Environment.isExternalStorageManager()){
                scan()
            }
            else{
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                launcher.launch(intent)
            }
            binding.btnTimKiem.visibility = View.GONE
            binding.btnTamDung.visibility = View.VISIBLE
            paused = !paused
        }

        binding.rcv.adapter = adapter
        binding.rcv.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        binding.btnTamDung.setOnClickListener {
            binding.btnTimKiem.visibility = View.VISIBLE
            binding.btnTamDung.visibility = View.GONE
            paused = !paused
        }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (Environment.isExternalStorageManager()){
            scan()
        }
        else{
//            Toast.makeText(this, "Phai cap quyen", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun scan(){
        val file = getSdRoot()
        if(file == null){
            Toast.makeText(this, "Không tìm thấy thẻ SD", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            scanFolder(file, loaiFile, result)
        }
    }
    suspend fun scanFolder(folder: File, loaiFile: List<String>, result: MutableList<File>){
        val dsFileCon = folder.listFiles()?: return
        for(file in dsFileCon){
            while (paused) {
                delay(100L.milliseconds)
            }
            if(file.isDirectory){
                scanFolder(file, loaiFile, result)
            }
            else{
                val loai = file.extension.lowercase()
                if(loai in loaiFile || true) {
                    Thread.sleep(1000L)
                    result.add(file)
                    val totalSpace ="Tổng dung lượng:" + "%.4f GB".format(file.totalSpace/ (1024.0 * 1024.0 * 1024.0))
                    val freeSpace = "Dung lượng trống:" +"%.4f GB".format(file.freeSpace/ (1024.0 * 1024.0 * 1024.0))
                    val totalFileScaned = "Số file đã quét:" + result.size.toString()
                    runOnUiThread { adapter.notifyItemInserted(result.size - 1)
                        binding.tvDungLuongTrong.text = totalSpace
                        binding.tvDungLuongBoNho.text = freeSpace
                        binding.tvSoFileDaQuet.text = totalFileScaned
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getSdRoot(): File?{
        val sm = getSystemService(StorageManager::class.java)
        val volumes = sm.storageVolumes
        for(vol in volumes){
            if(vol.isRemovable && vol.state == Environment.MEDIA_MOUNTED){
                return vol.directory
            }
        }
        return null
    }
    }