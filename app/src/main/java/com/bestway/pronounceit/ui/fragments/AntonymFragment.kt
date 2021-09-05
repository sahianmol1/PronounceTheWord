package com.bestway.pronounceit.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.shoppinglisttestingyt.others.Status
import com.bestway.pronounceit.databinding.FragmentAntonymBinding
import com.bestway.pronounceit.ui.DictionaryViewModel
import com.bestway.pronounceit.ui.adapters.SynonymAntonymAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AntonymFragment : Fragment() {
    private val viewModel: DictionaryViewModel by viewModels()
    private val args: AntonymFragmentArgs by navArgs()
    private var antonyms = mutableListOf<String>()
    private var _binding: FragmentAntonymBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAntonymBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.searchForWord(args.word)
        viewModel.wordResponse.observe(viewLifecycleOwner) { eventResult ->
            eventResult?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        val antonymsResult =
                            result?.data?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.antonyms
                        if (antonymsResult != null) {
                            if (antonymsResult.isNotEmpty()) {
                                antonyms.addAll(antonymsResult)
                                setUpAdapter()
                            } else {
                                binding.textViewEmptyResults.visibility = View.VISIBLE
                            }
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireView(),
                            "An Unknown error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        binding.textViewEmptyResults.visibility = View.VISIBLE
                    }
                }
            }
        }
        return view
    }

    private fun setUpAdapter() {
        binding.recyclerView.visibility = View.VISIBLE
        val adapter = SynonymAntonymAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        adapter.submitList(antonyms)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}