//package com.example.sahmed.utilityapp.utility;
//
//import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.SystemClock;
//import android.telephony.SmsManager;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.util.Xml;
//
//import org.xmlpull.v1.XmlSerializer;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by mehab on 10/17/2017.
// */
//
//public class PhoneUtils {
//    private PhoneUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    /**
//     * Determine if the device is a mobile phone
//     *
//     * @return {@code true}: Yes <br>{@code false}: No
//     */
//    public static boolean isPhone(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
//    }
//
//    /**
//     * Get the IMEI code
//     * <p> Need to add permission {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
//     *
//     * @return IMEI code
//     */
//    @SuppressLint("HardwareIds")
//    public static String getIMEI(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null ? tm.getDeviceId() : null;
//    }
//
//    /**
//     * Get the IMEI code
//     * <p>Need to add permission  {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
//     *
//     * @return IMEI code
//     */
//    @SuppressLint("HardwareIds")
//    public static String getIMSI(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null ? tm.getSubscriberId() : null;
//    }
//
//    /**
//     * Get the mobile terminal type
//     *
//     * @return Mobile phone system
//     * <ul>
//     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 Mobile phone system is unknown</li>
//     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 Mobile phone system for GSM, mobile and connectivity</li>
//     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 Mobile phone system for CDMA, telecommunications </li>
//     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
//     * </ul>
//     */
//    public static int getPhoneType(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null ? tm.getPhoneType() : -1;
//    }
//
//    /**
//     * Judge whether the sim card is ready
//     *
//     * @return {@code true}: yes <br>{@code false}: No
//     */
//    public static boolean isSimCardReady(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
//    }
//
//    /**
//     * Get Sim card operator name
//     * <p>China Mobile, such as China Unicom, China Telecom</p>
//     *
//     * @return sim card operator name
//     */
//    public static String getSimOperatorName(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tm != null ? tm.getSimOperatorName() : null;
//    }
//
//    /**
//     * Get Sim card operator name
//     * <p>China Mobile, such as China Unicom, China Telecom</p>
//     *
//     * @return Mobile network operator name
//     */
//    public static String getSimOperatorByMnc(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String operator = tm != null ? tm.getSimOperator() : null;
//        if (operator == null) return null;
//        switch (operator) {
//            case "46000":
//            case "46002":
//            case "46007":
//                return "中国移动";
//            case "46001":
//                return "中国联通";
//            case "46003":
//                return "中国电信";
//            default:
//                return operator;
//        }
//    }
//
//    /**
//     * Get phone status information
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
//     *
//     * @return DeviceId(IMEI) = 99000311726612<br>
//     * DeviceSoftwareVersion = 00<br>
//     * Line1Number =<br>
//     * NetworkCountryIso = cn<br>
//     * NetworkOperator = 46003<br>
//     * NetworkOperatorName = China Telecom<br>
//     * NetworkType = 6<br>
//     * honeType = 2<br>
//     * SimCountryIso = cn<br>
//     * SimOperator = 46003<br>
//     * SimOperatorName = China Telecom<br>
//     * SimSerialNumber = 89860315045710604022<br>
//     * SimState = 5<br>
//     * SubscriberId(IMSI) = 460030419724900<br>
//     * VoiceMailNumber = *86<br>
//     */
//    @SuppressLint("HardwareIds")
//    public static String getPhoneStatus(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getApplicationContext()
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        String str = "";
//        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
//        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
//        str += "Line1Number = " + tm.getLine1Number() + "\n";
//        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
//        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
//        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
//        str += "NetworkType = " + tm.getNetworkType() + "\n";
//        str += "PhoneType = " + tm.getPhoneType() + "\n";
//        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
//        str += "SimOperator = " + tm.getSimOperator() + "\n";
//        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
//        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
//        str += "SimState = " + tm.getSimState() + "\n";
//        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
//        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
//        return str;
//    }
//
//    /**
//     * Jump to the dial-up interface
//     *
//     * @param phoneNumber Telephone number
//     */
//    public static void dial(final String phoneNumber,Context context) {
//        context.getApplicationContext().startActivity(IntentUtils.getDialIntent(phoneNumber));
//    }
//
//    /**
//     * dial number
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
//     *
//     * @param phoneNumber 电话号码
//     */
//    public static void call(final String phoneNumber,Context context) {
//        context.getApplicationContext().startActivity(IntentUtils.getCallIntent(phoneNumber));
//    }
//
//    /**
//     * Jump to send SMS interface
//     *
//     * @param phoneNumber Receive number
//     * @param content     SMS content
//     */
//    public static void sendSms(final String phoneNumber, final String content,Context context) {
//        context.getApplicationContext().startActivity(IntentUtils.getSendSmsIntent(phoneNumber, content));
//    }
//
//    /**
//     * send messages
//
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.SEND_SMS"/>}</p>
//     *
//     * @param phoneNumber Receive number
//     * @param content     SMS content
//     */
//    public static void sendSmsSilent(final String phoneNumber, final String content,Context context) {
//        if (StringUtils.isEmpty(content)) return;
//        PendingIntent sentIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, new Intent(), 0);
//        SmsManager smsManager = SmsManager.getDefault();
//        if (content.length() >= 70) {
//            List<String> ms = smsManager.divideMessage(content);
//            for (String str : ms) {
//                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
//            }
//        } else {
//            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
//        }
//    }
//
//    /**
//     * Get the phone contact
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
//     * <p>Need to add permission  {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
//     *
//     * @return Contact list
//     */
//    public static List<HashMap<String, String>> getAllContactInfo(Context context) {
//        SystemClock.sleep(3000);
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        // 1.Get content parser
//        ContentResolver resolver = context.getContentResolver();
//        // 2.Get the address of the content provider:com.android.contacts
//        // raw_contacts Table address :raw_contacts
//        // view_data Table address : data
//        // 3. Generate query address
//        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
//        Uri date_uri = Uri.parse("content://com.android.contacts/data");
//        // 4.Query operation, first query raw_contacts, query contact_id
//        // projection : Query the field
//        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
//        try {
//            // 5.Parse the cursor
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    // 6.Get the query data
//                    String contact_id = cursor.getString(0);
//                    // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
//                    // : Query field in the cursor index value, are generally used in the query field more time
//                    // Determine if contact_id is empty
//                    if (!StringUtils.isEmpty(contact_id)) {//null   ""
//                        // 7.Query the data in the view_data table based on contact_id
//                        // selection : Query conditions
//                        // selectionArgs :Query the parameters of the condition
//                        // sortOrder : Sorting
//                        // Empty pointer: 1.null.Method 2.The parameter is null
//                        Cursor c = resolver.query(date_uri, new String[]{"data1",
//                                        "mimetype"}, "raw_contact_id=?",
//                                new String[]{contact_id}, null);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        // 8.Analysis c
//                        if (c != null) {
//                            while (c.moveToNext()) {
//                                // 9.retrieve data
//                                String data1 = c.getString(0);
//                                String mimetype = c.getString(1);
//                                // 10.According to the type to determine the data1 data obtained and save
//                                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
//                                    // phone
//                                    map.put("phone", data1);
//                                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
//                                    //Name
//                                    map.put("name", data1);
//                                }
//                            }
//                        }
//                        // 11.Added to the data in the collection
//                        list.add(map);
//                        // 12.Close the cursor
//                        if (c != null) {
//                            c.close();
//                        }
//                    }
//                }
//            }
//        } finally {
//            // 12.Close the cursor
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return list;
//    }
//
//    /**
//     * Open the phone contact interface Click the contact to get the number
//     * <p>Refer to the following comment code</p>
//     */
//    public static void getContactNum() {
//        Log.d("tips", "U should copy the following code.");
//        /*
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.PICK");
//        intent.setType("vnd.android.cursor.dir/phone_v2");
//        startActivityForResult(intent, 0);
//
//        @Override
//        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            if (data != null) {
//                Uri uri = data.getData();
//                String num = null;
//                // Create a content resolver
//                ContentResolver contentResolver = getContentResolver();
//                Cursor cursor = contentResolver.query(uri,
//                        null, null, null, null);
//                while (cursor.moveToNext()) {
//                    num = cursor.getString(cursor.getColumnIndex("data1"));
//                }
//                cursor.close();
//                num = num.replaceAll("-", "");//Replace the operation,555-6 -> 5556
//            }
//        }
//        */
//    }
//
//    /**
//     * Get SMS and save it in xml
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
//     * <p>Need to add permission {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
//     */
//    public static void getAllSMS(Context context) {
//        // 1.Get SMS
//        // 1.Get content parser
//        ContentResolver resolver = context.getApplicationContext().getContentResolver();
//        // 1.2 Get the content provider address   sms,sms Table address:null  Do not write
//        // 1.3 Get the query path
//        Uri uri = Uri.parse("content://sms");
//        // 1.4.Query operation
//        // projection : Query the field
//        // selection :
//        // selectionArgs : Query the parameters of the condition
//        // sortOrder : Sorting
//        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
//        // Set the maximum progress
//        int count = cursor.getCount();//Get the number of messages
//        // 2.Backup SMS
//        // 2.1 Get the xml sequencer
//        XmlSerializer xmlSerializer = Xml.newSerializer();
//        try {
//            // 2.2 Set the path where the xml file is saved
//            // os : Saved position
//            // encoding : Encoding format
//
//            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
//            // 2.3 Set header information
//            // standalone : 是否独立保存
//            xmlSerializer.startDocument("utf-8", true);
//            // 2.4 Set the root label
//            xmlSerializer.startTag(null, "smss");
//            // 1.5.Parse the cursor
//            while (cursor.moveToNext()) {
//                SystemClock.sleep(1000);
//                // 2.5 Set the label for the message
//                xmlSerializer.startTag(null, "sms");
//                // 2.6 Set the label for the message
//                xmlSerializer.startTag(null, "address");
//                String address = cursor.getString(0);
//                // 2.7 Set the text content
//                xmlSerializer.text(address);
//                xmlSerializer.endTag(null, "address");
//                xmlSerializer.startTag(null, "date");
//                String date = cursor.getString(1);
//                xmlSerializer.text(date);
//                xmlSerializer.endTag(null, "date");
//                xmlSerializer.startTag(null, "type");
//                String type = cursor.getString(2);
//                xmlSerializer.text(type);
//                xmlSerializer.endTag(null, "type");
//                xmlSerializer.startTag(null, "body");
//                String body = cursor.getString(3);
//                xmlSerializer.text(body);
//                xmlSerializer.endTag(null, "body");
//                xmlSerializer.endTag(null, "sms");
//                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
//            }
//            xmlSerializer.endTag(null, "smss");
//            xmlSerializer.endDocument();
//            // 2.8 Refresh the data into the file
//            xmlSerializer.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
