package com.example.myresponsive.activity.ui.transform

import android.content.Context
import android.content.pm.PackageManager
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object SignatureVerifier {

    private const val EXPECTED_SIGNATURE = "YOUR_EXPECTED_SIGNATURE_HASH"

    private var nativeLibLoaded = false

    init {
        try {
            System.loadLibrary("signature_check")
            nativeLibLoaded = true
        } catch (e: UnsatisfiedLinkError) {
            nativeLibLoaded = false
        }
    }

    private external fun nativeGetSignatureHash(context: Context): String

    fun verify(context: Context): Boolean {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            val signatures = packageInfo.signatures
            if (signatures.isNotEmpty()) {
                val signature = signatures[0]
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(signature.toByteArray())
                val hexString = digest.joinToString("") { "%02x".format(it) }
                hexString == EXPECTED_SIGNATURE
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun verifyNative(context: Context): Boolean {
        if (!nativeLibLoaded) {
            return false
        }
        return try {
            val nativeHash = nativeGetSignatureHash(context)
            nativeHash == EXPECTED_SIGNATURE
        } catch (e: Exception) {
            false
        }
    }

    fun verifyDual(context: Context): Boolean {
        val kotlinOk = verify(context)
        val nativeOk = verifyNative(context)
        return kotlinOk && nativeOk
    }
}

private fun ByteArray.toByteArrayInputStream() = java.io.ByteArrayInputStream(this)