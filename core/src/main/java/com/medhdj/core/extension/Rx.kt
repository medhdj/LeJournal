package com.medhdj.core.extension

import androidx.lifecycle.MutableLiveData
import com.medhdj.core.functionnal.Response
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.runInBackgroundObserveInMain(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

fun <T> Observable<T>.runAndObserveInBackground(): Observable<T> = subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Single<T>.runAndObserveInBackground(): Single<T> = subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Flowable<T>.runAndObserveInBackground(): Flowable<T> = subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T, ERROR, DATA> Observable<T>.startWithLoadingFromWorkerThread(liveData: MutableLiveData<Response<ERROR, DATA>>): Observable<T> =
    doOnSubscribe {
        liveData.postValue(Response.Loading)
    }

fun <T, ERROR, DATA> Flowable<T>.startWithLoadingFromWorkerThread(liveData: MutableLiveData<Response<ERROR, DATA>>): Flowable<T> =
    doOnSubscribe {
        liveData.postValue(Response.Loading)
    }

fun <T, ERROR, DATA> Observable<T>.mapAndConvertToLiveDataInBackground(
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
