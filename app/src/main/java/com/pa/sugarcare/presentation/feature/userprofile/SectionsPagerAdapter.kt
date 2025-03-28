package com.pa.sugarcare.presentation.feature.userprofile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pa.sugarcare.presentation.feature.userprofile.myconsumption.MyConsumptionFragment
import com.pa.sugarcare.presentation.feature.userprofile.producthistory.ProductHistoryFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MyConsumptionFragment()
            1 -> fragment = ProductHistoryFragment()
        }
        return fragment as Fragment    }
}