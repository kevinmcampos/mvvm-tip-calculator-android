package me.kevincampos.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import com.android.databinding.library.baseAdapters.BR

abstract class ObservableViewModel(app: Application) : AndroidViewModel(app), Observable {

    // The first time we reference mCallbacks, it will instantiate PropertyChangeRegistry()
    // It is also thread safe because the access to its lazy lambda is synchronized.
    // And reduce boilerplate like this:
    //
    //    synchronized (this)
    //    {
    //        if (mCallbacks == null) {
    //            mCallbacks = PropertyChangeRegistry()
    //        }
    //    }
    @delegate:Transient
    private val mCallbacks : PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }

    fun notifyChange() {
        mCallbacks.notifyChange(this, BR._all)
    }

}