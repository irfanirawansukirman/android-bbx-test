package com.irfanirawansukirman.abstraction.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding>(
    private val viewBinder: (LayoutInflater) -> ViewBinding
) : Fragment() {

    // val mViewBinding by lazy(LazyThreadSafetyMode.NONE) { viewBinding { viewBinder.invoke(layoutInflater).root as VB } as VB }
    val mViewBinding by lazy(LazyThreadSafetyMode.NONE) { viewBinder.invoke(layoutInflater) as VB }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onGetArguments(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = mViewBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFirstLaunch(savedInstanceState)
        setupViewListener()
        loadObservers()
    }

    override fun onStart() {
        super.onStart()
        continuousCall()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyActivities()
    }

    /**
     * Function for load livedata observer from viewmodel
     */
    abstract fun loadObservers()

    /**
     * Function for first launching like as onCreate
     */
    abstract fun onFirstLaunch(savedInstanceState: Bundle?)

    /**
     * Function for continously call after onCreate and onResume
     */
    abstract fun continuousCall()

    /**
     * Function for setup view listener
     */
    abstract fun setupViewListener()

    abstract fun onDestroyActivities()

    abstract fun onGetArguments(arguments: Bundle?)

    fun getMyParentFragment() = parentFragment
}