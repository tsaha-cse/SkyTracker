package com.skyscanner.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.koin.standalone.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    protected fun <T> LiveData<T>.postVal(value: T) {
        (this as? MutableLiveData)?.postValue(value)
    }

    protected fun <T> LiveData<T>.setVal(value: T) {
        (this as? MutableLiveData)?.value = value
    }
}