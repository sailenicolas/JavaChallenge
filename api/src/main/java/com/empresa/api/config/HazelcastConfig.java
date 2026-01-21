package com.empresa.api.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class HazelcastConfig {
    @Value("${hazel.cluster.name:ms-api-haz1}")
    private String hazelcastClusterName;
    @Value("${hazel.k8s.service.enabled:false}")
    private boolean hazelcastK8sEnabled;

    @Value("${hazel.tcpip.service.enabled:true}")
    private boolean hazelcastTcpipEnabled;

    @Value("${hazel.k8s.service.name:ms-api}")
    private String hazelcastServiceName;
    @Value("${hazel.k8s.namespace:default}")
    private String hazelcastNamespace;
    @Value("${hazel.k8s.port:5701}")
    private String hazelcastPort;
    @Value("${hazel.metrics:true}")
    private String hazelcastMetrics;
    @Value("${hazel.map.pos.ttl:86400}")
    private int mapMqTtl;
    @Value("${hazelcast.logging.type:none}")
    private String logging;

    @Bean
    HazelcastInstance getInstance(){
        Config config = new Config();
        config.setClusterName(hazelcastClusterName);
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPortAutoIncrement(true);
        networkConfig.getJoin().getKubernetesConfig().setEnabled(hazelcastK8sEnabled)
                .setProperty("service-port", hazelcastPort)
                .setProperty("service-name", hazelcastServiceName)
                .setProperty("namespace", hazelcastNamespace);
        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(hazelcastTcpipEnabled);

        config.setProperty("hazelcast.metrics.enabled", hazelcastMetrics);
        config.getMetricsConfig().setEnabled(Boolean.parseBoolean(hazelcastMetrics));
        config.setProperty("hazelcast.logging.type", logging);
        MapConfig mapConfig1 = new MapConfig("pos");
        mapConfig1.setTimeToLiveSeconds(mapMqTtl);
        config.addMapConfig(mapConfig1);
        return Hazelcast.newHazelcastInstance(config);
    }
}
