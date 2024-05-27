package com.hafidrf.app.ui.main.child.like

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import com.hafidrf.app.data.local.sample.SamplePost
import com.hafidrf.app.util.getOrAwaitValue

@RunWith(AndroidJUnit4ClassRunner::class)
class LikeViewModelTest : KoinTest {

    private val likeViewModel by inject<LikeViewModel>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addPostAndRead(){
        likeViewModel.addLike(SamplePost.post)
        likeViewModel.getLikePosts()
        val result = likeViewModel.post.getOrAwaitValue().find {
            it.text == "Labrador"
        }

        assertThat(result != null).isEqualTo(true)
    }

    @Test
    fun addPostAndReadWrong(){
        likeViewModel.addLike(SamplePost.post)
        likeViewModel.getLikePosts()

        val resultTrue = likeViewModel.post.getOrAwaitValue().find {
            it.text == "Labrador"
        }

        assertThat(resultTrue != null).isEqualTo(true)

        val resultFalse = likeViewModel.post.getOrAwaitValue().find {
            it.text == "ABC"
        }

        assertThat(resultFalse == null).isEqualTo(true)
    }

    @Test
    fun deleteAndRead(){
        likeViewModel.addLike(SamplePost.post)
        likeViewModel.deleteLike(SamplePost.post.id)
        likeViewModel.getLikePosts()
        val result = likeViewModel.post.getOrAwaitValue().find {
            it.id == SamplePost.post.id
        }

        assertThat(result != null).isEqualTo(false)
    }
}