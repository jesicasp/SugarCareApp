package com.pa.sugarcare.presentation.feature.userprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.FragmentUserProfileBinding
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingActivity
import com.pa.sugarcare.presentation.feature.userprofile.vm.UserProfileViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources
import com.pa.sugarcare.utility.TokenStorage

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserProfileViewModel by viewModels {
        CommonVmInjector.common(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        logout()
        observeLogout()

    }

    private fun logout(){
        binding.btnLogout.setOnClickListener {
            Log.d("LOGOUTT","klik logout()")

            viewModel.logout()
        }
    }

    private fun observeLogout(){
        Log.d("LOGOUTT","masuk observeLogout()")

        viewModel.logoutResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resources.Loading -> {
                    Log.d("LOGOUTT","loading observeLogout()")

                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    Log.d("LOGOUTT","succes observeLogout()")

                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(requireContext(), OnBoardingActivity::class.java)
                    startActivity(intent)
                }

                is Resources.Error -> {
                    Log.d("LOGOUTT","error observeLogout()")
                    Log.d("LogoutDebug", "Token before logout: ${TokenStorage.getToken()}")

                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_myconsumption,
            R.string.tab_producthistory
        )

        private const val TAG = "UserProfileFragment"
    }

}