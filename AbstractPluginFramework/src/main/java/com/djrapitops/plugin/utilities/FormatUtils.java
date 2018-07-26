package com.djrapitops.plugin.utilities;

import org.bukkit.Location;

import java.text.DecimalFormat;
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

    /**
     * Formats a Minecraft Location into readable format.
     *
     * @param loc Location to format
     * @return Readable location format.
     */
    public static String formatLocation(Location loc) {
        return "x " + loc.getBlockX() + " z " + loc.getBlockZ() + " in " + loc.getWorld();
    }

    /**
     * @param d
     * @return
     */
    public static String cutDecimals(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
//        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(d);
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
