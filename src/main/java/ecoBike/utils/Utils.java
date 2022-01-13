package ecoBike.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;


public class Utils {
    public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Logger LOGGER = getLogger(Utils.class.getName());
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
    }

    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }

    public static String getCurrencyFormat(int num) {
        Locale vietname = new Locale("vi", "VN");
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietname);
        return defaultFormat.format(num);
    }

    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Utils.getLogger(Utils.class.getName());
            digest = "";
        }
        return digest;
    }

    public static String convertTime(long time) {
        int hours = (int) time / 3600;
        int minutes = (int) (time - hours * 3600) / 60;
        if (minutes == 0) return hours + " hours";
        if (hours == 0) return minutes + " minutes";
        return hours + " hours " + minutes + " minutes";
    }

    public static String convertTimeSecond(long time) {
        int hour = (int) (time / 3600);
        int minute = (int) ((time - hour * 3600) / 60);
        int second = (int) (time - hour * 3600 - minute * 60);
        if (second < 59) second += 1;
        else {
            second = 0;
            if (minute < 59) minute += 1;
            else {
                hour += 1;
                minute = 0;
            }
        }
        return hour + " : " + minute + " : " + second;

    }
}
