package mr.sardorek.jobsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import mr.sardorek.jobsapp.MainActivity
import mr.sardorek.jobsapp.R
import mr.sardorek.jobsapp.adapter.JobToSaveAdapter
import mr.sardorek.jobsapp.databinding.FragmentSavedBinding
import mr.sardorek.jobsapp.model.JobToSave
import mr.sardorek.jobsapp.viewModel.MainViewModel

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val jobToSaveAdapter by lazy { JobToSaveAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        initViews()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter= jobToSaveAdapter
        }
        viewModel.getAllLocalJobs().observe(viewLifecycleOwner) {
            jobToSaveAdapter.submitList(it)
            binding.cardNoAvailable.isVisible = it.isEmpty()
        }
        jobToSaveAdapter.onDeleteClick = {
            deleteJob(it)
        }
        jobToSaveAdapter.onClick = {

        }
    }

    private fun deleteJob(it: JobToSave) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete")
            setMessage("Do you want to delete ${it.title}?")
            setPositiveButton("Yes") { di, _ ->
                viewModel.deleteJob(it)
                di.dismiss()
                Snackbar.make(binding.root, "Job deleted", Snackbar.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}