package com.showmenews.usecase.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.showmenews.App
import com.showmenews.arch.repo.RemoteRepository
import com.showmenews.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.showmenews.data.Error
import io.reactivex.functions.Action
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

open class StatefulViewModel : ViewModel() {

    protected val composite: CompositeDisposable = CompositeDisposable()
    var action: Action? = null

    val TIMEOUT = 500L

    var operationMap = ConcurrentHashMap<String, Long>()

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var remoteRepository: RemoteRepository


    fun createActionAndRun(body: () -> Unit) {
        this.action = Action(body)
        this.action?.run()
    }

    fun addDisposable(disposable: Disposable) {
        composite.add(disposable)
    }


    protected val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    protected val _error = SingleLiveData<Error>()
    val error: LiveData<Error>
        get() = _error


    fun Disposable.bind(vm: StatefulViewModel) {
        vm.addDisposable(this)
    }

    override fun onCleared() {
        super.onCleared()
        if (!composite.isDisposed) {
            composite.dispose()
        }
    }

    fun startOperation(operationName: String): Boolean {
        val now = Date()
        return if (operationMap.contains(operationName) && now.before(Date(operationMap[operationName]!!))) {
            Log.d(this::class.java.simpleName, "Operation '$operationName' is now processing...")
            false
        } else {
            operationMap[operationName] = now.time + TIMEOUT
            true
        }
    }
}