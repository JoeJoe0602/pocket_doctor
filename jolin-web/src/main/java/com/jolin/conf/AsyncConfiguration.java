package com.jolin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean("baseExecutor")
    public Executor baseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //Core thread count: the number of threads initialized when the thread pool is created
        executor.setCorePoolSize(10);
        //Maximum number of threads: The maximum number of threads in the thread pool. Threads exceeding the core number are requested only after the buffer queue is full
        executor.setMaxPoolSize(20);
        //Buffer queue: The queue used to buffer the execution of a task
        executor.setQueueCapacity(500);
        //Allow thread idle time of 60 seconds: threads outside the core thread will be destroyed when the idle time is reached
        executor.setKeepAliveSeconds(60);
        //The prefix of the thread pool name: When this is set, it is easy to locate the thread pool where the processing task is located
        executor.setThreadNamePrefix("base-");
        // Rejection strategy when the buffer queue is full: handled by the calling thread (usually the main thread)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

}