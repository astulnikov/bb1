package com.astulnikov.bb1mainunit

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.astulnikov.bb1mainunit.arch.DaggerActivity
import com.astulnikov.bb1mainunit.arch.OnFragmentInteractionListener
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import com.google.android.things.bluetooth.BluetoothConnectionManager
import com.google.android.things.bluetooth.BluetoothPairingCallback
import com.google.android.things.bluetooth.PairingParams
import timber.log.Timber

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class MainActivity : DaggerActivity(), OnFragmentInteractionListener {

    private lateinit var bluetoothConnectionManager: BluetoothConnectionManager

    override fun onFragmentInteraction(uri: String) {
        Timber.d("Url: $uri")
        title = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceMainContent(DashboardFragment.newInstance())

        bluetoothConnectionManager = BluetoothConnectionManager.getInstance().apply {
            Timber.d("registerPairingCallback")
            registerPairingCallback(bluetoothPairingCallback)
        }
    }

    private fun replaceMainContent(fragment: Fragment) {
        replaceFragment(R.id.main_container, fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothConnectionManager.unregisterPairingCallback(bluetoothPairingCallback)
    }

    private val bluetoothPairingCallback = object : BluetoothPairingCallback {

        override fun onPairingInitiated(
                bluetoothDevice: BluetoothDevice,
                pairingParams: PairingParams
        ) {
            Timber.d("onPairingInitiated $bluetoothDevice")
            handlePairingRequest(bluetoothDevice, pairingParams)
        }

        override fun onPaired(bluetoothDevice: BluetoothDevice) {
            Timber.d("bluetoothDevice $bluetoothDevice")
        }

        override fun onUnpaired(bluetoothDevice: BluetoothDevice) {
            Timber.d("onUnpaired $bluetoothDevice")
        }

        override fun onPairingError(
                bluetoothDevice: BluetoothDevice,
                pairingError: BluetoothPairingCallback.PairingError
        ) {
            Timber.d("onPairingError $pairingError")
        }
    }

    private fun handlePairingRequest(bluetoothDevice: BluetoothDevice, pairingParams: PairingParams) {
        Timber.d("pairingType ${pairingParams.pairingType}")
        when (pairingParams.pairingType) {
            PairingParams.PAIRING_VARIANT_DISPLAY_PIN,
            PairingParams.PAIRING_VARIANT_DISPLAY_PASSKEY -> {
                // Display the required PIN to the user
                Timber.d("Display Passkey - ${pairingParams.pairingPin}")
            }
            PairingParams.PAIRING_VARIANT_PIN, PairingParams.PAIRING_VARIANT_PIN_16_DIGITS -> {
                // Obtain PIN from the user
                val pin = "1234"
                // Pass the result to complete pairing
                bluetoothConnectionManager.finishPairing(bluetoothDevice, pin)
            }
            PairingParams.PAIRING_VARIANT_CONSENT,
            PairingParams.PAIRING_VARIANT_PASSKEY_CONFIRMATION -> {
                // Show confirmation of pairing to the user
                Timber.d("Complete the pairing process")
                bluetoothConnectionManager.finishPairing(bluetoothDevice)
            }
        }
    }
}
