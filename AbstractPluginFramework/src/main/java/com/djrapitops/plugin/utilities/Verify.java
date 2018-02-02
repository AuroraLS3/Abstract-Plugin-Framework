/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.command.ISender;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Rsl1122
 */
public class Verify {

    /**
     * Checks if the file exists if it is not null.
     *
     * @param file File to check
     * @return false if null or file.exists()
     */
    public static boolean exists(File file) {
        return notNull(file) && file.exists();
    }

    public static File existCheck(File file) throws IllegalArgumentException {
        return existCheck(file, () -> new IllegalArgumentException("File did not exist"));
    }

    public static <K extends Throwable> File existCheck(File file, ErrorLoader<K> exception) throws K {
        nullCheck(file, exception);
        if (exists(file)) {
            return file;
        }
        throw exception.load();
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
     * @param <T>
     * @param lookFor
     * @param map
     * @return
     */
    public static <T> boolean contains(T lookFor, Map<T, Object> map) {
        return contains(lookFor, map.keySet().stream());
    }

    /**
     * @param one
     * @param two
     * @return
     */
    public static boolean equalsIgnoreCase(String one, String two) {
        return notNull(one) && one.equalsIgnoreCase(two);
    }

    public static boolean equalsOne(String toCheck, String... isEqual) {
        if (isEqual == null) {
            return false;
        }
        if (toCheck == null && containsNull(new Object[]{isEqual})) {
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
     * @param <T>
     * @param <K>
     * @param object
     * @param object2
     * @return
     */
    public static <T, K> boolean equals(T object, K object2) {
        return notNull(object) && object.equals(object2);
    }

    public static boolean notNull(Object... object) {
        if (object == null) {
            return false;
        }
        return !containsNull(object);
    }

    public static boolean containsNull(Object... object) {
        for (Object t : object) {
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
    public static <T> T nullCheck(T object) throws IllegalArgumentException {
        return nullCheck(object, () -> new IllegalArgumentException(object.getClass().getSimpleName() + " was null!"));
    }

    public static <T, K extends Throwable> T nullCheck(T object, ErrorLoader<K> exception) throws K {
        if (!notNull(object)) {
            throw exception.load();
        }
        return object;
    }

    @SafeVarargs
    public static <T> T[] nullCheck(T... objects) throws IllegalArgumentException {
        return nullCheck(
                () -> new IllegalArgumentException(objects.getClass().getSimpleName() + " contained a null object!"),
                objects
        );
    }

    public static <T, K extends Throwable> T[] nullCheck(ErrorLoader<K> exception, T... objects) throws K {
        for (T obj : objects) {
            if (!notNull(obj)) {
                throw exception.load();
            }
        }
        return objects;
    }

    public static <K extends Throwable> void checkCondition(boolean condition, ErrorLoader<K> exception) throws K {
        if (!condition) {
            throw exception.load();
        }
    }

    public interface ErrorLoader<K extends Throwable> {
        K load();
    }

    /**
     * @param permission
     * @param sender
     * @return
     */
    public static boolean hasPermission(String permission, ISender sender) {
        return sender.hasPermission(permission);
    }

}
