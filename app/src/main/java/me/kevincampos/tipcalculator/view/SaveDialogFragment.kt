package me.kevincampos.tipcalculator.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import me.kevincampos.tipcalculator.R

class SaveDialogFragment : DialogFragment() {

    // Keep the same ID to the EditText, allowing the typed text to be automatically restored by
    // Android on recreation of the Dialog.
    // Without the same id, it would be necessary to manually handle the savedInstanceState.
    companion object {
        val editTextViewId = View.generateViewId()
    }

    private var onSaveCallback: SaveCallback? = null

    interface SaveCallback {
        fun onSave(locationName: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val saveDialog = context?.let { context ->

            val editText = EditText(context)
            editText.id = editTextViewId
            editText.hint = context.getString(R.string.input_location_name_hint)

            AlertDialog.Builder(context)
                    .setView(editText)
                    .setPositiveButton(R.string.action_save, { _, _ -> onSave(editText) })
                    .setNegativeButton(R.string.action_cancel, null)
                    .create()
        }

        return saveDialog!!
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onSaveCallback = activity as? SaveCallback
    }

    override fun onDetach() {
        super.onDetach()
        onSaveCallback = null
    }

    private fun onSave(editText: EditText) {
        onSaveCallback?.onSave(editText.text.toString())
    }

}