package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.api.utility.log.Log;
import org.bukkit.Location;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rsl1122
 */
@Deprecated
public class FormatUtils {

    /**
     * @param epochMs
     * @return
     */
    public static String formatTimeStamp(long epochMs) {
        Date date = new Date(epochMs);
        String timeStamp = date.toString();
        // "EEE MMM dd HH:mm:ss zzz yyyy"
        // "0123456789012345678901234567"
        String day = timeStamp.substring(4, 10);
        String clock = timeStamp.substring(11, 16);
        return day + ", " + clock;
    }

    public static String formatTimeStampSecond(long epochMs) {
        Date date = new Date(epochMs);
        String timeStamp = date.toString();
        String day = timeStamp.substring(4, 10);
        String clock = timeStamp.substring(11, 19);
        return day + ", " + clock;
    }

    public static String formatTimeStampYear(long epochMs) {
        Date date = new Date(epochMs);
        String timeStamp = date.toString();
        String year = timeStamp.substring(24);
        String day = timeStamp.substring(4, 10);
        String clock = timeStamp.substring(11, 16);
        return day + " " + year + ", " + clock;
    }

    /**
     * Removes letters from a string leaving only numbers and dots.
     *
     * @param dataPoint
     * @return
     */
    public static String removeLetters(String dataPoint) {
        return dataPoint.replaceAll("[A-Za-z]", "");
    }

    /**
     * @param dataPoint
     * @return
     */
    public static String removeNumbers(String dataPoint) {
        return dataPoint.replaceAll("[0-9]", "");
    }

    public static String removeSymbols(String dataPoint) {
        return dataPoint.replaceAll("[^a-zA-Z0-9_\\s]", "");
    }

    public static String removeSymbolsButDot(String dataPoint) {
        return dataPoint.replaceAll("[^a-zA-Z0-9_\\s\\.]", "");
    }

    public static String spaceWhitespace(String dataPoint) {
        return dataPoint.replaceAll("[\\s]", " ");
    }

    public static String removeWhitespace(String dataPoint) {
        return dataPoint.replaceAll("[\\s]", "");
    }

    /**
     * Turns the version string into a integer
     *
     * @param versionString String - number format 1.1.1
     * @return parsed double - for example 1,11
     * @throws NumberFormatException When wrong format
     */
    public static long parseVersionNumber(String versionString) throws NumberFormatException {
        String replaced = versionString.replaceAll("[^0-9]", ".");
        String[] split = replaced.split("\\.");
        List<String> version = Arrays.stream(split).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        long versionNumber = 0;
        for (int i = 0; i < version.size(); i++) {
            try {
                int num = Integer.parseInt(version.get(i));
                long multiplier = (long) Math.pow(100, 8.0 - i);
                versionNumber += num * multiplier;
            } catch (NumberFormatException e) {
                Log.toLog(FormatUtils.class, e);
            }
        }
        return versionNumber;
    }

    public static String[] removeFirstArgument(String... args) {
        String[] arguments = new String[args.length - 1];
        System.arraycopy(args, 1, arguments, 0, args.length - 1);
        return arguments;
    }

    /**
     * Merges multiple arrays into one.
     *
     * @param arrays String arrays that need to be combined
     * @return One array with contents of the multiple
     */
    public static String[] mergeArrays(String[]... arrays) {
        int arraySize = 0;
        for (String[] array : arrays) {
            arraySize += array.length;
        }
        String[] result = new String[arraySize];
        int j = 0;
        for (String[] array : arrays) {
            for (String string : array) {
                result[j++] = string;
            }
        }
        return result;
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
