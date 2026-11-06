package com.example.tuan8.bai4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tuan8.R
import com.example.tuan8.Xe

class MyAdapter(context: Context, resource: Int, dsXe : List<Xe>) : ArrayAdapter<Xe>(context, resource, dsXe) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false)
        val xe = getItem(position)
        val dongXe = view.findViewById<TextView>(R.id.tvDongXe)
        val phienBan = view.findViewById<TextView>(R.id.tvPhienBan)
        val hangXe = view.findViewById<TextView>(R.id.tvHangXe)
        val gia = view.findViewById<TextView>(R.id.tvGia)
        val logo = view.findViewById<ImageView>(R.id.logo)

        dongXe.text = xe?.dongXe.toString()
        phienBan.text = xe?.phienBan.toString()
        hangXe.text = xe?.hangXe.toString()
        gia.text = xe?.gia.toString()
        when(xe?.hangXe.toString()){
            "BMV"-> logo.setImageResource(R.drawable.facebooklogo)
            "GLC"-> logo.setImageResource(R.drawable.googlelogo)
            "Huyndai"-> logo.setImageResource(R.drawable.waving_hand_svgrepo_com)
        }
        return view
    }
}