package tw.com.andyawd.seenote.database

import androidx.room.*
import tw.com.andyawd.seenote.bean.Setting

@Dao
interface SettingDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting): Long

    @Update
    suspend fun update(setting: Setting)

    @Query("SELECT * FROM setting_table ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): Setting?

    @Query("SELECT * FROM setting_table ORDER BY id ASC")
    suspend fun getAll(): List<Setting>
}