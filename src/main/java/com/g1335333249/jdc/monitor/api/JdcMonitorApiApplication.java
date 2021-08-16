package com.g1335333249.jdc.monitor.api;

import com.g1335333249.jdc.monitor.api.security.config.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author guanpeng
 */
@SpringBootApplication
@EnableScheduling
public class JdcMonitorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdcMonitorApiApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
