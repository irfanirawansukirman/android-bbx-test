package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.irfanirawansukirman.extensions.putArgs
import com.irfanirawansukirman.extensions.widget.orDefault
import com.irfanirawansukirman.extensions.widget.setLinearList
import com.irfanirawansukirman.featuredashboard.R
import com.irfanirawansukirman.featuredashboard.data.model.PodsPlace
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import kotlinx.android.synthetic.main.pods_filter_dialog.*

class DashboardPodsFilterDialogFragment : BottomSheetDialogFragment(),
    DashboardContract.PodsFilter {

    private lateinit var dashboardPodsFilterAdapter: DashboardPodsFilterAdapter
    private lateinit var statusFilter: java.util.ArrayList<PodsStatus>
    private lateinit var placeFilter: java.util.ArrayList<PodsPlace>

    private val viewModel by activityViewModels<DashboardVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTransparent)

        arguments?.apply {
            statusFilter = getParcelableArrayList<PodsStatus>("status_filter").orDefault(
                ArrayList()
            )
            placeFilter = getParcelableArrayList<PodsPlace>("place_filter").orDefault(
                ArrayList()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pods_filter_dialog, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!::dashboardPodsFilterAdapter.isInitialized) {
            dashboardPodsFilterAdapter = DashboardPodsFilterAdapter({
                dismiss()
                getPodsByStatus(it)
            }, {
                dismiss()
            })
        }

        recyclerPodsFilter.apply {
            setLinearList()
            adapter = dashboardPodsFilterAdapter
        }

        dashboardPodsFilterAdapter.apply {
            if (statusFilter.isNotEmpty()) {
                statusFilter.add(0, PodsStatus("AS", "All Status"))
                addAllStatus(statusFilter)
            } else {
                addAllPlace(placeFilter)
            }
        }
    }

    override fun getPodsByStatus(status: PodsStatus) {
        viewModel.getPodsByStatus(status)
    }

    companion object {
        const val STATUS_TYPE = 0
        const val PLACE_TYPE = 1

        fun newInstanceStatus(statusFilter: ArrayList<PodsStatus>) =
            DashboardPodsFilterDialogFragment().putArgs {
                putParcelableArrayList("status_filter", statusFilter)
            }

        fun newInstancePlace(placeFilter: ArrayList<PodsPlace>) =
            DashboardPodsFilterDialogFragment().putArgs {
                putParcelableArrayList("place_filter", placeFilter)
            }
    }
}