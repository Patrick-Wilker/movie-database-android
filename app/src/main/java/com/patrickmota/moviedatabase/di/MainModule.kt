package com.patrickmota.moviedatabase.di

import com.patrickmota.moviedatabase.repository.home.HomeRepository
import com.patrickmota.moviedatabase.repository.home.HomeRepositoryImpl
import com.patrickmota.moviedatabase.remote.MovieApiService
import com.patrickmota.moviedatabase.repository.credit.CreditRepository
import com.patrickmota.moviedatabase.repository.credit.CreditRepositoryImpl
import com.patrickmota.moviedatabase.repository.detail.DetailRepository
import com.patrickmota.moviedatabase.repository.detail.DetailRepositoryImpl
import com.patrickmota.moviedatabase.repository.image.ImageRepository
import com.patrickmota.moviedatabase.repository.image.ImageRepositoryImpl
import com.patrickmota.moviedatabase.viewmodel.activities.MainViewModel
import com.patrickmota.moviedatabase.viewmodel.activities.MovieDetailViewModel
import com.patrickmota.moviedatabase.viewmodel.activities.PhotoViewModel
import com.patrickmota.moviedatabase.viewmodel.fragments.CreditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single(named("BASE_URL")) {
        "https://api.themoviedb.org/3/"
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("BASE_URL")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(MovieApiService::class.java)
    }

}

val repositoryModule = module {
    single {
        HomeRepositoryImpl(get()) as HomeRepository
    }

    single {
        DetailRepositoryImpl(get()) as DetailRepository
    }

    single {
        CreditRepositoryImpl(get()) as CreditRepository
    }

    single {
        ImageRepositoryImpl(get()) as ImageRepository
    }
}


val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        MovieDetailViewModel(get(), get())
    }

    viewModel {
        PhotoViewModel(get())
    }

    viewModel {
        CreditViewModel(get())
    }
}