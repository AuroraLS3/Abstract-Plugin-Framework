/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.utilities;

import java.lang.reflect.Array;

/**
 * Utility for a few array operations.
 *
 * @author AuroraLS3
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