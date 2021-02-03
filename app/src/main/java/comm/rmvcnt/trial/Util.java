package comm.rmvcnt.trial;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Util {

    final public static Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final int EOF = -1;
    private static final long A_DAY = 86400 * 1000;

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float getValueInRange(float percentage,float start,float end){
       return (percentage * (end - start) / 100) + start;
    }

    public static float getPercentInRange(float value,float start,float end){
        return ((value - start) * 100) / (end - start);
    }


    public static String getStringTime(long millis){
        return getStringTime(millis,false);
    }

    public static String getStringTime(long millis,boolean isSec){
        millis = isSec?millis*1000:millis;
        if (millis>=3600000) {
            return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        }else {
            return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        }
    }




    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }


    /**
     *
     * @param old_width original width of image,video,gif,view
     * @param old_height original height of image,video,gif,view
     * @param max_width max width of image,video,gif,view
     * @param max_height max height of image,video,gif,view
     * @return int array where index 0 is width and index 1 is height
     */
    public static int[] resizeWithAspectRatio(int old_width,int old_height,int max_width,int max_height){
        int new_width;
        int new_height;
        if(old_height>old_width){
            new_height = max_height;
            new_width = (new_height * old_width)/old_height;
        }else{
            new_width = max_width;
            new_height = (new_width*old_height)/old_width;
            if ((old_width==old_height) && (max_height<max_width)){
                new_height = max_height;
                new_width = (new_height * old_width)/old_height;
            }
        }
        return new int[]{new_width,new_height};
    }


    public static int gcd(int x, int y) {
        return (y == 0) ? x : gcd(y, x % y);
    }


    /**
     *
     * @param context
     * @param file  should be with extension and whole path like  relativepath/radio.json
     * @return
     */
    public static String loadJSONFromAssets(Context context,String file) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Uri getSongAlbumArt(String albumId){
        if (TextUtils.isEmpty(albumId))
            return null;
        else return ContentUris.withAppendedId(sArtworkUri, Long.parseLong(albumId));
    }






    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        Collections.reverse(list);
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }


    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }







    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity,@DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(drawableRes);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }


    public static String getNumbersWithSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1));
    }


    public static String formatDate(Date date, Context context) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM, getAppLocale(context)).format(date);
    }


    public static String formatTime(Date date, Context context) {
        return DateFormat.getDateInstance(DateFormat.SHORT, getAppLocale(context)).format(date);
    }


    public static String localizeDate(Context context, Date date) {
        return  formatDate(date, context);
    }


    public static String localizeTime(Context context, Date date) {
        return  formatTime(date, context);
    }

    public static Locale getAppLocale(Context context) {
        String lang = "en";
        Locale loc;
        if (lang.equals("system")) {
            loc = Locale.getDefault();
        } else if (lang.matches(".*-.*")) {
            //to differentiate different versions of the language
            //for example, pt (portuguese in Portugal) and pt-br (portuguese in Brazil)
            String[] localisation = lang.split("-");
            lang = localisation[0];
            String country = localisation[1];
            loc = new Locale(lang, country);
        } else {
            loc = new Locale(lang);
        }
        return loc;
    }


    public static boolean getPercentageInRange(int inRange, int num1, int num2) {
        return Math.min(num1,num2) <= inRange && Math.max(num1,num2) >= inRange;
    }


    /**
     * This method is used to check if the variableValue lies in desired range or not.
     * @param variableValue The value in question.
     * @param valueToCheck The desired exact value.
     * @param threshold The threshold for both side of the value.
     * @return true if lies in range ,false otherwise.
     */
    public static boolean rangeCheck(int variableValue, int valueToCheck, int threshold) {
        return variableValue > valueToCheck - threshold && variableValue < valueToCheck + threshold;
    }


    public static Uri getVcard(String lookUpKey){
        if (TextUtils.isEmpty(lookUpKey))
            return null;
       return Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookUpKey);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Uri getVcard(List<String> lookUpKeys){
        String lookupKey="";
        for (int i = 0; i < lookUpKeys.size(); i++) {
            String lookUp =  lookUpKeys.get(i);
            if (lookUpKeys.size() - 1 != i) {
                lookupKey = lookupKey + lookUp + ":";
            } else {
                lookupKey = lookupKey + lookUp;
            }
        }
        if (TextUtils.isEmpty(lookupKey)) {
            return null;
        } else {
            return Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_MULTI_VCARD_URI, lookupKey);
        }
    }


    private static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }








    private static long[] getTodayRange() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new long[]{cal.getTimeInMillis(), timeNow};
    }

    public static long getYesterdayTimestamp() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeNow - A_DAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private static long[] getYesterday() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeNow - A_DAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        long end = start + A_DAY > timeNow ? timeNow : start + A_DAY;
        return new long[]{start, end};
    }

    private static long[] getThisWeek() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        long end = start + A_DAY > timeNow ? timeNow : start + A_DAY;
        return new long[]{start, end};
    }

    private static long[] getThisMonth() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new long[]{cal.getTimeInMillis(), timeNow};
    }

    public static long[] getDateWiseData(int date){
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, date+1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 1);

        return new long[]{cal.getTimeInMillis(),cal1.getTimeInMillis()};
//        return new long[]{cal.getTimeInMillis(),timeNow};
    }


    private static long[] getThisYear() {
        long timeNow = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new long[]{cal.getTimeInMillis(), timeNow};
    }


    public static double calculateBMI (double weight, double height) {
        return (double) (((weight / 2.2046) / (height * 0.0254)) / (height * 0.0254));
    }


    public static double calculateIdealWeight(double height){
        double x = (2.2*(22))+((3.5*22)*((height/100)-1.5));
        return  Math.round(x*10.0)/10.0;
    }





}
