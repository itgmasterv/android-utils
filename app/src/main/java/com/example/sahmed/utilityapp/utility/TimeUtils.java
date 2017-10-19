package com.example.sahmed.utilityapp.utility;

import com.example.sahmed.utilityapp.utility.constants.TimeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sahmed on 10/19/2017.
 */

public class TimeUtils {
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Change the timestamp to a time string
     * <p> format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param millis millisecond timestamp
     * @return time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * Change the timestamp to a time string
     * <p> format is format </ p>
     *
     * @param millis millisecond timestamp
     * @param format time format
     * @return time string
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * Turn the time string into a timestamp
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return millisecond timestamp
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * Turn the time string into a timestamp
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return millisecond timestamp
     *      
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Converts the time string to the Date type
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return Date type
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * Converts the time string to the Date type
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return Date type
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Change the Date type to a time string
     * <p> format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param date Date type time
     * @return time string
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * Change the Date type to a time string
     * <p> format is format </ p>
     *
     * @param date   Date type time
     * @param format time format
     * @return time string
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * Change the Date type to a timestamp
     *
     * @param date Date type time
     * @return millisecond timestamp
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * Change the timestamp to the Date type
     *
     * @param millis millisecond timestamp
     * @return Date Type Time
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * Obtain two time differences (unit: unit)
     * <p> Both time0 and time1 are yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time0 time string 0
     * @param time1 time string 1
     * @param unit  unit type
     *              <ul>
     *              <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *              <li> {@ link TimeConstants # SEC}: seconds </li>
     *              <li> {@ link TimeConstants # MIN}: points </li>
     *              <li> {@ link TimeConstants # HOUR}: hours </li>
     *              <li> {@ link TimeConstants # DAY}: days </li>
     *              </ul>
     * @return unit timestamp
     */
    public static long getTimeSpan(final String time0, final String time1, @TimeConstants.Unit final int unit) {
        return getTimeSpan(time0, time1, DEFAULT_FORMAT, unit);
    }

    /**
     * Obtain two time differences (unit: unit)
     * <p> Both time0 and time1 are format </p>
     *
     * @param time0  time string 0
     * @param time1  time string 1
     * @param format time format
     * @param unit   unit type
     *               <ul>
     *               <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *               <li> {@ link TimeConstants # SEC}: seconds </li>
     *               <li> {@ link TimeConstants # MIN}: points </li>
     *               <li> {@ link TimeConstants # HOUR}: hours </li>
     *               <li> {@ link TimeConstants # DAY}: days </li>
     *               </ul>
     * @return unit timestamp
     */
    public static long getTimeSpan(final String time0, final String time1, final DateFormat format, @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(string2Millis(time0, format) - string2Millis(time1, format)), unit);
    }

    /**
     * Obtain two time differences (unit: unit)
     *
     * @param date0 Date Type Time 0
     * @param date1 Date Type 1
     * @param unit  unit type
     *              <ul>
     *              <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *              <li> {@ link TimeConstants # SEC}: seconds </li>
     *              <li> {@ link TimeConstants # MIN}: points </li>
     *              <li> {@ link TimeConstants # HOUR}: hours </li>
     *              <li> {@ link TimeConstants # DAY}: days </li>
     *              </ul>
     * @return unit timestamp
     */
    public static long getTimeSpan(final Date date0, final Date date1, @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * Obtain two time differences (unit: unit)
     *
     * @param millis0 millisecond timestamp 0
     * @param millis1 millisecond timestamp 1
     * @param unit    unit type
     *                <ul>
     *                <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                <li> {@ link TimeConstants # SEC}: seconds </li>
     *                <li> {@ link TimeConstants # MIN}: points </li>
     *                <li> {@ link TimeConstants # HOUR}: hours </li>
     *                <li> {@ link TimeConstants # DAY}: days </li>
     *                </ul>
     * @return unit timestamp
     */
    public static long getTimeSpan(final long millis0, final long millis1, @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * Obtain the appropriate two time difference
     * <p> Both time0 and time1 are yyyy-MM-dd HH: mm: ss </p>
     *
     * @param time0     time string 0
     * @param time1     time string 1
     * @param precision <p> precision = 0, return null </p>
     *                  <p> precision = 1, return day </p>
     *                  <p> precision = 2, return day and hour </p>
     *                  <p> precision = 3, return day, hour and minute </p>
     *                  <p> precision = 4, return days, hours, minutes and seconds </p>
     *                  <p> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </p>
     * @return fit two time difference
     */
    public static String getFitTimeSpan(final String time0, final String time1, final int precision) {
        return millis2FitTimeSpan(Math.abs(string2Millis(time0, DEFAULT_FORMAT) - string2Millis(time1, DEFAULT_FORMAT)), precision);
    }

    /**
     * Obtain the appropriate two time difference
     * <p> Both time0 and time1 are format </p>
     *
     * @param time0     time string 0
     * @param time1     time string 1
     * @param format    time format
     * @param precision <p> precision = 0, return null </p>
     *                  <p> precision = 1, return day </p>
     *                  <p> precision = 2, return day and hour </p>
     *                  <p> precision = 3, return day, hour and minute </p>
     *                  <p> precision = 4, return days, hours, minutes and seconds </p>
     *                  <p> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </p>
     * @return fit two time difference
     */
    public static String getFitTimeSpan(final String time0, final String time1, final DateFormat format, final int precision) {
        return millis2FitTimeSpan(Math.abs(string2Millis(time0, format) - string2Millis(time1, format)), precision);
    }

    /**
     *  Obtain the appropriate two time difference
     *  @param date0 Date Type Time 0
     *  @param date1 Date Type 1
     *  @param precision
     *  <p> precision = 0, return null </p>
     *  <p> precision = 1, return day </p>
     *  <p> precision = 2, return day and hour </p>
     *  <p> precision = 3, return day, hour and minute </p>
     *  <p> precision = 4, return days, hours, minutes and seconds </p>
     *  <p> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </p>
     *
     * @return fit two time difference
     */
    public static String getFitTimeSpan(final Date date0, final Date date1, final int precision) {
        return millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * Obtain the appropriate two time difference
     *
     * @param millis0   millisecond timestamp 1
     * @param millis1   millisecond timestamp 2
     * @param precision <p> precision = 0, return null </p>
     *                  <p> precision = 1, return day </p>
     *                  <p> precision = 2, return day and hour </p>
     *                  <p> precision = 3, return day, hour and minute </p>
     *                  <p> precision = 4, return days, hours, minutes and seconds </p>
     *                  <p> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </p>
     * @return fit two time difference
     */
    public static String getFitTimeSpan(final long millis0, final long millis1, final int precision) {
        return millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * Get the current millisecond timestamp
     *
     * @return millisecond timestamp
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * Get the current time string
     * <p> format is yyyy-MM-dd HH: mm: ss </p>
     *
     * @return time string
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * Get the current time string
     * <p> format is format </p>
     *
     * @param format time format
     * @return time string
     */
    public static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * Get the current Date
     *
     * @return Date Type Time
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Obtain a difference from the current time (unit: unit)
     * <p> time format is yyyy-MM-dd HH: mm: ss </p>
     *
     * @param time time string
     * @param unit unit type
     *             <ul>
     *             <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *             <li> {@ link TimeConstants # SEC}: seconds </li>
     *             <li> {@ link TimeConstants # MIN}: points </li>
     *             <li> {@ link TimeConstants # HOUR}: hours </li>
     *             <li> {@ link TimeConstants # DAY}: days </li>
     *             </ul>
     * @return unit timestamp
     */
    public static long getTimeSpanByNow(final String time, @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(), time, DEFAULT_FORMAT, unit);
    }

    /**
     * Obtain a difference from the current time (unit: unit)
     * <p> time format is format </p>
     *
     * @param time   time string
     * @param format time format
     * @param unit   unit type
     *               <ul>
     *               <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *               <li> {@ link TimeConstants # SEC}: seconds </li>
     *               <li> {@ link TimeConstants # MIN}: points </li>
     *               <li> {@ link TimeConstants # HOUR}: hours </li>
     *               <li> {@ link TimeConstants # DAY}: days </li>
     *               </ul>
     * @return unit timestamp
     */
    public static long getTimeSpanByNow(final String time, final DateFormat format, @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(format), time, format, unit);
    }

    /**
     * Obtain a difference from the current time (unit: unit)
     *
     * @param date Date type time
     * @param unit unit type
     *             <ul>
     *             <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *             <li> {@ link TimeConstants # SEC}: seconds </li>
     *             <li> {@ link TimeConstants # MIN}: points </li>
     *             <li> {@ link TimeConstants # HOUR}: hours </li>
     *             <li> {@ link TimeConstants # DAY}: days </li>
     *             </ul>
     * @return unit timestamp
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants.Unit final int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     *  Obtain a difference from the current time (unit: unit)
     *  @param millis millisecond timestamp
     *  @param unit unit type
     * <ul>
     * <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     * <li> {@ link TimeConstants # SEC}: seconds </li>
     * <li> {@ link TimeConstants # MIN}: points </li>
     * <li> {@ link TimeConstants # HOUR}: hours </li>
     * <li> {@ link TimeConstants # DAY}: days </li>
     * </ul>
     *
     * @return unit timestamp
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * Get the difference between the appropriate type and the current time
     * <p> time format is yyyy-MM-dd HH: mm: ss </p>
     *
     * @param time      time string
     * @param precision  <ul>
     *                   <li> precision = 0, return null </li>
     *                   <li> precision = 1, return day </li>
     *                   <li> precision = 2, return day and hour </li>
     *                   <li> precision = 3, return days, hours and minutes </li>
     *                   <li> precision = 4, return days, hours, minutes and seconds </li>
     *                   <li> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </li>
     *                   </ul>
     * @return the difference between the fit and the current time
     */
    public static String getFitTimeSpanByNow(final String time, final int precision) {
        return getFitTimeSpan(getNowString(), time, DEFAULT_FORMAT, precision);
    }

    /**
     *  Get the difference between the appropriate type and the current time
     *  <p> time format is format </p>
     *  @param time time string
     *  @param format time format
     *  @param precision
     *  <ul>
     *  <li> precision = 0, return null </li>
     *  <li> precision = 1, return day </li>
     *  <li> precision = 2, return day and hour </li>
     *  <li> precision = 3, return days, hours and minutes </li>
     *  <li> precision = 4, return days, hours, minutes and seconds </li>
     * <li> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </li>
     * </ul>
     *
     * @return the difference between the fit and the current time
     */
    public static String getFitTimeSpanByNow(final String time, final DateFormat format, final int precision) {
        return getFitTimeSpan(getNowString(format), time, format, precision);
    }

    /**
     * Get the difference between the appropriate type and the current time
     *
     * @param date      Date type time
     * @param precision <ul>
     *                  <li> precision = 0, return null </li>
     *                  <li> precision = 1, return day </li>
     *                  <li> precision = 2, return day and hour </li>
     *                  <li> precision = 3, return days, hours and minutes </li>
     *                  <li> precision = 4, return days, hours, minutes and seconds </li>
     *                  <li> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </li>
     *                  </ul>
     * @return the difference between the fit and the current time
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(getNowDate(), date, precision);
    }

    /**
     *  Get the difference between the appropriate type and the current time
     *
     * @param millis    millisecond timestamp
     * @param precision <ul>
     *                  <li> precision = 0, return null </li>
     *                  <li> precision = 1, return day </li>
     *                  <li> precision = 2, return day and hour </li>
     *                  <li> precision = 3, return days, hours and minutes </li>
     *                  <li> precision = 4, return days, hours, minutes and seconds </li>
     *                  <li> precision & gt; = 5, return days, hours, minutes, seconds and milliseconds </li>
     *                  </ul>
     * @return the difference between the fit and the current time
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * Get the difference between friendly and current time
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return friendly difference with current time
     * <ul>
     * <li> If less than 1 second, just show </ li>
     * <li> If it is displayed within 1 minute before XXX seconds </ li>
     * <li> if it is displayed within 1 hour before XXX minutes </ li>
     * <li> If today in 1 hour, show today 15:32 </ li>
     * <li> if it is yesterday, show yesterday 15:32 </ li>
     * <li> the rest of the show, 2016-10-15 </ li>
     * <li> Time is not legal All date and time information, such as Saturday October 27 14:21:20 CST 2007 </ li>
     * </ ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_FORMAT);
    }

    /**
     * Get the difference between friendly and current time
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return friendly difference with current time
     * <ul>
     * <li> If less than 1 second, just show </ li>
     * <li> If it is displayed within 1 minute before XXX seconds </ li>
     * <li> if it is displayed within 1 hour before XXX minutes </ li>
     * <li> If today in 1 hour, show today 15:32 </ li>
     * <li> if it is yesterday, show yesterday 15:32 </ li>
     * <li> the rest of the show, 2016-10-15 </ li>
     * <li> Time is not legal All date and time information, such as Saturday October 27 14:21:20 CST 2007 </ li>
     * </ ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time, final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * Get the difference between friendly and current time
     *
     * @param date Date type time
     * @return friendly difference with current time
     * <ul>
     * <li> If less than 1 second, just show </ li>
     * <li> If it is displayed within 1 minute before XXX seconds </ li>
     * <li> if it is displayed within 1 hour before XXX minutes </ li>
     * <li> If today in 1 hour, show today 15:32 </ li>
     * <li> if it is yesterday, show yesterday 15:32 </ li>
     * <li> the rest of the show, 2016-10-15 </ li>
     * <li> Time is not legal All date and time information, such as Saturday October 27 14:21:20 CST 2007 </ li>
     * </ ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * Get the difference between friendly and current time
     *
     * @param millis millisecond timestamp
     * @return friendly difference with current time
     * <ul>
     * <li> If less than 1 second, just show </ i>
     * <li> If it is displayed within 1 minute before XXX seconds </li>
     * <li> if it is displayed within 1 hour before XXX minutes </li>
     * <li> If today in 1 hour, show today 15:32 </li>
     * <li> if it is yesterday, show yesterday 15:32 </li>
     * <li> the rest of the show, 2016-10-15 </li>
     * <li> Time is not legal All date and time information, such as Saturday October 27 14:21:20 CST 2007 </li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return String.format("%tc", millis);// U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        if (span < 1000) {
            return "just";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%dSeconds ago", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%dminutes ago", span / TimeConstants.MIN);
        }
        // Get the day 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("Nowadays%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("yesterday%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * Get week of today
     *
     * @return get week of today in milli sec
     */
    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Get a timestamp with a given time equal to the time difference
     *
     * @param millis   given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return timestamp with a given time equal to the time difference
     */
    public static long getMillis(final long millis, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Get a timestamp with a given time equal to the time difference
     * <p> time format is yyyy-MM-dd HH: mm: ss </p>
     *
     * @param time     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return timestamp with a given time equal to the time difference
     */
    public static long getMillis(final String time, final long timeSpan, @TimeConstants.Unit final int unit) {
        return getMillis(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * Get a timestamp with a given time equal to the time difference
     * <p> time format is format </ p>
     *
     * @param time     given time
     * @param format   time format
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return timestamp with a given time equal to the time difference
     */
    public static long getMillis(final String time, final DateFormat format, final long timeSpan, @TimeConstants.Unit final int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Get a timestamp with a given time equal to the time difference
     *
     * @param date     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return timestamp with a given time equal to the time difference
     */
    public static long getMillis(final Date date, final long timeSpan, @TimeConstants.Unit final int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param millis   given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final long millis, final long timeSpan, @TimeConstants.Unit final int unit) {
        return getString(millis, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> format is format </p>
     *
     * @param millis   given time
     * @param format   time format
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </li>
     *                 <li> {@ link TimeConstants # MIN}: points </li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </li>
     *                 <li> {@ link TimeConstants # DAY}: days </li>
     *                 </ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final long millis, final DateFormat format, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final String time, final long timeSpan, @TimeConstants.Unit final int unit) {
        return getString(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> format is format </ p>
     *
     * @param time     given time
     * @param format   time format
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final String time, final DateFormat format, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param date     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final Date date, final long timeSpan, @TimeConstants.Unit final int unit) {
        return getString(date, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * Get a time string that is equal to the time difference for a given time
     * <p> format is format </ p>
     *
     * @param date     given time
     * @param format   time format
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return is a time string with a given time equal to the time difference
     */
    public static String getString(final Date date, final DateFormat format, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * Get Date with a given time equal to the time difference
     *
     * @param millis   given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with a given time equal to the time difference
     */
    public static Date getDate(final long millis, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Get Date with a given time equal to the time difference
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with a given time equal to the time difference
     */
    public static Date getDate(final String time, final long timeSpan, @TimeConstants.Unit final int unit) {
        return getDate(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * Get Date with a given time equal to the time difference
     * <p> format is format </ p>
     *
     * @param time     given time
     * @param format   time format
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with a given time equal to the time difference
     */
    public static Date getDate(final String time, final DateFormat format, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Get Date with a given time equal to the time difference
     *
     * @param date     given time
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with a given time equal to the time difference
     */
    public static Date getDate(final Date date, final long timeSpan, @TimeConstants.Unit final int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Get a timestamp with the current time equal to the time difference
     *
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return is timestamp with the current time equal to the time difference
     */
    public static long getMillisByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * Get the time string with the current time equal to the time difference
     * <p> format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with the current time is equal to the time difference between the time string
     */
    public static String getStringByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getStringByNow(timeSpan, DEFAULT_FORMAT, unit);
    }

    /**
     * Get the time string with the current time equal to the time difference
     * <p> format is format </ p>
     *
     * @param timeSpan time difference in milliseconds timestamp
     * @param format   time format
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with the current time is equal to the time difference between the time string
     */
    public static String getStringByNow(final long timeSpan, final DateFormat format, @TimeConstants.Unit final int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * Get Date with the current time equal to the time difference
     *
     * @param timeSpan time difference in milliseconds timestamp
     * @param unit     unit type
     *                 <ul>
     *                 <li> {@ link TimeConstants # MSEC}: milliseconds </ li>
     *                 <li> {@ link TimeConstants # SEC}: seconds </ li>
     *                 <li> {@ link TimeConstants # MIN}: points </ li>
     *                 <li> {@ link TimeConstants # HOUR}: hours </ li>
     *                 <li> {@ link TimeConstants # DAY}: days </ li>
     *                 </ ul>
     * @return with the current time equal to the time difference
     */
    public static Date getDateByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    /**
     * Judge today
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, DEFAULT_FORMAT));
    }

    /**
     * Judge today
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isToday(final String time, final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * Judge today
     *
     * @param date Date type time
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * Judge today
     *
     * @param millis millisecond timestamp
     * @return {@code true}: Yes {@code false}: No
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * Judge whether the leap year
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return {@code true}: leap year {@code false}: plain
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * Judge whether the leap year
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return {@code true}: leap year {@code false}: plain
     */
    public static boolean isLeapYear(final String time, final DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * Judge whether the leap year
     *
     * @param date Date type time
     * @return {@code true}: leap year {@code false}: plain
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * Judge whether the leap year
     *
     * @param millis millisecond timestamp
     * @return {@code true}: leap year {@code false}: plain
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * Judge whether the leap year
     *
     * @param year year
     * @return {@code true}: leap year {@code false}: plain
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Get the week index
     * <p> Note: Sunday's Index is 1, Saturday for 7 </ p>
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return 1 ... 7
     * @see Calendar # SUNDAY
     * @see Calendar # MONDAY
     * @see Calendar # TUESDAY
     * @see Calendar # WEDNESDAY
     * @see Calendar # THURSDAY
     * @see Calendar # FRIDAY
     * @see Calendar # SATURDAY
     */
    public static int getWeekIndex(final String time) {
        return getWeekIndex(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * Get the week index
     * <p> Note: Sunday's Index is 1, Saturday for 7 </ p>
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return 1 ... 7
     * @see Calendar # SUNDAY
     * @see Calendar # MONDAY
     * @see Calendar # TUESDAY
     * @see Calendar # WEDNESDAY
     * @see Calendar # THURSDAY
     * @see Calendar # FRIDAY
     * @see Calendar # SATURDAY
     */
    public static int getWeekIndex(final String time, final DateFormat format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * Get the week index
     * <p> Note: Sunday's Index is 1, Saturday for 7 </ p>
     *
     * @param date Date type time
     * @return 1 ... 7
     * @see Calendar # SUNDAY
     * @see Calendar # MONDAY
     * @see Calendar # TUESDAY
     * @see Calendar # WEDNESDAY
     * @see Calendar # THURSDAY
     * @see Calendar # FRIDAY
     * @see Calendar # SATURDAY
     */
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Get the week index
     * <p> Note: Sunday's Index is 1, Saturday for 7 </ p>
     *
     * @param millis millisecond timestamp
     * @return 1 ... 7
     * @see Calendar # SUNDAY
     * @see Calendar # MONDAY
     * @see Calendar # TUESDAY
     * @see Calendar # WEDNESDAY
     * @see Calendar # THURSDAY
     * @see Calendar # FRIDAY
     * @see Calendar # SATURDAY
     */
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * Get the first few weeks in the month
     * <p> Note: foreign Sunday is the beginning of a new week </p>
     * <p> time format is yyyy-MM-dd HH: mm: ss </p>
     *
     * @param time time string
     * @return 1 ... 5
     */
    public static int getWeekOfMonth(final String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * Get the first few weeks in the month
     * <p> Note: foreign Sunday is the beginning of a new week </ p>
     * <p> time format is format </ p>
     *
     * @param time   time string
     * @param format time format
     * @return 1 ... 5
     */
    public static int getWeekOfMonth(final String time, final DateFormat format) {
        return getWeekOfMonth(string2Date(time, format));
    }

    /**
     * Get the first few weeks in the month
     * <p> Note: foreign Sunday is the beginning of a new week </ p>
     *
     * @param date Date type time
     * @return 1 ... 5
     */
    public static int getWeekOfMonth(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Get the first few weeks in the month
     * <p> Note: foreign Sunday is the beginning of a new week </p>
     *
     * @param millis millisecond timestamp
     * @return 1 ... 5
     */
    public static int getWeekOfMonth(final long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * Get the first few weeks in the year
     * <p> Note: foreign Sunday is the beginning of a new week </ p>
     * <p> time format is yyyy-MM-dd HH: mm: ss </ p>
     *
     * @param time time string
     * @return 1 ... 54
     */
    public static int getWeekOfYear(final String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * Get the first few weeks in the year
     * <p> Note: foreign Sunday is the beginning of a new week </p>
     * <p> time format is format </p>
     *
     * @param time   time string
     * @param format time format
     * @return 1 ... 54
     */
    public static int getWeekOfYear(final String time, final DateFormat format) {
        return getWeekOfYear(string2Date(time, format));
    }

    /**
     * Get the first few weeks in the year
     * <p> Note: foreign Sunday is the beginning of a new week </p>
     *
     * @param date Date type time
     * @return 1 ... 54
     */
    public static int getWeekOfYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Get the first few weeks in the year
     * <p> Note: foreign Sunday is the beginning of a new week </ p>
     *
     * @param millis millisecond timestamp
     * @return 1 ... 54
     */
    public static int getWeekOfYear(final long millis) {
        return getWeekOfYear(millis2Date(millis));
    }


    private static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (millis < 0 || precision <= 0) return null;
        precision = Math.min(precision, 5);
        String[] units = {"day", "hour", "minute", "second", "millisecond"};
        if (millis == 0) return 0 + units[precision - 1];
        StringBuilder sb = new StringBuilder();
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }
}
