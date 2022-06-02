package com.patrickmota.moviedatabase.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.patrickmota.moviedatabase.R
import com.patrickmota.moviedatabase.databinding.ActivityMovieDetailBinding
import com.patrickmota.moviedatabase.view.adapters.PhotoAdapter
import com.patrickmota.moviedatabase.view.fragments.ConnectionErrorFragment
import com.patrickmota.moviedatabase.view.fragments.CreditFragment
import com.patrickmota.moviedatabase.viewmodel.activities.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var binding: ActivityMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MovieDatabase)
        setContentView(binding.root)

        val extras: Bundle? = intent.extras
        val movieId = extras?.get("movieId")

        backToPreviousScreen()
        initTextDetails(movieId.toString())
        loadCast(movieId.toString())
        listenerOnButtons(movieId.toString())
        initPhotoRecyclerView(movieId.toString())
    }

    private fun backToPreviousScreen() {
        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initTextDetails(id: String) {
        movieDetailViewModel.getData(id)
        movieDetailViewModel.movieDetailResponse().observe(this) {
            val bannerUrl = "https://image.tmdb.org/t/p/w500/" + it.backdropPath

            val requestOptions = RequestOptions()
                .placeholder(R.color.gray)
                .error(R.color.gray)

            Glide.with(this@MovieDetailActivity)
                .applyDefaultRequestOptions(requestOptions)
                .load(bannerUrl)
                .fitCenter()
                .into(imageDetail)

            titleDetail.text = it.title
            popularityDetail.text = it.popularity.toString()
            dateDetail.text = it.releaseDate

            val genres = it.genres.joinToString(", ") { genre -> genre.name }

            genreDetail.text = genres
            synopsisText.text = it.overview

            if (synopsisText.text.length >= 200) {
                showMore.visibility = View.VISIBLE
                synopsisText.maxLines = 4
            } else {
                showMore.visibility = View.GONE
            }

            var changed = false

            showMore.setOnClickListener {
                if (!changed) {
                    synopsisText.maxLines = 20
                    showMore.setText(R.string.show_less)
                    showMore.visibility = View.VISIBLE
                    changed = true
                } else {
                    showMore.setText(R.string.show_more)
                    synopsisText.maxLines = 4
                    changed = false
                }
            }
        }

        movieDetailViewModel.error().observe(this){
            showConnectionError()
        }
    }

    private fun initPhotoRecyclerView(id: String) {
        movieDetailViewModel.getPhotos(id)
        movieDetailViewModel.imageResponse().observe(this) {
            val recyclerView: RecyclerView = horizontalPhotosRecyclerView

            photoAdapter = PhotoAdapter()

            photoAdapter.items = it

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = photoAdapter
            }
        }

    }

    private fun loadCast(movieId: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, CreditFragment(movieId, false))
            .commit()
    }

    private fun showConnectionError() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentError, ConnectionErrorFragment())
            .commit()

        binding.constraint.visibility = View.GONE
    }

    private fun listenerOnButtons(movieId: String) {
        val castButton = viewAllCast
        val photoButton = viewAllPhotos

        castButton.setOnClickListener {
            startActivity(
                Intent(this, CastActivity::class.java)
                    .putExtra("movieId", movieId)
            )
        }

        photoButton.setOnClickListener {
            startActivity(
                Intent(this, PhotoActivity::class.java)
                    .putExtra("movieId", movieId)
            )
        }

    }
}