package com.djrapitops.plugin.utilities;

import java.lang.reflect.Array;

/**
 * Utility for a few array operations.
 *
 * @author Rsl1122
 */
public class ArrayUtil {

    private ArrayUtil() {
        // Static method class
    }

    /**
     * Merge multiple arrays into a single array.
     *
     * @param arrays Arrays to merge
     * @param <T>    Type of the objects in the arrays.
     * @return a single array with all of the arrays one after another.
     */
    public static <T> T[] merge(T[]... arrays) {
        int length = 0;
        for (T[] arr : arrays) {
            length += arr.length;
        }
        T[] all = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), length);
        int copied = 0;
        for (T[] arr : arrays) {
            System.arraycopy(arr, 0, all, copied, arr.length);
            copied += arr.length;
        }
        return all;
    }

    /**
     * Remove first element of the varargs array.
     *
     * @param args Array to remove arguments from.
     * @param <T>  Type of the objects in the array.
     * @return new array with first element removed.
     */
    public static <T> T[] removeFirstArgument(T... args) {
        int length = args.length > 0 ? args.length - 1 : 0;
        T[] arguments = (T[]) Array.newInstance(args.getClass().getComponentType(), length);
        if (length > 0) {
            System.arraycopy(args, 1, arguments, 0, args.length - 1);
        }
        return arguments;
    }

}