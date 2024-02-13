package uz.humoyun.evaluationassignmentleadsx.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import uz.humoyun.evaluationassignmentleadsx.App

fun isConnected(): Boolean = App.instance.isAvailableNetwork()

private fun Context.isAvailableNetwork(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun String.formatToPrice(): String {
    val value = replace(" ", "")
    val reverseValue = StringBuilder(value).reverse()
        .toString()
    val finalValue = StringBuilder()
    for (i in 1..reverseValue.length) {
        val `val` = reverseValue[i - 1]
        finalValue.append(`val`)
        if (i % 3 == 0 && i != reverseValue.length && i > 0) {
            finalValue.append(" ")
        }
    }
    return finalValue.reverse().toString()
}
