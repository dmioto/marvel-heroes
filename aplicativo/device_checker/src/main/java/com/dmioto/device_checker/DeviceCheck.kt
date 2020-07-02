//package com.dmioto.device_checker
//
//import android.content.Context
//import com.dmioto.device_checker.network_checker.NetworkChecker
//import com.dmioto.device_checker.network_checker.NetworkType
//
//class DeviceCheck(private val context: Context) {
//
//    fun getAppVersion(): String {
//        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
//    }
//
//
//    fun netCheck(response: (String) -> Unit){
//        NetworkChecker(context) { canConnectToInternet, networkType ->
//            if(canConnectToInternet){
//                when(networkType){
//                    NetworkType.WIFI -> response(context.getString(R.string.connected_on_internet))
//                    NetworkType.NOT_CONNECTED -> response(context.getString(R.string.no_internet_connection))
//                    NetworkType.OTHER -> response(context.getString(R.string.no_internet_connection))
//                    NetworkType.MOBILE_2G -> response(context.getString(R.string.connected_to_2g))
//                    NetworkType.MOBILE_3G -> response(context.getString(R.string.connected_to_3g))
//                    NetworkType.MOBILE_4G -> response(context.getString(R.string.connected_to_4g))
//                }
//            } else {
//                response(context.getString(R.string.no_internet_connection))
//            }
//        }
//    }
//
//}