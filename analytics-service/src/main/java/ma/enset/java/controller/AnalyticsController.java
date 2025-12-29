package ma.enset.java.controller;


import ma.enset.java.analytics.entity.OrderAnalytics;
import ma.enset.java.analytics.repository.OrderAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final OrderAnalyticsRepository analyticsRepository;

    @GetMapping("/orders")
    public List<OrderAnalytics> getOrderAnalytics() {
        return analyticsRepository.findAllByOrderByWindowStartDesc();
    }

    @GetMapping("/orders/latest")
    public List<OrderAnalytics> getLatestAnalytics() {
        return analyticsRepository.findAllByOrderByWindowStartDesc()
                .stream()
                .limit(20)
                .toList();
    }
}