package com.example.moodflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.domain.usecase.AddNotificationUseCase
import com.example.moodflow.domain.usecase.CancelAllNotificationUseCase
import com.example.moodflow.domain.usecase.FetchNotificationsUseCase
import com.example.moodflow.domain.usecase.FetchUserUseCase
import com.example.moodflow.domain.usecase.GetFingerprintEnabledUseCase
import com.example.moodflow.domain.usecase.GetNotificationsEnabledUseCase
import com.example.moodflow.domain.usecase.RemoveNotificationUseCase
import com.example.moodflow.domain.usecase.RescheduleNotificationsUseCase
import com.example.moodflow.domain.usecase.SetFingerprintEnabledUseCase
import com.example.moodflow.domain.usecase.SetNotificationsEnabledUseCase
import com.example.moodflow.presentation.state.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
	private val fetchUserUseCase: FetchUserUseCase,
	private val fetchNotificationsUseCase: FetchNotificationsUseCase,
	private val addNotificationUseCase: AddNotificationUseCase,
	private val removeNotificationUseCase: RemoveNotificationUseCase,
	private val getNotificationsEnabledUseCase: GetNotificationsEnabledUseCase,
	private val setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase,
	private val getFingerprintEnabledUseCase: GetFingerprintEnabledUseCase,
	private val setFingerprintEnabledUseCase: SetFingerprintEnabledUseCase,
	private val cancelAllNotificationUseCase: CancelAllNotificationUseCase,
	private val rescheduleNotificationsUseCase: RescheduleNotificationsUseCase,
) : ViewModel() {

	private val _state = MutableStateFlow(SettingsState())
	val state: StateFlow<SettingsState> = _state.asStateFlow()

	val notificationsEnabled = getNotificationsEnabledUseCase()
		.stateIn(viewModelScope, SharingStarted.Lazily, false)

	val fingerprintEnabled = getFingerprintEnabledUseCase()
		.stateIn(viewModelScope, SharingStarted.Lazily, false)

	fun toggleNotifications(enabled: Boolean) {
		viewModelScope.launch {
			setNotificationsEnabledUseCase(enabled)
			state.value.user?.let { user ->
				if (!enabled) {
					cancelAllNotificationUseCase(user.id)
				} else {
					rescheduleNotificationsUseCase(user.id)
				}
			}
		}
	}

	fun toggleFingerprint(enabled: Boolean) {
		viewModelScope.launch {
			setFingerprintEnabledUseCase(enabled)
		}
	}

	init {
		load()
	}

	private fun load() {
		viewModelScope.launch {
			val user = fetchUserUseCase()
			_state.update { it.copy(user = user) }

			fetchNotificationsUseCase(user.id)
				.collect { notifs ->
					_state.update { st -> st.copy(notifications = notifs) }
				}
		}
	}
	fun addNotification(time: String) {
		viewModelScope.launch {
			_state.value.user?.let { addNotificationUseCase(it.id, time) }
		}
	}

	fun removeNotification(notificationId: String) {
		viewModelScope.launch {
			removeNotificationUseCase(notificationId)
		}
	}
}