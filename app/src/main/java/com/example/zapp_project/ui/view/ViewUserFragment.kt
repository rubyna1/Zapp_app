package com.example.zapp_project.ui.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.zapp_project.databinding.FragmentViewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

const val ID = "id"

class ViewUserFragment : DaggerFragment() {
    companion object {
        fun newInstance(id: String): ViewUserFragment = ViewUserFragment().apply {
            arguments = Bundle().apply {
                this.putString(ID, id)
            }
        }
    }

    @Inject
    lateinit var viewUserViewModel: ViewUserViewModel
    lateinit var binding: FragmentViewBinding
    lateinit var listener: NavigationInterface

    var id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewBinding.inflate(layoutInflater)
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
        binding.fragmentViewUserBackImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        id?.let { viewUserViewModel.getUser(it) }
        viewUserViewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it?.id != null) {
                binding.fragmentViewProgressBar.root.visibility = View.GONE
                binding.fragmentViewNameValueTextView.text = it.name
                binding.fragmentViewEmailValueTextView.text = it.email
                binding.fragmentViewUserNameValueTextView.text = it.username
            } else {
                Toast.makeText(requireContext(), "No Such User", Toast.LENGTH_SHORT).show()
                binding.fragmentViewProgressBar.root.visibility = View.GONE
                requireActivity().onBackPressed()
            }
        })
        binding.fragmentViewEditUserButton.setOnClickListener {
            listener.onEditUserClicked(id)
        }
        binding.fragmentViewDeleteUserButton.setOnClickListener {
            binding.fragmentViewProgressBar.root.visibility = View.VISIBLE
            id?.let { it1 -> viewUserViewModel.deleteUser(it1) }
        }
        viewUserViewModel.deleteUserResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && it) {
                binding.fragmentViewProgressBar.root.visibility = View.GONE
                Toast.makeText(requireContext(), "User deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                binding.fragmentViewProgressBar.root.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Something went wrong!! Try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as NavigationInterface
    }

    interface NavigationInterface {
        fun onEditUserClicked(id: String?)
    }
}