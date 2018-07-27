package com.djrapitops.plugin.utilities;

import java.util.Collection;
import java.util.Date;

/**
 * @author Rsl1122
 */
@Deprecated
public class FormatUtils {

    public static String formatTimeStampSecond(long epochMs) {
        Date date = new Date(epochMs);
        String timeStamp = date.toString();
        String day = timeStamp.substring(4, 10);
        String clock = timeStamp.substring(11, 19);
        return day + ", " + clock;
    }

    public static <T> String collectionToStringNoBrackets(Collection<T> coll) {
        return coll.toString().replace("[", "").replace("]", "").replace("{", "").replace("}", "");
    }

    public static String formatBench(String source, long ms) {
        StringBuilder b = new StringBuilder();
        b.append(ms).append(" ms");
        while (b.length() < 10) {
            b.append(" ");
        }
        b.append(source);
        return b.toString();
    }
}
