package com.hafidrf.app.core.common.util.validator

object ValidatorPaging {
    fun validateReload(itemSize : Int, isLoading : Boolean, limit : Int) : Boolean{
        return (itemSize % limit == 0 && !isLoading)
    }
}