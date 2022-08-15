package com.example.zapp_project.ui.edit


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.apollographql.apollo3.api.Optional
import com.example.zapp_project.R
import com.example.zapp_project.databinding.FragmentEditBinding
import com.example.zapp_project.type.UpdateUserInput
import com.example.zapp_project.utils.hideSoftKeyboard
import dagger.android.support.DaggerFragment
import javax.inject.Inject

const val ID = "_id"

class EditUserFragment : DaggerFragment() {
    companion object {
        fun newInstance(id: String?): EditUserFragment = EditUserFragment()
            .apply {
                arguments = Bundle().apply {
                    this.putString(ID, id)
                }
            }
    }

    @Inject
    lateinit var editUserViewModel: EditUserViewModel
    lateinit var binding: FragmentEditBinding
    var id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            id = it?.getString(ID)
        }
        initialize()
    }

    private fun initialize() {
        binding.fragmentEditUserBackImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fragmentEditButton.setOnClickListener {
            binding.fragmentEditProgressBar.root.visibility = View.VISIBLE
            binding.fragmentEditButton.isEnabled = false
            hideSoftKeyboard(requireActivity())
            editUserViewModel.updateUserData(
                id!!, UpdateUserInput(
                    Optional.presentIfNotNull(binding.fragmentEditNameEditText.text.toString()),
                    Optional.presentIfNotNull(binding.fragmentEditUserNameEditText.text.toString()),
                    Optional.presentIfNotNull(binding.fragmentEditEmailEditText.text.toString())
                )
            )
        }
        id?.let { editUserViewModel.getUser(it) }
        editUserViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.fragmentEditProgressBar.root.visibility=View.GONE
                binding.fragmentEditNameEditText.setText(it.name)
                binding.fragmentEditEmailEditText.setText(it.email)
                binding.fragmentEditUserNameEditText.setText(it.username)
            }
            else{
                binding.fragmentEditProgressBar.root.visibility=View.GONE
            }
        })
        editUserViewModel.updatedResponse.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.fragmentEditProgressBar.root.visibility = View.GONE
                binding.fragmentEditButton.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "Something went wrong!! Try again later",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.fragmentEditProgressBar.root.visibility = View.GONE
                binding.fragmentEditButton.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "User data updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                showDialog()
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

        editUserViewModel.updateUserDataResponse.observe(viewLifecycleOwner, Observer {
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