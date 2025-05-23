package com.packsure.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.packsure.service.OrderFetchService;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Autowired
    private OrderFetchService fetchService;

    @Scheduled(initialDelay = 1000,fixedDelay = 3600000) // every 1 minute
    public void fetchOrders() {
        fetchService.fetchAndSaveOrders(0);
    }
}
 