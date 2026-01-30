package com.example.ava.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.KSerializer
import timber.log.Timber

interface SettingsStore<T> {
    fun getFlow(): Flow<T>
    suspend fun get(): T
    suspend fun update(transform: suspend (T) -> T)
}

open class SettingsStoreImpl<T>(
    private val context: Context,
    private val default: T,
    fileName: String,
    serializer: KSerializer<T>
) :
    SettingsStore<T> {

    private val Context.dataStore: DataStore<T> by dataStore(
        fileName,
        SettingsSerializer(serializer, default),
        corruptionHandler = defaultCorruptionHandler(default)
    )

    override fun getFlow() = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading settings, returning defaults")
                emit(default)
            } else throw exception
        }
        .onEach { Timber.d("Loaded settings: $it") }

    override suspend fun get(): T = getFlow().first()

    override suspend fun update(transform: suspend (T) -> T) {
        context.dataStore.updateData(transform)
    }
}