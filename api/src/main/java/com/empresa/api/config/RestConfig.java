package com.empresa.pos.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.config.rest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestConfig {
    private GenericUrlConfig pos;
    private GenericUrlConfig data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenericUrlConfig {
        private String url;
        private boolean debug;
    }
}
