package com.bestway.pronounceit.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.androiddevs.shoppinglisttestingyt.others.Status
import com.bestway.pronounceit.databinding.FragmentMeaningBinding
import com.bestway.pronounceit.ui.DictionaryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeaningFragment : Fragment() {
    private val viewModel: DictionaryViewModel by viewModels()
    private val args: MeaningFragmentArgs by navArgs()

    private var _binding: FragmentMeaningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeaningBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.searchForWord(args.word)
        viewModel.wordResponse.observe(viewLifecycleOwner){ eventResponse ->
            eventResponse?.getContentIfNotHandled()?.let { result ->
                when(result.status){
                    Status.SUCCESS -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            tvWordName.text = args.word
                            tvDefinition.text = "Definition: "+result?.data?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition
                            tvExample.text = "Example: " + result?.data?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.example
                        }
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireView(), "An Unknown error occurred", Snackbar.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}