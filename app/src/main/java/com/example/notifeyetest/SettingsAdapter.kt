package com.example.notifeyetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SettingsAdapter(private val settingsList: List<String>, private val clickListener: (String) -> Unit) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val settingName: TextView = itemView.findViewById(R.id.tvSettingName)

        fun bind(setting: String) {
            settingName.text = setting
            itemView.setOnClickListener { clickListener(setting) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(settingsList[position])
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }
}
