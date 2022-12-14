package mr.sardorek.jobsapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mr.sardorek.jobsapp.model.JobToSave

@Database(entities = [JobToSave::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var instance: JobDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                JobDatabase::class.java,
                "job_db"
            ).build()
    }
}