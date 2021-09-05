package com.bestway.pronounceit.ui.fragments

import android.media.AudioAttributes
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bestway.pronounceit.databinding.FragmentPronounceBinding

import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.androiddevs.shoppinglisttestingyt.others.Status
import com.bestway.pronounceit.ui.DictionaryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class PronounceFragment : Fragment() {
    private var _binding: FragmentPronounceBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer
    private val args: PronounceFragmentArgs by navArgs()
    private val viewModel: DictionaryViewModel by viewModels()
    private var audioUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPronounceBinding.inflate(inflater, container, false)
        val view = binding.root
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchForWord(args.word)
            viewModel.wordResponse.observe(viewLifecycleOwner) { eventResult ->
                eventResult?.getContentIfNotHandled()?.let { result ->
                    when (result.status) {
                        Status.SUCCESS -> {
                            try {
                                val phoneticText = result.data?.get(0)?.phonetics?.get(0)?.text
                                binding.tvPhonetic.text = "Phonetics: " + phoneticText
                                audioUrl = result.data?.get(0)?.phonetics?.get(0)?.audio
                                startLottieAnimation()
                                audioUrl?.let { playAudio(it) }
                                binding.progressBar.visibility = View.GONE
                                binding.btnPlayAgain.visibility = View.VISIBLE
                            } catch (e: Exception) {
                                Snackbar.make(
                                    requireView(),
                                    "An Unknown error occurred",
                                    Snackbar.LENGTH_LONG
                                ).show()
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                requireView(),
                                "An Unknown error occurred",
                                Snackbar.LENGTH_LONG
                            ).show()
                            binding.progressBar.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.btnPlayAgain.visibility = View.GONE
                        }
                    }
                }
            }
        }

        setOnClickListeners()
        return view
    }

    private fun setOnClickListeners() {
        binding.btnPlayAgain.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                startLottieAnimation()
                audioUrl?.let { it1 -> playAudio(it1) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun playAudio(url: String) {
        val audioUrl = "https:$url"

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(audioUrl)
            prepare() // might take long! (for buffering, etc)
        }
        mediaPlayer.start()
    }

    override fun onStop() {
        super.onStop()
//        mediaPlayer.stop()
//        mediaPlayer.release()
    }

    private fun startLottieAnimation() {
        binding.lottieAnimation.playAnimation()
    }
}