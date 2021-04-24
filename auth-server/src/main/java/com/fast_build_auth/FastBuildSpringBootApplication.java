package com.fast_build_auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // 开启缓存
@MapperScan("com.fast_build_auth.dao") // 秒扫dao层
public class FastBuildSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastBuildSpringBootApplication.class, args);
    }

}
