package com.pa.sugarcare.presentation.feature.sugargrade

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pa.sugarcare.presentation.feature.sugargrade.tab.OtherInfoFragment
import com.pa.sugarcare.presentation.feature.sugargrade.tab.SugarGradeFragment

class SugarGradePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = SugarGradeFragment()
            1 -> fragment = OtherInfoFragment()
        }
        return fragment as Fragment
    }
}