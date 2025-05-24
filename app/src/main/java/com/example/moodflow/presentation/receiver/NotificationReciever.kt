package com.example.moodflow.presentation.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.moodflow.R
import com.example.moodflow.utils.Constants.NOTIFICATION_BODY
import com.example.moodflow.utils.Constants.REMINDER

class NotificationReceiver : BroadcastReceiver() {
	override fun onReceive(ctx: Context, intent: Intent) {
		val id = intent.getIntExtra("extra_notification_id", 0)
		val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		val notif = NotificationCompat.Builder(ctx, "settings_channel")
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setContentTitle(REMINDER)
			.setContentText(NOTIFICATION_BODY)
			.setAutoCancel(true)
			.build()
		nm.notify(id, notif)
	}

}