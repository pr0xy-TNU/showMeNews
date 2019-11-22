package com.showmenews.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, Observer { data ->
            if (data != null) {
                observer.onChanged(data)
                postValue(null)
            }
        })
    }
}