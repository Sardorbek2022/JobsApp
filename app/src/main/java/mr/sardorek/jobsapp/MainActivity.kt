package mr.sardorek.jobsapp


import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mr.sardorek.jobsapp.databinding.ActivityMainBinding
import mr.sardorek.jobsapp.network.RetroInstance
import mr.sardorek.jobsapp.repository.JobRepository
import mr.sardorek.jobsapp.viewModel.MainViewModel
import mr.sardorek.jobsapp.viewModel.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.statusBarColor = Color.parseColor()
        setContentView(binding.root)
        supportActionBar?.hide()

        val repository = JobRepository(RetroInstance.retroInstance())
        val viewModelFactory = MainViewModelFactory(application, repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

    }
}