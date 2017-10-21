package com.djrapitops.plugin.api.utility;

import com.djrapitops.plugin.utilities.Verify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Can be used for reducing FieldNotFoundErrors related to Enums and versions.
 *
 * @author Rsl1122
 */
public class EnumUtility {

    /**
     * Method for version compatibility - enums can have different values with
     * different versions.
     *
     * @param <T>   Enum class with values() & name() method.
     * @param clazz Class of the enum
     * @param names Names of the enum variables to get
     * @return List of Enum variables that were found
     * @throws NullPointerException If class or names is null
     */
    public static <T> List<T> getSupportedEnumValues(Class<T> clazz, String... names) throws NullPointerException {
        Verify.nullCheck(clazz, names);
        try {
            List<String> wantedNames = getWantedNames(names);

            Method method = clazz.getMethod("values");
            Verify.nullCheck(method);
            T[] values = (T[]) method.invoke(clazz);

            return getSupportedValues(values, wantedNames);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            return new ArrayList<>();
        }
    }

    private static List<String> getWantedNames(String[] names) {
        return Arrays.stream(names)
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    private static <T> List<T> getSupportedValues(T[] values, List<String> materialNames) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .filter(obj -> {
                    try {
                        String name = (String) obj.getClass().getMethod("name").invoke(obj);
                        return Verify.contains(name, materialNames);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
