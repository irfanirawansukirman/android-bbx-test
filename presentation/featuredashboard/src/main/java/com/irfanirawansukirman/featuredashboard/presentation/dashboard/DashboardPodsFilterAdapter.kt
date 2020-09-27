package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.featuredashboard.data.model.PodsPlace
import com.irfanirawansukirman.featuredashboard.data.model.PodsStatus
import com.irfanirawansukirman.featuredashboard.databinding.PodsFilterItemBinding

class DashboardPodsFilterAdapter(
    private val onSelectRoomStatus: (PodsStatus) -> Unit,
    private val onConfigRoom: () -> Unit
) :
    RecyclerView.Adapter<DashboardPodsFilterAdapter.ItemHolder>() {

    private val statusFilter = arrayListOf<PodsStatus>()
    private val placeFilter = arrayListOf<PodsPlace>()
    private var filterType = DashboardPodsFilterDialogFragment.STATUS_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            PodsFilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.apply {
            if (filterType == DashboardPodsFilterDialogFragment.STATUS_TYPE) {
                bindStatus(statusFilter[holder.adapterPosition])
            } else {
                bindPlace(placeFilter[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int =
        if (filterType == DashboardPodsFilterDialogFragment.STATUS_TYPE) {
            statusFilter.size
        } else {
            placeFilter.size
        }

    inner class ItemHolder(private val viewBinding: PodsFilterItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindStatus(status: PodsStatus) {
            viewBinding.txtPodsFilterName.apply {
                text = status.name
                setOnClickListener { onSelectRoomStatus(status) }
            }
        }

        fun bindPlace(place: PodsPlace) {
            viewBinding.txtPodsFilterName.apply {
                text = place.name
                setOnClickListener { onConfigRoom() }
            }
        }
    }

    fun addAllStatus(data: ArrayList<PodsStatus>) {
        filterType = DashboardPodsFilterDialogFragment.STATUS_TYPE
        this.statusFilter.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addAllPlace(data: ArrayList<PodsPlace>) {
        filterType = DashboardPodsFilterDialogFragment.PLACE_TYPE
        this.placeFilter.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}