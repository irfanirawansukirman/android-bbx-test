package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.setPadding
import com.irfanirawansukirman.abstraction.UIState
import com.irfanirawansukirman.abstraction.UIState.Status.*
import com.irfanirawansukirman.abstraction.base.BaseActivity
import com.irfanirawansukirman.extensions.*
import com.irfanirawansukirman.extensions.util.Const.Navigation.LoginActivity
import com.irfanirawansukirman.extensions.util.Const.Time.DELAY_TIME_DEFAULT
import com.irfanirawansukirman.extensions.widget.*
import com.irfanirawansukirman.featuredashboard.R
import com.irfanirawansukirman.featuredashboard.data.DataFactory.getPodsPlaces
import com.irfanirawansukirman.featuredashboard.data.model.PodsCalculate
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import com.irfanirawansukirman.featuredashboard.data.model.RoomNote
import com.irfanirawansukirman.featuredashboard.databinding.DashboardActivityBinding
import com.varunjohn1990.iosdialogs4android.IOSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dashboard_activity.*
import kotlinx.android.synthetic.main.pods_config_dialog.*


@AndroidEntryPoint
class DashboardActivity :
    BaseActivity<DashboardActivityBinding>(DashboardActivityBinding::inflate),
    DashboardContract.ToDashboard {

    private lateinit var dashboardPodsAdapter: DashboardPodsAdapter
    private lateinit var dashboardPodsConfigAdapter: DashboardPodsConfigAdapter

    private val viewModel by viewModels<DashboardVM>()

    private var isListPods = false
    private var dataTemp = ArrayList<PodsData>()
    private var podsDataCalculate = mutableListOf<PodsData>()
    private var podsDataCount = mutableListOf<PodsCalculate>()

    override fun loadObservers() {
        viewModel.apply {
            pods.subscribe(this@DashboardActivity, ::showPods)
            status.subscribe(this@DashboardActivity, ::setPodsStatus)
        }
    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        // setup status status bar to transparent background and fullscreen
        makeStatusBarTransparent()

        // set margin top to content in the first position from root container
        ViewCompat.setOnApplyWindowInsetsListener(mViewBinding.rootContainer) { _, insets ->
            constDashboardTop.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        setupPodsListAdapter()
        setupPodsListRecyclerView()

        if (!::dashboardPodsConfigAdapter.isInitialized) {
            dashboardPodsConfigAdapter = DashboardPodsConfigAdapter {
                dashboardPodsConfigAdapter.notifyByPosition(it)
            }
        }

        getPods()
    }

    private fun setupPodsListRecyclerView() {
        mViewBinding.recyclerDashboardPods.apply {
            setLinearList(withDecoration = true)
            adapter = dashboardPodsAdapter
        }
    }

    private fun setupPodsListAdapter() {
        if (!::dashboardPodsAdapter.isInitialized) {
            dashboardPodsAdapter = DashboardPodsAdapter({ desc, roomNotes ->
                showPodsConfigDialog(desc, roomNotes)
            }, { roomNumber, pods ->
                showPodsUpdateDialog(
                    roomNumber,
                    pods.roomStatusCode.orDefault("ABC"),
                    pods.roomStatusCode.orDefault("CBA")
                )
            })
        }
    }

    private fun showPodsConfigDialog(status: String, roomNotes: List<RoomNote>) {
        createDialog(R.layout.pods_config_dialog) {
            it.apply {
                show()

                txtPodsConfigName.text = status
                recyclerPodsConfig.apply {
                    setGridList(numberOfColumns = 3, withDecoration = true, itemSpacing = 2.toPx())
                    adapter = dashboardPodsConfigAdapter

                    if (roomNotes.isEmpty()) {
                        hide()
                    } else {
                        show()
                        dashboardPodsConfigAdapter.addAllData(roomNotes)
                    }
                }
                btnPodsConfigSubmit.setOnClickListener { dismiss() }
                btnPodsConfigCancel.setOnClickListener { dismiss() }
            }
        }
    }

    private fun showPodsUpdateDialog(roomNumber: Int, from: String, to: String) {
        IOSDialog.Builder(this)
            .title("Continue this action?")
            .message("Are you sure want to change Pod Number $roomNumber from $from to $to")
            .positiveButtonText("Continue")
            .negativeButtonText("Cancel")
            .cancelable(false)
            .positiveClickListener { iosDialog ->
                iosDialog.dismiss()
                dashboardPodsAdapter.deleteAtPosition(roomNumber.minus(1))
            }.negativeClickListener { iosDialog ->
                iosDialog.dismiss()
            }
            .build()
            .show()
    }

    override fun continuousCall() {

    }

    override fun setupViewListener() {
        mViewBinding.apply {
            txtDashboardStatus.setOnClickListener { showPodsFilterDialog() }
            linDashboardPodsFilter.setOnClickListener {
                showPodsFilterDialog(
                    DashboardPodsFilterDialogFragment.PLACE_TYPE
                )
            }
            imgDashboardList.setOnClickListener {
                it.disableView()
                imgDashboardGrid.enableView()
                txtDashboardStatus.enableView()
                setupPodsButtonState()
                setupPods()
            }
            imgDashboardGrid.setOnClickListener {
                it.disableView()
                imgDashboardList.enableView()
                txtDashboardStatus.disableView()
                setupPodsButtonState(true)
                setupPods(true)
            }
            imgDashboardLogout.setOnClickListener {
                createDialog(R.layout.progress_dialog) {
                    it.show()

                    runCoroutine(delayTime = DELAY_TIME_DEFAULT) {
                        navigationModule(
                            isRestartActivity = true,
                            targetClass = LoginActivity
                        )
                    }
                }
            }
        }
    }

    private fun setupPods(isGridPods: Boolean = false) {
        recyclerDashboardPods.apply {
            dashboardPodsAdapter.apply {
                setupViewType(if (isGridPods) DashboardPodsAdapter.GRID_TYPE else DashboardPodsAdapter.LIST_TYPE)
                if (isGridPods) addAllCount(podsDataCount) else addAllData(dataTemp)
            }
            if (isGridPods) setGridList() else setLinearList(withDecoration = true)
            adapter = dashboardPodsAdapter
        }
    }

    private fun setupPodsButtonState(isGrid: Boolean = false) {
        isListPods = !isGrid

        imgDashboardGrid.setColorFilter(
            ContextCompat.getColor(
                this@DashboardActivity,
                if (isGrid) R.color.blue else R.color.grayBackground
            )
        )
        imgDashboardList.setColorFilter(
            ContextCompat.getColor(
                this@DashboardActivity,
                if (isGrid) R.color.grayBackground else R.color.blue
            )
        )
        recyclerDashboardPods.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    this@DashboardActivity,
                    if (isGrid) R.color.grayBackground else R.color.white
                )
            )
            setPadding(
                if (isGrid) {
                    8.toPx()
                } else {
                    0.toPx()
                }
            )
        }
    }

    private fun showPodsFilterDialog(filterType: Int = DashboardPodsFilterDialogFragment.STATUS_TYPE) {
        DashboardPodsFilterDialogFragment.apply {
            val fragment = if (filterType == STATUS_TYPE) {
                val cleansingDuplicateStatus = dataTemp.distinctBy { it.roomStatusCode }
                val statusFilter = arrayListOf<PodsStatus>().apply {
                    cleansingDuplicateStatus.forEach {
                        add(
                            PodsStatus(
                                it.roomStatusCode.orDefault("ABC"),
                                it.roomStatusLabel.orDefault("Clean")
                            )
                        )
                    }
                }
                newInstanceStatus(statusFilter)
            } else {
                val placeFilter = getPodsPlaces()
                newInstancePlace(placeFilter)
            }
            fragment.show(supportFragmentManager, fragment.tag)
        }
    }

    private fun setPodsStatus(status: PodsStatus) {
        txtDashboardStatus.text = status.name
    }

    private fun showPods(state: UIState<List<PodsData>>) {
        when (state.status) {
            LOADING -> {
                // show progress
            }
            SUCCESS -> {
                val data = state.data.orDefault(null)
                dataTemp.apply {
                    clear()
                    addAll(data.orDefault(emptyList()))
                }

                if (data.isNotNull()) {
                    dashboardPodsAdapter.addAllData(data.orDefault(emptyList()))
                } else {
                    // handle when data is null
                }

                podsDataCalculate.addAll(data.orDefault(emptyList()))
                val roomStatusFilter = podsDataCalculate.distinctBy { it.roomStatusCode }
                podsDataCount.apply {
                    clear()
                    var count = 0
                    roomStatusFilter.forEach { roomFilter ->
                        podsDataCalculate.forEach { room ->
                            if (roomFilter.roomStatusCode == room.roomStatusCode) {
                                count++
                            }
                        }
                        add(
                            PodsCalculate(
                                roomFilter.roomStatusCode.orDefault("ABC"),
                                roomFilter.roomStatusLabel.orDefault("CBA"),
                                count,
                                getPodsCalculateColor(roomFilter.roomStatusCode.orDefault("ABC"))
                            )
                        )
                        count = 0
                    }
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

    private fun getPodsCalculateColor(code: String): String {
        return when (code) {
            "VC" -> "#F0C04F"
            "VD" -> "#A5ACAA"
            "VCI" -> "#DA8751"
            "OUD" -> "#E84E44"
            else -> "#B2ECEB"
        }
    }

    override fun enableBackButton(): Boolean = false

    override fun bindToolbar(): Toolbar? = null

    override fun onDestroyActivities() {}

    override fun getPods() {
        viewModel.getPods()
    }
}
