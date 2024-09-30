package com.example.notifeyetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AchievementAdapter(private var achievements: List<Achievement>) : RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvAchievementTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvAchievementDescription)
        val dateTextView: TextView = itemView.findViewById(R.id.tvAchievementDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.achievement_item, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        holder.titleTextView.text = achievement.title
        holder.descriptionTextView.text = achievement.description
        holder.dateTextView.text = achievement.date
    }

    override fun getItemCount(): Int = achievements.size

    fun updateAchievements(newAchievements: List<Achievement>) {
        achievements = newAchievements
        notifyDataSetChanged()
    }
}
