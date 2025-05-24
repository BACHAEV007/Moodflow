package com.example.moodflow.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moodflow.R
import com.example.moodflow.databinding.NotificationItemBinding
import com.example.moodflow.domain.model.NotificationModel

class SettingsAdapter(
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    val notifications: MutableList<NotificationModel> = mutableListOf()

    inner class SettingsViewHolder(view: View) : ViewHolder(view) {
        private val binding = NotificationItemBinding.bind(view)
        fun bind(item: NotificationModel) = with(binding) {
            timeText.text = item.time
            deleteButton.setOnClickListener {
                onDeleteClick(item.id)
            }
        }
    }

    fun submitList(newNotification: List<NotificationModel>) {
        notifications.clear()
        notifications.addAll(newNotification)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        )
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
    fun addNotification(time: NotificationModel) {
        notifications.add(time)
        notifyItemInserted(notifications.size - 1)
        notifyDataSetChanged()
    }
}