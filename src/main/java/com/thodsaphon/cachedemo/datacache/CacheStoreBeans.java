package com.thodsaphon.cachedemo.datacache;

import com.thodsaphon.cachedemo.model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Employee> employeeCache() {
        return new CacheStore<Employee>(120, TimeUnit.SECONDS);
    }

}