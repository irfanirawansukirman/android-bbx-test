package com.irfanirawansukirman.ustman.presentation.main.qibla

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irfanirawansukirman.extensions.putArgs
import com.irfanirawansukirman.ustman.R
import kotlinx.android.synthetic.main.qibla_fragment.*

class QiblaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.qibla_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webviewQibla.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://qiblafinder.withgoogle.com/intl/id/onboarding")
        }

    }

    companion object {
        fun newInstance() = QiblaFragment().putArgs { }
    }
}