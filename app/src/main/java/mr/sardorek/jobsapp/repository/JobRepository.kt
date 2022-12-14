package mr.sardorek.jobsapp.repository

import mr.sardorek.jobsapp.dataBase.JobDao
import mr.sardorek.jobsapp.model.JobToSave
import mr.sardorek.jobsapp.network.ApiService

class JobRepository(
    private val apiService: ApiService,
    private val db: JobDao
) {
    suspend fun getAllJobs(limit: Int = 25) = apiService.getRemoteJobs(limit)
    suspend fun saveJob(job: JobToSave) = db.addFavoriteJob(job)
    fun getAllFavoriteJobs() = db.getAllFavoriteJob()
    suspend fun deleteJob(job: JobToSave) = db.deleteFavJob(job)
    suspend fun searchJob(query: String) = apiService.searchRemoteJob(query)
}