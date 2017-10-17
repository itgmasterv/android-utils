package com.example.sahmed.utilityapp.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by mehab on 10/17/2017.
 */
/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : Network related tools
 * </pre>
 */
public class NetworkUtils {

        private NetworkUtils() {
            throw new UnsupportedOperationException("u can't instantiate me...");
        }

        public enum NetworkType {
            NETWORK_WIFI,
            NETWORK_4G,
            NETWORK_3G,
            NETWORK_2G,
            NETWORK_UNKNOWN,
            NETWORK_NO
        }

        /**
         * Open the Network Settings screen
         */
        public static void openWirelessSettings(Context context) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        /**
         * Get active network information
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
         *
         * @return NetworkInfo
         */

        private static NetworkInfo getActiveNetworkInfo(Context context) {
            return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        }

        /**
         * check if NetworkInfo is not = null and connected
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
         *
         * @return {@code true}:yes <br>{@code false}: No
         */

        public static boolean isConnected(Context context) {

            NetworkInfo info = getActiveNetworkInfo(context);
            return info != null && info.isConnected();
        }

        /**
         * Determine if the network is available
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
         * <p>Need asynchronous ping，If the ping is not available, the network is not available</p>
         * <p>ping ip for Alibaba public ip: 223.5.5.5</p>
         *
         * @return {@code true}: Available<br>{@code false}: unavailable
         */
        public static boolean isAvailableByPing() {
            return isAvailableByPing();
        }

        /**
         * Determine if the network is available
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
         * <p>Need asynchronous ping，If the ping is not available, the network is not available</p>
         *
         * @param ip ip address（Own server ip），If it is empty，ip for the Alibaba public ip
         * @return {@code true}: Available<br>{@code false}: Unavailable
         */
//        public static boolean isAvailableByPing(String ip) {
//            if (ip == null || ip.length() <= 0) {
//                ip = "223.5.5.5";// Alibaba public ip
//            }
//            ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false);
//            boolean ret = result.result == 0;
//            if (result.errorMsg != null) {
//                Log.d("NetworkUtils", "isAvailableByPing() called" + result.errorMsg);
//            }
//            if (result.successMsg != null) {
//                Log.d("NetworkUtils", "isAvailableByPing() called" + result.successMsg);
//            }
//            return ret;
//        }

        /**
         * To determine whether the mobile data is open
         *
         * @return {@code true}: Yes<br>{@code false}: No
         */
        public static boolean getDataEnabled(Context context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
                if (null != getMobileDataEnabledMethod) {
                    return (boolean) getMobileDataEnabledMethod.invoke(tm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * Turn the mobile data on or off
         * <p>Need to add permissions to the system application{@code <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>}</p>
         *
         * @param enabled {@code true}: turn on<br>{@code false}: shut down
         */
        public static void setDataEnabled(final boolean enabled,Context context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Method setMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
                if (null != setMobileDataEnabledMethod) {
                    setMobileDataEnabledMethod.invoke(tm, enabled);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Determine whether the network is 4G
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
         *
         * @return {@code true}: Yes<br>{@code false}: no

         */
        public static boolean is4G(Context context) {
            NetworkInfo info = getActiveNetworkInfo(context);
            return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
        }

        /**
         * To determine whether wifi open
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
         *
         * @return {@code true}: Yes<br>{@code false}: no
         */
        public static boolean getWifiEnabled(Context context) {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.isWifiEnabled();
        }

        /**
         * Open or close wifi
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>}</p>
         *
         * @param enabled {@code true}: turn on<br>{@code false}: shut down
         */
        public static void setWifiEnabled(final boolean enabled,Context  context) {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (enabled) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
            } else {
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
            }
        }

        /**
         * Determine whether wifi is connected
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
         *
         * @return {@code true}: connection<br>{@code false}: not connected
         */
        public static boolean isWifiConnected(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm != null && cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        }

        /**
         * Determine if wifi data is available
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
         *
         * @return {@code true}: Yes<br>{@code false}: no
         */
        public static boolean isWifiAvailable(Context context) {
            return getWifiEnabled(context) && isAvailableByPing();
        }

        /**
         * Get the name of the network operator
         * <pChina Mobile, such as China Unicom, China Telecom</p>
         *
         * @return Operator name
         */
        public static String getNetworkOperatorName(Context context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getNetworkOperatorName() : null;
        }

        private static final int NETWORK_TYPE_GSM = 16;
        private static final int NETWORK_TYPE_TD_SCDMA = 17;
        private static final int NETWORK_TYPE_IWLAN = 18;

        /**
         * Gets the current network type
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
         *
         * @return Network Type
         * <ul>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_WIFI   } </li>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_4G     } </li>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_3G     } </li>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_2G     } </li>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_UNKNOWN} </li>
         * <li>{@link NetworkUtils.NetworkType#NETWORK_NO     } </li>
         * </ul>
         */
        public static NetworkType getNetworkType(Context context) {
            NetworkType netType = NetworkType.NETWORK_NO;
            NetworkInfo info = getActiveNetworkInfo(context);
            if (info != null && info.isAvailable()) {

                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = NetworkType.NETWORK_WIFI;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    switch (info.getSubtype()) {

                        case NETWORK_TYPE_GSM:
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netType = NetworkType.NETWORK_2G;
                            break;

                        case NETWORK_TYPE_TD_SCDMA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netType = NetworkType.NETWORK_3G;
                            break;

                        case NETWORK_TYPE_IWLAN:
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netType = NetworkType.NETWORK_4G;
                            break;
                        default:

                            String subtypeName = info.getSubtypeName();
                            if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                    || subtypeName.equalsIgnoreCase("WCDMA")
                                    || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                netType = NetworkType.NETWORK_3G;
                            } else {
                                netType = NetworkType.NETWORK_UNKNOWN;
                            }
                            break;
                    }
                } else {
                    netType = NetworkType.NETWORK_UNKNOWN;
                }
            }
            return netType;
        }

        /**
         * Get the IP address
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
         *
         * @param useIPv4 Whether to use IPv4
         * @return IP address
         */
        public static String getIPAddress(final boolean useIPv4) {
            try {
                for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                    NetworkInterface ni = nis.nextElement();
                    // Prevent millet phone from returning 10.0.2.15
                    if (!ni.isUp()) continue;
                    for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                        InetAddress inetAddress = addresses.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String hostAddress = inetAddress.getHostAddress();
                            boolean isIPv4 = hostAddress.indexOf(':') < 0;
                            if (useIPv4) {
                                if (isIPv4) return hostAddress;
                            } else {
                                if (!isIPv4) {
                                    int index = hostAddress.indexOf('%');
                                    return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                                }
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Get the domain ip address
         * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
         *
         * @param domain domain name
         * @return ip address
         */
        public static String getDomainAddress(final String domain) {
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(domain);
                return inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


