package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.extensions.widget.orDefault
import com.irfanirawansukirman.featuredashboard.R
import com.irfanirawansukirman.featuredashboard.data.model.PodsCalculate
import com.irfanirawansukirman.featuredashboard.data.model.PodsData
import com.irfanirawansukirman.featuredashboard.data.model.RoomNote
import com.irfanirawansukirman.featuredashboard.databinding.PodsGridItemBinding
import com.irfanirawansukirman.featuredashboard.databinding.PodsListItemBinding

class DashboardPodsAdapter(
    private val onCancelPods: (String, List<RoomNote>) -> Unit,
    private val onSubmitPods: (Int, PodsData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = arrayListOf<PodsData>()
    private var dataCount = arrayListOf<PodsCalculate>()
    private var VIEW_TYPE = LIST_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (VIEW_TYPE) {
            GRID_TYPE -> ItemGridHolder(
                PodsGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> ItemListHolder(
                PodsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemGridHolder -> holder.bondItem(dataCount[holder.adapterPosition])
            is ItemListHolder -> holder.bindItem(data[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return if (VIEW_TYPE == LIST_TYPE) {
            data.size
        } else {
            dataCount.size
        }
    }

    inner class ItemGridHolder(private val viewBinding: PodsGridItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bondItem(pods: PodsCalculate) {
            viewBinding.apply {
                txtPodsGridTitle.apply {
                    text = pods.name

                    val color = Color.parseColor(pods.color)
                    setTextColor(color)
                    compoundDrawables[0].setTint(color)
                }
                txtPodsGridCount.text = pods.total.toString()
            }
        }
    }

    inner class ItemListHolder(private val viewBinding: PodsListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(pods: PodsData) {
            viewBinding.apply {
                txtPodsListName.text = "Pod Number ${pods.roomName}"

                val code = pods.roomStatusCode
                txtPodsListCode.apply {
                    text = code
                    background = getBackgroundCode(context, code)
                }

                txtPodsListCancel.setOnClickListener {
                    onCancelPods(
                        "${pods.roomStatusLabel} (${pods.roomStatusCode})",
                        pods.roomNotes.orDefault(
                            emptyList()
                        )
                    )
                }
                txtPodsListSubmit.setOnClickListener {
                    onSubmitPods(
                        getRoomNumber(
                            adapterPosition.plus(
                                1
                            )
                        ).toInt(), pods
                    )
                }
            }
        }

        private fun getBackgroundCode(context: Context, code: String?): Drawable? {
            return ContextCompat.getDrawable(
                context,
                when (code) {
                    "OUD" -> R.drawable.bg_round_indicator_red
                    "VD" -> R.drawable.bg_round_indicator_gray
                    else -> R.drawable.bg_round_indicator_yellow
                }
            )
        }

        private fun getRoomNumber(pos: Int): String {
            return if (pos < 10) {
                "0$pos"
            } else {
                "$pos"
            }
        }
    }

    fun deleteAtPosition(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.count())
    }

    fun setupViewType(viewType: Int = GRID_TYPE) {
        VIEW_TYPE = viewType
        notifyDataSetChanged()
    }

    fun addAllData(data: List<PodsData>) {
        this.dataCount.clear()
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addAllCount(data: List<PodsCalculate>) {
        this.data.clear()
        this.dataCount.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    companion object {
        const val GRID_TYPE = 0
        const val LIST_TYPE = 1
    }
}