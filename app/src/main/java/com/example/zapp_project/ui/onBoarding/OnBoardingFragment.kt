package com.example.zapp_project.ui.onBoarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.zapp_project.databinding.FragmentOnBoardingBinding
import com.example.zapp_project.utils.hideSoftKeyboard
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OnBoardingFragment : DaggerFragment() {
    lateinit var binding: FragmentOnBoardingBinding
    lateinit var listener: NavigationInterface

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.fragmentOnBoardingViewUserButton.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            val id = binding.fragmentOnBoardingIdEditText.text.toString()
            if (id.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter an Id", Toast.LENGTH_SHORT).show()
            } else {
                binding.fragmentOnBoardingIdEditText.clearFocus()
                binding.fragmentOnBoardingIdEditText.text.clear()
                listener.onViewUserClicked(id)
            }
        }
        binding.fragmentOnBoardingIdEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                binding.fragmentOnBoardingIdEditText.clearFocus()
                hideSoftKeyboard(requireActivity())
            }
            false
        }
        binding.fragmentOnBoardingCreateUserButton.setOnClickListener {
            listener.onCreateUserClicked()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as NavigationInterface
    }

    interface NavigationInterface {
        fun onCreateUserClicked()
        fun onViewUserClicked(id: String)
    }
}