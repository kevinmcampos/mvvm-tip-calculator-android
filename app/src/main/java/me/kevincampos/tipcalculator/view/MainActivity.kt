package me.kevincampos.tipcalculator.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.databinding.ActivityMainBinding
import me.kevincampos.tipcalculator.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity(), SaveDialogFragment.SaveCallback {

    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSave(locationName: String) {
        dataBinding.vm?.saveCurrentTip(locationName)
        Snackbar.make(dataBinding.root, "Saved $locationName", Snackbar.LENGTH_SHORT).show()
    }

    private fun showSaveDialog() {
        val dialog = SaveDialogFragment()
        dialog.show(supportFragmentManager, "SaveDialogFragment")
    }
}
