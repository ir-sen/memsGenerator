package com.kis.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kis.classes.Meme

@Dao
interface UserRoomDao {

    @Query("SELECT * FROM mem_playersDb")
    fun getAllHaveUser(): LiveData<List<Meme>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDbUser(meme: Meme)

    @Query("DELETE FROM mem_playersDb")
    suspend fun deleteAllFromUser()

    @Query("DELETE FROM mem_playersDb WHERE id=:item")
    suspend fun deleteSelectItem(item: String)

}