package com.empresa.api.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    private int mapPosTtl;
    @Value("${hazel.map.posCost.findAll.ttl:3600}")
    private int mapFindAllTtl;
    @Value("${hazel.map.posCost.minCost.ttl:300}")
    private int mapMinCostTtl;
    @Value("${hazel.map.pos.byId.ttl:300}")
    private int mapByIdTtl;
    @Value("${hazel.map.pos.pointB.ttl:300}")
    private int mapPointBTtl;
    @Value("${hazel.map.posAll.ttl:3600}")
    private int mapPosAllTtl;
    @Value("${hazel.map.data.ttl:3600}")
    private int mapDataTtl;
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
        mapConfig1.setTimeToLiveSeconds(mapPosTtl);
        MapConfig mapConfig2 = new MapConfig("posCost-findAll");
        mapConfig2.setTimeToLiveSeconds(mapFindAllTtl);
        MapConfig mapConfig3 = new MapConfig("posCost-MinCost");
        mapConfig3.setTimeToLiveSeconds(mapMinCostTtl);
        MapConfig mapConfig4 = new MapConfig("posCost-ById");
        mapConfig4.setTimeToLiveSeconds(mapByIdTtl);
        MapConfig mapConfig5 = new MapConfig("posCost-pointB");
        mapConfig5.setTimeToLiveSeconds(mapPointBTtl);
        MapConfig mapConfig6 = new MapConfig("posAll");
        mapConfig6.setTimeToLiveSeconds(mapPosAllTtl);
        MapConfig mapConfig7 = new MapConfig("data");
        mapConfig7.setTimeToLiveSeconds(mapDataTtl);
        config.addMapConfig(mapConfig1);
        config.addMapConfig(mapConfig2);
        config.addMapConfig(mapConfig3);
        config.addMapConfig(mapConfig4);
        config.addMapConfig(mapConfig5);
        config.addMapConfig(mapConfig6);
        config.addMapConfig(mapConfig7);
        return Hazelcast.newHazelcastInstance(config);
    }
}
