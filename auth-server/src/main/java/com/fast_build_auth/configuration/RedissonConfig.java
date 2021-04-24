package com.fast_build_auth.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.cluster.nodes}")
    String clusterNodes;

    @Bean
    /**
     * 单机配置
     */
    public RedissonClient redisson() {
        //1. 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 2. 根据config创建出RedissonClient实例
        return Redisson.create(config);
    }


    /**
     * 集群配置
     */
//    @Bean
//    public RedissonClient bloomFilter() {
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers();
//        String[] cNodes = clusterNodes.split(",");
//        //分割出集群节点
//        for (String node : cNodes) {
//            clusterServersConfig.addNodeAddress("redis://" + node);
//
//        }
//        return Redisson.create(config);
//    }

}