package com.empresa.core.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataUtils {
    public static String getId(String init, String id, String idB) {
        return "%s:%s".formatted(init, Stream.of(id, idB)
                .sorted()
                .collect(Collectors.joining(":")));
    }

}
