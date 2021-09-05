package com.bestway.pronounceit.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bestway.pronounceit.R
import com.bestway.pronounceit.databinding.FragmentMainBinding
import com.bestway.pronounceit.ui.DictionaryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DictionaryViewModel by viewModels()
    private val wordHistoryList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        setOnClickListeners()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                clearText()
                return true
            }
            R.id.action_history -> {
                Snackbar.make(requireView(), "Feature Coming Soon!", Snackbar.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun clearText() {
        binding.etEnterWord.text?.clear()
    }

    private fun setOnClickListeners() {

        binding.apply {
            btnAntonym.setOnClickListener {
                if (etEnterWord.text.isNullOrEmpty()) showSnackBar()
                else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        animateAntonym()
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToAntonymFragment(
                                etEnterWord.text.toString()
                            )
                        )
                    }
                }
            }
            btnMeaning.setOnClickListener {
                if (etEnterWord.text.isNullOrEmpty()) showSnackBar()
                else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        animateMeaning()
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToMeaningFragment(
                                etEnterWord.text.toString()
                            )
                        )
                    }
                }
            }
            btnPronounce.setOnClickListener {
                if (etEnterWord.text.isNullOrEmpty()) showSnackBar()
                else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        animatePronounce()
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToPronounceFragment(
                                etEnterWord.text.toString()
                            )
                        )
                    }
                }
            }
            btnSynonym.setOnClickListener {
                if (etEnterWord.text.isNullOrEmpty()) showSnackBar()
                else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        animateSynonym()
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToSynonymFragment(
                                etEnterWord.text.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(requireView(), "Please Enter a Word", Snackbar.LENGTH_SHORT).show()
    }

    private suspend fun animatePronounce() {
        binding.apply {
            btnSynonym.isClickable = false
            btnAntonym.isClickable = false
            btnMeaning.isClickable = false
            btnPronounce.isClickable = false
            btnSynonym.animate().translationXBy(1200f).duration = 600L
            btnMeaning.animate().translationXBy(-1200f).duration = 600L
            btnAntonym.animate().translationXBy(-1200f).duration = 600L
            delay(500L)
        }
    }

    private suspend fun animateMeaning() {
        binding.apply {
            btnSynonym.isClickable = false
            btnAntonym.isClickable = false
            btnMeaning.isClickable = false
            btnPronounce.isClickable = false
            btnSynonym.animate().translationXBy(1200f).duration = 600L
            btnPronounce.animate().translationXBy(-1200f).duration = 600L
            btnAntonym.animate().translationXBy(-1200f).duration = 600L
            delay(500L)
            btnMeaning.animate().translationYBy(-200f).duration = 600L
            delay(600L)
        }
    }

    private suspend fun animateSynonym() {
        binding.apply {
            btnSynonym.isClickable = false
            btnAntonym.isClickable = false
            btnMeaning.isClickable = false
            btnPronounce.isClickable = false
            btnMeaning.animate().translationYBy(1200f).duration = 600L
            btnPronounce.animate().translationYBy(1200f).duration = 600L
            btnAntonym.animate().translationYBy(1200f).duration = 600L
            delay(500L)
            btnSynonym.animate().translationYBy(-300f).duration = 600L
            delay(600L)
        }
    }

    private suspend fun animateAntonym() {
        binding.apply {
            btnSynonym.isClickable = false
            btnAntonym.isClickable = false
            btnMeaning.isClickable = false
            btnPronounce.isClickable = false
            btnMeaning.animate().translationYBy(1200f).duration = 600L
            btnPronounce.animate().translationYBy(1200f).duration = 600L
            btnSynonym.animate().translationYBy(1200f).duration = 600L
            delay(500L)
            btnAntonym.animate().translationYBy(-400f).duration = 600L
            delay(600L)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}