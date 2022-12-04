package mr.sardorek.jobsapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mr.sardorek.jobsapp.repository.JobRepository

class MainViewModelFactory(
    val app: Application,
    val jobRepository: JobRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app, jobRepository) as T
    }
}