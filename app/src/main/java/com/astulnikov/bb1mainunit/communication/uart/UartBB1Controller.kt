package com.astulnikov.bb1mainunit.communication.uart

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable


/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface UartBB1Controller {
    fun subscribeForData(): Observable<ByteArray>

    fun sendData(data: ByteArray): Completable

    fun release()
}
