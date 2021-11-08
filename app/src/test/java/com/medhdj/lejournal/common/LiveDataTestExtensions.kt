package com.medhdj.lejournal.common

import androidx.lifecycle.LiveData

fun <T : Any?> LiveData<T>.test() = LiveDataTestObserver<T>().also {
    observeForever(it)
}
