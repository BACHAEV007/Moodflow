package com.example.moodflow.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.moodflow.presentation.receiver.NotificationReceiver
import java.util.Calendar

class NotificationHelper(
	private val context: Context
) {
	companion object {
		private const val CHANNEL_ID = "settings_channel"
		private const val CHANNEL_NAME = "Settings Notifications"
		private const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
	}

	private val notificationManager =
		context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	fun createChannel() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

		if (notificationManager.getNotificationChannel(CHANNEL_ID) != null) return

		val channel = NotificationChannel(
			CHANNEL_ID,
			CHANNEL_NAME,
			NotificationManager.IMPORTANCE_HIGH
		)

		notificationManager.createNotificationChannel(channel)
	}

	@SuppressLint("ScheduleExactAlarm")
	fun scheduleNotification(id: Int, hour: Int, minute: Int) {
		val intent = Intent(context, NotificationReceiver::class.java).apply {
			putExtra(EXTRA_NOTIFICATION_ID, id)
		}

		val pending = PendingIntent.getBroadcast(
			context,
			id,
			intent,
			PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)

		val calendar = Calendar.getInstance().apply {
			set(Calendar.HOUR_OF_DAY, hour)
			set(Calendar.MINUTE, minute)
			set(Calendar.SECOND, 0)
			if (before(Calendar.getInstance())) {
				add(Calendar.DAY_OF_YEAR, 1)
			}
		}

		val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
		alarmMgr.setExactAndAllowWhileIdle(
			AlarmManager.RTC_WAKEUP,
			calendar.timeInMillis,
			pending
		)
	}

	fun cancelScheduled(id: Int) {
		val intent = Intent(context, NotificationReceiver::class.java)
		val pending = PendingIntent.getBroadcast(
			context, id, intent,
			PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)
		(context.getSystemService(Context.ALARM_SERVICE) as AlarmManager)
			.cancel(pending)
	}
}