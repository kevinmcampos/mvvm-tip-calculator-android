package me.kevincampos.tipcalculator.view

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.saved_tip_calculation_list.view.*
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.viewmodel.CalculatorViewModel

class LoadDialogFragment : DialogFragment() {

    private var onLoadCallback: LoadCallback? = null

    interface LoadCallback {
        fun onLoad(locationName: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val loadDialog = context?.let { context ->


            AlertDialog.Builder(context)
                    .setView(createView(context))
                    .setNegativeButton(R.string.action_cancel, null)
                    .create()
        }

        return loadDialog!!
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onLoadCallback = activity as? LoadCallback
    }

    override fun onDetach() {
        super.onDetach()
        onLoadCallback = null
    }

    private fun createView(context: Context): View {
        val recyclerView = LayoutInflater
                .from(context)
                .inflate(R.layout.saved_tip_calculation_list, null)
                .recycler_view_calculations

        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val adapter = TipSummaryAdapter {
            onLoadCallback?.onLoad(it.locationName)
            dismiss()
        }
        recyclerView.adapter = adapter

        val vm = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        vm.loadSavedTipCalculationSummaries().observe(this, Observer {
            if (it != null) {
                adapter.updateList(it)
            }
        })

        return recyclerView
    }

}