package com.hafidrf.app.data.local.dao

import androidx.room.*
import com.hafidrf.app.data.local.entity.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getPosts(): List<PostEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg post: PostEntity)

    @Query("DELETE FROM post WHERE id IN (:id)")
    suspend fun deletePost(id : String?)

    @Query("SELECT EXISTS (SELECT * FROM post WHERE id IN (:id))")
    fun isExist(id : String?) : Boolean
}