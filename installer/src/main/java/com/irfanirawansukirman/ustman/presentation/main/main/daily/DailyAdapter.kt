package com.irfanirawansukirman.ustman.presentation.main.main.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.ustman.databinding.DailyItemBinding

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.ItemHolder>() {

    class ItemHolder(dailyItemBinding: DailyItemBinding) :
        RecyclerView.ViewHolder(dailyItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            DailyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

    }

    override fun getItemCount(): Int = 30
}