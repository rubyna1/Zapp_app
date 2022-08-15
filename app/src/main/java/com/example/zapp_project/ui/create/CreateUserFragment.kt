package com.example.zapp_project.ui.create

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.zapp_project.R
import com.example.zapp_project.databinding.FragmentCreateBinding
import com.example.zapp_project.type.CreateUserInput
import com.example.zapp_project.utils.hideSoftKeyboard
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateUserFragment : DaggerFragment() {
    companion object {
        fun newInstance(): CreateUserFragment = CreateUserFragment()
    }

    @Inject
    lateinit var createUserViewModel: CreateUserViewModel
    lateinit var binding: FragmentCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.fragmentCreateUserBackImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fragmentCreateButton.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            val name = binding.fragmentCreateNameEditText.text.toString()
            val userName = binding.fragmentCreateUserNameEditText.text.toString()
            val email = binding.fragmentCreateEmailEditText.text.toString()
            if (name.isEmpty() || userName.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.
                matcher(email).matches()
            ) {
                when {
                    name.isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            "Name can not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    userName.isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            "Username can not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    email.isEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            "Email can not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(
                            requireContext(),
                            "Email address invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                binding.fragmentCreateProgressBar.root.visibility=View.VISIBLE
                createUserViewModel.createUser(CreateUserInput(name, userName, email))
                binding.fragmentCreateButton.isEnabled=false
            }
        }
        createUserViewModel.createUserResponse.observe(viewLifecycleOwner, Observer {
            if (it.id != null) {
                binding.fragmentCreateButton.isEnabled=true
                binding.fragmentCreateProgressBar.root.visibility=View.GONE
                Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_SHORT)
                    .show()
                showDialog()
            }else{
                binding.fragmentCreateProgressBar.root.visibility=View.GONE
                binding.fragmentCreateButton.isEnabled=true
            }
        })
    }

    private fun showDialog() {
        val dialogLayout = this.layoutInflater.inflate(R.layout.item_user_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialogLayout)
        val dialog = alertDialog.create()
        val idTextView = dialogLayout.findViewById<TextView>(R.id.item_id_value_text_view)
        val nameTextView = dialogLayout.findViewById<TextView>(R.id.item_name_value_text_view)
        val userNameTextView =
            dialogLayout.findViewById<TextView>(R.id.item_user_name_value_text_view)
        val emailTextView = dialogLayout.findViewById<TextView>(R.id.item_email_value_text_view)
        val button = dialogLayout.findViewById<Button>(R.id.item_go_to_home_button)

        createUserViewModel.createUserResponse.observe(viewLifecycleOwner, Observer {
            idTextView.text = it.id
            nameTextView.text = it.name
            userNameTextView.text = it.username
            emailTextView.text = it.email
        })
        button.setOnClickListener {
            dialog.hide()
            val count = requireActivity().supportFragmentManager.backStackEntryCount
            for (i in 0 until count - 1) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        dialog.show()
    }
}