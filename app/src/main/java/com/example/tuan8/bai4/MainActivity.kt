package com.example.tuan8.bai4

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tuan8.R
import com.example.tuan8.Xe
import com.example.tuan8.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {
    val dsXe = mutableListOf<Xe>(Xe("BMV", "ABC", "1.0", 200000000),
        Xe("GLC", "ABC", "1.0", 200000000),
        Xe("Huyndai", "ABC", "1.0", 200000000))
    val dsHangXe = mutableListOf<String>("BMV", "GLC", "Huyndai")
    private lateinit var adapter: MyAdapter
    private lateinit var binding : ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.toolBar.inflateMenu(R.menu.abc)
        adapter = MyAdapter(this, R.layout.item_lv, dsXe)
        binding.lvXe.adapter = adapter
        binding.sprHangXe.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dsHangXe)
        binding.tvThem.setOnClickListener {
            val hangXe = binding.sprHangXe.selectedItem.toString()
            val dongXe = binding.edtDongXe.text.toString()
            val phienBan = binding.edtPhienBan.text.toString()
            val gia = binding.edtGia.text.toString().toLong()
            val xe = Xe(hangXe, dongXe, phienBan, gia)
            dsXe.add(xe)
            adapter.notifyDataSetChanged()
        }
        binding.toolBar.setOnMenuItemClickListener { item->
            if(item.itemId == R.id.them){
                them()
                true
            }
            else{
                 false
            }
        }
    }
    fun them(){
        val hangXe = binding.sprHangXe.selectedItem.toString()
        val dongXe = binding.edtDongXe.text.toString()
        val phienBan = binding.edtPhienBan.text.toString()
        val gia = binding.edtGia.text.toString().toLong()
        val xe = Xe(hangXe, dongXe, phienBan, gia)
        dsXe.add(xe)
        adapter.notifyDataSetChanged()
    }
}