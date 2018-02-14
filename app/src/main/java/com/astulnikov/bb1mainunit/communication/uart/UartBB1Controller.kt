package com.astulnikov.bb1mainunit.communication.uart

import io.reactivex.Completable
import io.reactivex.Observable

/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface UartBB1Controller {
    fun subscribeForData(): Observable<ByteArray>

    fun sendData(data: ByteArray): Completable

    fun release()
}
