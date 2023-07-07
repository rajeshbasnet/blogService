package com.treeleaf.blog.config;


import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;


public class LoadBalancerConfig {
    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new RatingServiceInstanceListSupplier("abc@xyz");
    }
}


class RatingServiceInstanceListSupplier implements ServiceInstanceListSupplier {
    private final String serviceId;

    public RatingServiceInstanceListSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays.asList(
                new DefaultServiceInstance("ratingService-1", serviceId, "localhost", 8083, false),
                new DefaultServiceInstance("ratingService-2", serviceId, "localhost", 8084, false)
        ));
    }
}