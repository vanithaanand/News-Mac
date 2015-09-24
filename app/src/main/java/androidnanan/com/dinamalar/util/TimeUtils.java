package androidnanan.com.dinamalar.util;

import android.content.Context;

/**
 * Created by anand on 9/5/15.
 */
public class TimeUtils {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static String getTimeAgo(long time, Context ctx) {
        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = UIUtils.getCurrentTime(ctx);
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "just now";
        } else if (diff < 2 * MINUTE) {
            return "1m";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + "m";
        } else if (diff < 90 * MINUTE) {
            return "1h";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + "h";
        } else if (diff < 48 * HOUR) {
            return "yesterday";
        } else {
            return diff / DAY + "d";
        }
    }

}
