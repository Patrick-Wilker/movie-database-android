package com.patrickmota.moviedatabase.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.patrickmota.moviedatabase.R
import com.patrickmota.moviedatabase.view.fragments.CreditFragment
import kotlinx.android.synthetic.main.activity_movie_detail.*

class CastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieDatabase)
        setContentView(R.layout.activity_cast)

        val extras: Bundle? = intent.extras
        val movieId = extras?.get("movieId")

        backToPreviousScreen()
        loadCastList(movieId.toString())
    }

    private fun backToPreviousScreen() {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun loadCastList(movieId: String){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, CreditFragment(movieId, true))
            .commit()
    }
}