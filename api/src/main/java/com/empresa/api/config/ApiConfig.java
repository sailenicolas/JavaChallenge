package com.empresa.api.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("api.config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfig {
    private RestConfig restConfig;
}
