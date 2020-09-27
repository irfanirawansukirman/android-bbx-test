package com.irfanirawansukirman.featuremovie.presentation.movie

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.base.BaseViewModel
import com.irfanirawansukirman.featuremovie.data.model.Result
import com.irfanirawansukirman.featuremovie.domain.usecase.MovieUseCaseImpl
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

interface MovieContract {
    interface MovieActivity {
        fun getMovies()
    }
}

class MovieVM @ViewModelInject constructor(
    @ApplicationContext context: Context,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val movieRepositoryImpl: MovieUseCaseImpl
) : BaseViewModel(context as Application, coroutineContextProvider), MovieContract.MovieActivity {

    private val _movies = MutableLiveData<UIState<List<Result>>>()
    val movies: LiveData<UIState<List<Result>>>
        get() = _movies

    override fun getMovies() {
        executeJob {
            movieRepositoryImpl().collect {
                withContext(coroutineContextProvider.main) {
                    _movies.value = it
                }
            }
        }
    }
}