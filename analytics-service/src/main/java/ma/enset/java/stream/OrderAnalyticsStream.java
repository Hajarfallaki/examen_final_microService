package ma.enset.java.stream;


import ma.enset.java.analytics.dto.OrderEventDTO;
import ma.enset.java.analytics.entity.OrderAnalytics;
import ma.enset.java.analytics.repository.OrderAnalyticsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderAnalyticsStream {

    private final OrderAnalyticsRepository analyticsRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public KStream<String, String> orderAnalyticsStream(StreamsBuilder builder) {

        KStream<String, String> stream = builder.stream("order-events",
                Consumed.with(Serdes.String(), Serdes.String()));

        // Window analytics: count and total per 5 seconds window
        stream
                .filter((key, value) -> value.contains("OrderCreatedEvent"))
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
                .count(Materialized.as("order-count-store"))
                .toStream()
                .foreach((windowedKey, count) -> {
                    log.info("Window [{}] - Order count: {}",
                            windowedKey.window(), count);

                    // Save analytics to database
                    OrderAnalytics analytics = new OrderAnalytics();
                    analytics.setWindowStart(new Date(windowedKey.window().start()));
                    analytics.setWindowEnd(new Date(windowedKey.window().end()));
                    analytics.setOrderCount(count.intValue());
                    analytics.setCreatedAt(new Date());
                    analyticsRepository.save(analytics);
                });

        // Calculate total amount per window
        stream
                .filter((key, value) -> value.contains("OrderCreatedEvent"))
                .mapValues(value -> {
                    try {
                        OrderEventDTO event = objectMapper.readValue(value, OrderEventDTO.class);
                        double total = event.getOrderLines().stream()
                                .mapToDouble(line -> line.getQuantity() * line.getUnitPrice() * (1 - line.getDiscount()))
                                .sum();
                        return total;
                    } catch (Exception e) {
                        log.error("Error parsing order event", e);
                        return 0.0;
                    }
                })
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
                .reduce(Double::sum, Materialized.as("order-total-store"))
                .toStream()
                .foreach((windowedKey, total) -> {
                    log.info("Window [{}] - Total amount: {}",
                            windowedKey.window(), total);

                    // Update analytics in database
                    analyticsRepository.findByWindowStartAndWindowEnd(
                            new Date(windowedKey.window().start()),
                            new Date(windowedKey.window().end())
                    ).ifPresent(analytics -> {
                        analytics.setTotalAmount(total);
                        analyticsRepository.save(analytics);
                    });
                });

        return stream;
    }
}
