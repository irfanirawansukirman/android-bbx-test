package com.irfanirawansukirman.featuremovie.presentation.movie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.UIState.Status.*
import com.irfanirawansukirman.extensions.*
import com.irfanirawansukirman.extensions.widget.isNotNull
import com.irfanirawansukirman.extensions.widget.orDefault
import com.irfanirawansukirman.featuremovie.R
import com.irfanirawansukirman.featuremovie.data.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val viewModel by viewModels<MovieVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)

        viewModel.apply {
            movies.subscribe(this@MovieActivity, ::showMovies)
            test.subscribe(this@MovieActivity, ::showToast)
            ioException.subscribe(this@MovieActivity) {
                showToast(it.error + " A")
            }
            errorException.subscribe(this@MovieActivity) {
                showToast(it.error + " B")
            }
            timeoutException.subscribe(this@MovieActivity) {
                showToast(it.error + " C")
            }
        }
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