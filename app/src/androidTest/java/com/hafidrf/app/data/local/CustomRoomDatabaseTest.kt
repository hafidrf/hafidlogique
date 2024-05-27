package com.hafidrf.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.local.sample.SamplePost

@RunWith(AndroidJUnit4::class)
class CustomRoomDatabaseTest : TestCase(){


    private lateinit var db: CustomRoomDatabase
    private lateinit var dao : PostDao


    @Before
    public  override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CustomRoomDatabase::class.java).build()
        dao = db.postDao()
    }

    @After
    fun closeDB(){
        db.close()
    }



    @Test
    fun readPost() = runBlocking  {
        val posts = dao.getPosts()
        assertThat(posts?.size == 0).isTrue()
    }

    @Test
    fun writeAndReadPost() = runBlocking  {
        val data = SamplePost.post
        dao.insert(data)
        val posts = dao.getPosts()
        assertThat(posts?.size == 1).isTrue()
    }

    @Test
    fun writeAndDeletePost() = runBlocking  {
        val data = SamplePost.post
        dao.insert(data)
        dao.deletePost(data.id)

        val posts = dao.getPosts()

        assertThat(posts?.size == 0).isTrue()
//        assertThat(posts?.contains(data)).isFalse()
    }
}