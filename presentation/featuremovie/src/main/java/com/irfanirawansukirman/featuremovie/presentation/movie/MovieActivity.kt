package com.irfanirawansukirman.featuremovie.presentation.movie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.UIState.Status.*
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.extensions.decodeToObj
import com.irfanirawansukirman.extensions.encodeToString
import com.irfanirawansukirman.extensions.logD
import com.irfanirawansukirman.extensions.subscribe
import com.irfanirawansukirman.extensions.widget.isNotNull
import com.irfanirawansukirman.featuremovie.data.model.Result
import com.irfanirawansukirman.featuremovie.databinding.MovieActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : BaseActivity<MovieActivityBinding>(MovieActivityBinding::inflate),
    MovieContract.MovieActivity {

    private val viewModel by viewModels<MovieVM>()

    override fun loadObservers() {
        viewModel.movies.subscribe(this@MovieActivity, ::showMovies)
    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        getMovies()
    }

    override fun continuousCall() {}

    override fun setupViewListener() {}

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {}

    override fun getMovies() {
        viewModel.getMovies()
    }

    private fun showMovies(state: UIState<List<Result>>) {
        when (state.status) {
            LOADING -> {
                // show progress
            }
            SUCCESS -> {
                val isNotEmptyData = state.data.isNotNull()
                if (isNotEmptyData) {
                    state.data?.forEach {
                        val asStr = encodeToString(it)
                        logD("As String $asStr")
                        val asObj = decodeToObj<Result>(asStr)
                        logD("As Obj ${asObj.originalTitle}")
                    }
                } else {
                    // handle when data is null
                }
            }
            FINISH -> {
                // hide progress
            }
            ERROR -> {
                // error api, general exception
            }
            else -> {
                // timeout
            }
        }
    }
}