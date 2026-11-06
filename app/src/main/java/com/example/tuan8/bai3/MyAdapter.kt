package com.example.tuan8.bai3

//noinspection SuspiciousImport
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tuan8.R
import java.io.File

class MyAdapter(private val ds: MutableList<File>) : RecyclerView.Adapter<MyAdapter.VH>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val b = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return VH(b)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val fl = ds[position]
        holder.tenFile.text = fl.nameWithoutExtension
        holder.dungLuong.text = "%.2f KB".format(fl.length()/ 1024.0)
        holder.duongDan.text = fl.absolutePath.toString()
        when(fl.extension.lowercase()){
            "txt"->holder.icon.setImageResource(R.drawable.txt_document_svgrepo_com)
            "jpg"->holder.icon.setImageResource(R.drawable.img_box_svgrepo_com)
            "webp"->holder.icon.setImageResource(R.drawable.facebooklogo)
            "jpeg"->holder.icon.setImageResource(R.drawable.insert_word_svgrepo_com)
            "docx"->holder.icon.setImageResource(R.drawable.insert_word_svgrepo_com)
            else->holder.icon.setImageResource(R.drawable.help_svgrepo_com)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tenFile = itemView.findViewById<TextView>(R.id.tvTenFile)
        val duongDan = itemView.findViewById<TextView>(R.id.tvDuongDan)
        val dungLuong = itemView.findViewById<TextView>(R.id.tvDungLuong)
        val icon = itemView.findViewById<ImageView>(R.id.imgIcon)
    }
}