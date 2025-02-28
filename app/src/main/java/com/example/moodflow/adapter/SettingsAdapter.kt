package com.example.moodflow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moodflow.R
import com.example.moodflow.databinding.NotificationItemBinding

class SettingsAdapter(
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    val notifications: MutableList<String> = mutableListOf("20:00", "19:00")
    inner class SettingsViewHolder(view: View) : ViewHolder(view){
        private val binding = NotificationItemBinding.bind(view)
        fun bind(item: String) = with(binding){
            timeText.text = item
            deleteButton.setOnClickListener {
                onDeleteClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SettingsViewHolder {
        return SettingsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false))
    }

    override fun getItemCount(): Int = notifications.size


    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        if (position in 0 until notifications.size) {
            notifications.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNotification(time: String) {
        notifications.add(time)
        notifyItemInserted(notifications.size - 1)
        notifyDataSetChanged()
    }
}