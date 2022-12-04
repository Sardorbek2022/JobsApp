package mr.sardorek.jobsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mr.sardorek.jobsapp.MainActivity
import mr.sardorek.jobsapp.R
import mr.sardorek.jobsapp.adapter.JobAdapter
import mr.sardorek.jobsapp.databinding.FragmentJobsBinding
import mr.sardorek.jobsapp.utils.Resource
import mr.sardorek.jobsapp.viewModel.MainViewModel

class JobsFragment : Fragment(R.layout.fragment_jobs) {
    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobsBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val viewModel = (activity as MainActivity).mainViewModel
        binding.rvRemoteJobs.adapter = jobAdapter
        binding.rvRemoteJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
        }
        setupViewModel(viewModel)
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupViewModel(viewModel: MainViewModel) {
        viewModel.remoteJobs.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.swipeContainer.isRefreshing = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.swipeContainer.isRefreshing = false
                    jobAdapter.submitList(it.data?.jobs)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}