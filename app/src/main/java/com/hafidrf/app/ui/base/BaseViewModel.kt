package com.hafidrf.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.hafidrf.app.data.model.LoaderState

open class BaseViewModel : ViewModel(), KoinComponent {

    private val baseUseCase: BaseUseCase by inject()

    protected var loaderState = MutableLiveData<LoaderState>()
    val loader: LiveData<LoaderState>
        get() = loaderState
}