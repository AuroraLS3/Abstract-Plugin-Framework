package com.djrapitops.plugin.utilities;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import org.bukkit.Location;

/**
 *
 * @author Rsl1122
 */
public class FormattingUtils {

    /**
     *
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
        return dataPoint.replaceAll("[^\\d.]", "");
    }

    /**
     *
     * @param dataPoint
     * @return
     */
    public static String removeNumbers(String dataPoint) {
        for (char c : removeLetters(dataPoint).toCharArray()) {
            dataPoint = dataPoint.replace(c + "", "");
        }
        dataPoint = dataPoint.replace(" ", "");
        return dataPoint;
    }

    /**
     * Turns the version string into a integer
     *
     * @param versionString String - number format 1.1.1
     * @return parsed double - for example 1,11
     * @throws NumberFormatException When wrong format
     */
    public static int parseVersionNumber(String versionString) throws NumberFormatException {
        String[] versionArray = versionString.split("\\.");
        if (versionArray.length != 3) {
            throw new NumberFormatException("Wrong format used");
        }
        int main = Integer.parseInt(versionArray[0]) * 10000;
        int major = Integer.parseInt(versionArray[1]) * 100;
        int minor = Integer.parseInt(versionArray[2]);
        int versionNumber = main + major + minor;
        return versionNumber;
    }

    public static String[] removeFirstArgument(String... args) {
        String[] arguments = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arguments[i - 1] = args[i];
        }
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
     *
     * @param d
     * @return
     */
    public static String cutDecimals(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
//        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(d);
    }

    public static String collectionToStringNoBrackets(Collection<? extends Object> coll) {
        return coll.toString().replace("[", "").replace("]", "").replace("{", "").replace("}", "");
    }
}
