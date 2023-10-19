package com.example.phinconattendance.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.FragmentProfileBinding
import com.example.phinconattendance.ui.login.LoginActivity
import com.example.phinconattendance.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var profileViewModel: ProfileViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.getCurrentFullName().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showProgressBar()
                Status.SUCCESS -> {
                    hideProgressBar()
                    binding.tvProfileName.text = it.data?.fullName ?: ""
                }

                Status.ERROR -> {
                    hideProgressBar()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        listener()

        return root
    }

    private fun listener() {
        binding.ivProfileLogout.setOnClickListener {
            profileViewModel.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity?.startActivity(intent)
        }

        binding.ivProfileEdit.setOnClickListener {
            Toast.makeText(activity, getString(R.string.all_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        binding.pbProfile.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbProfile.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}