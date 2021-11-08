package com.medhdj.core.extension

import androidx.lifecycle.MutableLiveData
import com.medhdj.core.functionnal.Response
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun <T> Single<T>.runAndObserveInBackground(): Single<T> = subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Flowable<T>.runAndObserveInBackground(): Flowable<T> = subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T, ERROR, DATA> Flowable<T>.startWithLoadingFromWorkerThread(liveData: MutableLiveData<Response<ERROR, DATA>>): Flowable<T> =
    doOnSubscribe {
        liveData.postValue(Response.Loading)
    }

fun <T, ERROR, DATA> Single<T>.startWithLoadingFromWorkerThread(liveData: MutableLiveData<Response<ERROR, DATA>>): Single<T> =
    doOnSubscribe {
        liveData.postValue(Response.Loading)
    }

fun <T, ERROR, DATA> Flowable<T>.mapAndConvertToLiveDataInBackground(
    successHandler: (T) -> DATA,
    errorHandler: (Throwable) -> ERROR,
    liveData: MutableLiveData<Response<ERROR, DATA>>
): Disposable = this
    .runAndObserveInBackground()
    .startWithLoadingFromWorkerThread(liveData)
    .subscribe(
        { data ->
            liveData.postValue(Response.Success(successHandler(data)))
        },
        { throwable ->
            liveData.postValue(Response.Failure(errorHandler(throwable)))
        }
    )

fun <T, ERROR, DATA> Single<T>.mapAndConvertToLiveDataInBackground(
    successHandler: (T) -> DATA,
    errorHandler: (Throwable) -> ERROR,
    liveData: MutableLiveData<Response<ERROR, DATA>>
): Disposable = this
    .runAndObserveInBackground()
    .startWithLoadingFromWorkerThread(liveData)
    .subscribe(
        { data ->
            liveData.postValue(Response.Success(successHandler(data)))
        },
        { throwable ->
            liveData.postValue(Response.Failure(errorHandler(throwable)))
        }
    )
