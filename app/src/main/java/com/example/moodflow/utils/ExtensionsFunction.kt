package com.example.moodflow.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.moodflow.utils.Constants.DATASTORE_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)