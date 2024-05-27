package com.com.app.core.common.util.validator


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorPagingTest{

    @Test
    fun whenPageValidLoaded(){
        val itemSize = 20
        val isLoading = false
        val limit = 10
        val result = ValidatorPaging.validateReload(itemSize, isLoading, limit)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenDataInsufficient(){
        val itemSize = 10
        val isLoading = false
        val limit = 20
        val result = ValidatorPaging.validateReload(itemSize, isLoading, limit)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenLoadingInProgress(){
        val itemSize = 20
        val isLoading = true
        val limit = 10
        val result = ValidatorPaging.validateReload(itemSize, isLoading, limit)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenLoadingInProgressAndInsufficientData(){
        val itemSize = 5
        val isLoading = true
        val limit = 10
        val result = ValidatorPaging.validateReload(itemSize, isLoading, limit)
        assertThat(result).isEqualTo(false)
    }
}