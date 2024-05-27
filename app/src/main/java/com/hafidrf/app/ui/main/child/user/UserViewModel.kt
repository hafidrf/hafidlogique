package com.hafidrf.app.ui.main.child.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hafidrf.app.core.common.cons.RequestCons
import com.hafidrf.app.data.model.BaseResponseData
import com.hafidrf.app.data.model.LoaderState
import com.hafidrf.app.data.model.ResultState
import com.hafidrf.app.data.model.User
import com.hafidrf.app.ui.base.BaseViewModel

class UserViewModel(
    private val useCase: UserUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<ResultState<BaseResponseData<User>>>()
    val user: LiveData<ResultState<BaseResponseData<User>>> = _user

    fun getUser(limit: Int = RequestCons.QUERY.LIMIT, page: Int) {
        loaderState.value = LoaderState.OnLoading(true)
        viewModelScope.launch {
            try {
                val response = useCase.getUser(limit, page)
                if (response.isSuccessful) {
                    _user.postValue(ResultState.Success(response.body()))
                } else {
                    _user.postValue(
                        ResultState.Error(
                            errorCode = response.code(),
                            data = response.errorBody()
                        )
                    )
                }
                loaderState.value = LoaderState.OnLoading(false)
            } catch (throwable: Throwable) {
                _user.postValue(ResultState.Error(exception = throwable))
                loaderState.value = LoaderState.OnLoading(false)
            }
        }
    }

}