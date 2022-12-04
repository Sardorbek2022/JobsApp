package mr.sardorek.jobsapp.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import mr.sardorek.jobsapp.model.JobToSave

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteJob(job: JobToSave)

    @Query(/* value = */ "SELECT * FROM job ORDER BY id DESC")
    fun getAllFavoriteJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteFavJob(job: JobToSave)
}