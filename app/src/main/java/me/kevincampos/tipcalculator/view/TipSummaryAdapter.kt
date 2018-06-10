package me.kevincampos.tipcalculator.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.databinding.SavedTipCalculationListItemBinding
import me.kevincampos.tipcalculator.viewmodel.TipCalculationSummaryItem

class TipSummaryAdapter(
        val onItemSelected: (item: TipCalculationSummaryItem) -> Unit
) : RecyclerView.Adapter<TipSummaryAdapter.TipSummaryViewHolder>() {

    private val tipCalculationSummaries = mutableListOf<TipCalculationSummaryItem>()

    fun updateList(updates: List<TipCalculationSummaryItem>) {
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
                DataBindingUtil.inflate<SavedTipCalculationListItemBinding>(inflater,
                        R.layout.saved_tip_calculation_list_item, parent, false)

        return TipSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipSummaryViewHolder, position: Int) {
        holder.bind(tipCalculationSummaries[position])
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.size
    }

    inner class TipSummaryViewHolder(
            private val binding: SavedTipCalculationListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tipCalculation: TipCalculationSummaryItem) {
            binding.item = tipCalculation
            binding.executePendingBindings()
            binding.root.setOnClickListener { onItemSelected(tipCalculation) }
        }

    }

}