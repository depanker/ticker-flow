package com.depanker.ticker.config;

import com.depanker.ticker.beans.TickerCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Slf4j
public class AppConfig {
    @Bean(name = "tickerQueue")
    public BlockingQueue<TickerCase> tickerQueue() {
        return new ArrayBlockingQueue<>(10000);
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor =  new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setRejectedExecutionHandler((r, executor) -> {
            log.warn("Tasks are overflowing the queue");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            executor.submit(r);
        });
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("tickerWriter-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


}
