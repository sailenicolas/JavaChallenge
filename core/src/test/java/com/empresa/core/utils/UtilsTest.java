package com.empresa.core.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void getId() {
        String id = DataUtils.getId("a", "a", "b");
        assertThat(id).isEqualTo("a:a:b");
        String id1 = DataUtils.getId("a", "a", "b");
        assertThat(id1).isEqualTo("a:a:b");
    }
}