package com.example.ava.stubs

import com.example.ava.settings.SettingState
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> StubSettingState(value: T): SettingState<T> {
    val state = MutableStateFlow<T>(value)
    return SettingState(state) { state.value = it }
}