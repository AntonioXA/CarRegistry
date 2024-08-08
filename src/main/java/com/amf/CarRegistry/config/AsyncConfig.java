package com.amf.CarRegistry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Numero minimo de hilos concurrentes
        executor.setMaxPoolSize(10); // Numero maximo de hilos concurrentes
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("demoThread-");
        executor.initialize();
        return executor;
    }
}
