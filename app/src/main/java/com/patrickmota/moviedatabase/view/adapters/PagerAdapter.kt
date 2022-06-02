package com.patrickmota.moviedatabase.view.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.patrickmota.moviedatabase.view.fragments.ComingSoonFragment
import com.patrickmota.moviedatabase.view.fragments.NowPlayingFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NowPlayingFragment()
            }
            1 -> {
                ComingSoonFragment()
            }
            else -> throw Resources.NotFoundException("Position not found")
        }
    }

}