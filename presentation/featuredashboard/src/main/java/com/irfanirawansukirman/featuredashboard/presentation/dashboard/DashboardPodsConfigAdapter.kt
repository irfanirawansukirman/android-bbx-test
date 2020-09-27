package com.irfanirawansukirman.featuredashboard.presentation.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.abstraction.R
import com.irfanirawansukirman.featuredashboard.data.model.PodsErrorType
import com.irfanirawansukirman.featuredashboard.data.model.RoomNote
import com.irfanirawansukirman.featuredashboard.databinding.PodsConfigItemBinding

class DashboardPodsConfigAdapter(
    private val onSelectPods: (Int) -> Unit
) : RecyclerView.Adapter<DashboardPodsConfigAdapter.ItemHolder>() {

    init {
        setHasStableIds(true)
    }

    private val data = arrayListOf<RoomNote>()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            PodsConfigItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindItem(data[holder.adapterPosition])
    }

    override fun getItemCount(): Int = data.size

    inner class ItemHolder(private val viewBinding: PodsConfigItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(roomNote: RoomNote) {
            viewBinding.apply {
                txtPodsConfigErrorType.apply {
                    text = "${roomNote.noteCode}\n${roomNote.noteName}"
                    setOnClickListener {
                        roomNote.selected = !roomNote.selected
                        onSelectPods(adapterPosition)
                    }
                    setTextColor(
                        getTextColorSelected(
                            root.context,
                            roomNote.selected
                        )
                    )
                }
                cardPodsConfigContainer.setCardBackgroundColor(
                    getBackgroundColorSelected(
                        root.context,
                        roomNote.selected
                    )
                )
            }
        }

        private fun getBackgroundColorSelected(context: Context, isSelected: Boolean): Int {
            return ContextCompat.getColor(
                context,
                if (isSelected) {
                    R.color.green
                } else {
                    R.color.grayBackground
                }
            )
        }

        private fun getTextColorSelected(context: Context, isSelected: Boolean): Int {
            return ContextCompat.getColor(
                context,
                if (isSelected) {
                    R.color.greenDark
                } else {
                    R.color.black
                }
            )
        }
    }

    fun notifyByPosition(position: Int) {
        notifyItemChanged(position)
    }

    fun addAllData(data: List<RoomNote>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}