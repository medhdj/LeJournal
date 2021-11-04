package com.medhdj.business

import io.reactivex.Observable


interface BaseUseCase<RESULT, in PARAMS> where RESULT : Any {
    fun run(params: PARAMS): Observable<RESULT>
}
