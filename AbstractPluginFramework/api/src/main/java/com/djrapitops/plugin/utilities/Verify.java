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

import com.djrapitops.plugin.command.Sender;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utility class for throwing exceptions if some conditions are not fulfilled.
 *
 * @author AuroraLS3
 */
public class Verify {

    private Verify() {
        /* Static method class */
    }

    /**
     * Checks if the file exists if it is not null.
     *
     * @param file File to check
     * @return false if null or file.exists()
     */
    @Deprecated
    public static boolean exists(File file) {
        return notNull(file) && file.exists();
    }

    @Deprecated
    public static File existCheck(File file) {
        return existCheck(file, () -> new IllegalArgumentException("File did not exist"));
    }

    @Deprecated
    public static <K extends Throwable> File existCheck(File file, Supplier<K> exception) throws K {
        nullCheck(file, exception);
        if (exists(file)) {
            return file;
        }
        throw exception.get();
    }

    /**
     * Checks if the given parameter is empty if it is not null.
     *
     * @param string String.
     * @return true if null, string.isEmpty() if not
     */
    public static boolean isEmpty(String string) {
        return !notNull(string) || string.isEmpty();
    }

    /**
     * Checks if the given parameter is empty if it is not null.
     *
     * @param <T>  Object type variable
     * @param coll Collection
     * @return true if null, coll.isEmpty() if not
     */
    public static <T> boolean isEmpty(Collection<T> coll) {
        return !notNull(coll) || coll.isEmpty();
    }

    /**
     * Checks if the given parameter is empty if it is not null.
     *
     * @param <T> Object type variable
     * @param <K> Object type variable, not checked
     * @param map Any map object
     * @return true if null, map.isEmpty() if not
     */
    public static <T, K> boolean isEmpty(Map<T, K> map) {
        return !notNull(map) || map.isEmpty();
    }

    /**
     * Checks if the collection contains the object if collection is not null.
     *
     * @param <T>     Object type variable
     * @param lookFor object to look for
     * @param coll    Collection to check the object for
     * @return false if collection is null or Stream#anyMatch(Verify.equals)
     */
    public static <T> boolean contains(T lookFor, Collection<T> coll) {
        return !isEmpty(coll) && contains(lookFor, coll.stream());
    }

    /**
     * Checks if the stream contains the object.
     *
     * @param <T>     Object type variable
     * @param lookFor object to look for
     * @param stream  stream of objects
     * @return Does the array contain the object.
     */
    public static <T> boolean contains(T lookFor, Stream<T> stream) {
        return stream.anyMatch(obj -> equals(obj, lookFor));
    }

    /**
     * Checks if the array contains the object.
     *
     * @param <T>     Object type variable
     * @param lookFor object to look for
     * @param objects objects
     * @return Does the array contain the object.
     */
    @SafeVarargs
    public static <T> boolean contains(T lookFor, T... objects) {
        return contains(lookFor, Arrays.stream(objects));
    }

    /**
     * Check if a Map contains a specific key.
     *
     * @param <T>     Type of the key.
     * @param lookFor Key to look for.
     * @param map     Map to search from.
     * @return Does the map have the key.
     */
    public static <T> boolean contains(T lookFor, Map<T, Object> map) {
        return contains(lookFor, map.keySet().stream());
    }

    /**
     * Check if a String equals another when case is ignored, and that they are not null.
     *
     * @param one First string.
     * @param two Second string.
     * @return If the Strings match and are not null.
     */
    public static boolean equalsIgnoreCase(String one, String two) {
        return notNull(one) && one.equalsIgnoreCase(two);
    }

    /**
     * Check if a String equals one option.
     *
     * @param toCheck String to check against
     * @param isEqual Options that are considered good.
     * @return If one of the given strings matches the first one.
     */
    public static boolean equalsOne(String toCheck, String... isEqual) {
        if (isEqual == null) {
            return false;
        }
        if (toCheck == null && containsNull((Object[]) isEqual)) {
            return true;
        }
        for (String s : isEqual) {
            if (s.equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a String contains one option.
     *
     * @param toCheck   String to check against
     * @param contained Options that are considered good.
     * @return If one of the given strings is found in the first one.
     */
    public static boolean containsOne(String toCheck, String... contained) {
        if (contained == null) {
            return false;
        }
        if (toCheck == null && containsNull((Object[]) contained)) {
            return true;
        }
        if (toCheck == null) {
            return false;
        }
        for (String check : contained) {
            if (toCheck.contains(check)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Check if two objects equal and are not null.
     *
     * @param <T>     Type of the first object.
     * @param <K>     Type of the second object.
     * @param object  First object.
     * @param object2 Second object.
     * @return If the objects are equal and are not null.
     */
    public static <T, K> boolean equals(T object, K object2) {
        return notNull(object) && object.equals(object2);
    }

    /**
     * Check if an array is null or contains null objects.
     *
     * @param objects Objects to check
     * @return true if a null is found.
     */
    public static boolean notNull(Object... objects) {
        if (objects == null) {
            return false;
        }
        return !containsNull(objects);
    }

    /**
     * Check if an array contains null objects.
     *
     * @param objects Objects to check.
     * @return true if a null is found.
     */
    public static boolean containsNull(Object... objects) {
        for (Object t : objects) {
            if (t == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given object is null, throws exception if null.
     *
     * @param <T>    Object type variable
     * @param object Object to check
     * @return the original object
     * @throws NullPointerException If the object is null.
     */
    public static <T> T nullCheck(T object) {
        return nullCheck(object, () -> new IllegalArgumentException("Something was null!"));
    }

    /**
     * Checks if the given object is null, throws exception if null.
     *
     * @param object    Object to check
     * @param exception Supplier for exception to throw if the object is null.
     * @param <T>       Type of the object
     * @param <K>       Type of the throwable
     * @return The object if it is not null.
     * @throws K Supplied exception if the object is null.
     */
    public static <T, K extends Throwable> T nullCheck(T object, Supplier<K> exception) throws K {
        if (!notNull(object)) {
            throw exception.get();
        }
        return object;
    }

    /**
     * Checks if the given array contains null, throws exception if a null is found.
     *
     * @param objects Objects to check.
     * @param <T>     Type of the objects in the array
     * @return the given objects if they are not null.
     * @throws IllegalArgumentException If a null is found.
     */
    @SafeVarargs
    public static <T> T[] nullCheck(T... objects) {
        return nullCheck(
                () -> new IllegalArgumentException("Contained a null object!"),
                objects
        );
    }

    /**
     * Checks if the given array contains null, throws exception if a null is found.
     *
     * @param exception Supplier for exception to throw if an object is null.
     * @param objects   Objects to check
     * @param <T>       Type of the objects
     * @param <K>       Type of the exception
     * @return The given objects if a null is not found
     * @throws K Supplied exception thrown if a null object is found.
     */
    public static <T, K extends Throwable> T[] nullCheck(Supplier<K> exception, T... objects) throws K {
        for (T obj : objects) {
            if (!notNull(obj)) {
                throw exception.get();
            }
        }
        return objects;
    }

    /**
     * Check if a condition is true, otherwise throw exception.
     *
     * @param condition Condition to check.
     * @param exception Supplier for exception to throw if condition is false.
     * @param <K>       Type of the exception
     * @throws K Supplied exception if the condition is false.
     */
    public static <K extends Throwable> void checkCondition(boolean condition, Supplier<K> exception) throws K {
        if (!condition) {
            throw exception.get();
        }
    }

    @Deprecated
    public static boolean hasPermission(String permission, Sender sender) {
        return sender.hasPermission(permission);
    }

    /**
     * Check if a condition is true, otherwise throw exception.
     *
     * @param value     Condition to check.
     * @param exception Supplier for exception to throw if condition is false.
     * @param <K>       Type of the exception
     * @throws K Supplied exception if the condition is false.
     */
    public static <K extends Throwable> void isTrue(boolean value, Supplier<K> exception) throws K {
        checkCondition(value, exception);
    }

    /**
     * Check if a condition is false, otherwise throw exception.
     *
     * @param value     Condition to check.
     * @param exception Supplier for exception to throw if condition is true.
     * @param <K>       Type of the exception
     * @throws K Supplied exception if the condition is true.
     */
    public static <K extends Throwable> void isFalse(boolean value, Supplier<K> exception) throws K {
        checkCondition(!value, exception);
    }
}
