package ma.enset.java.repository;


import ma.enset.java.analytics.entity.OrderAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderAnalyticsRepository extends JpaRepository<OrderAnalytics, Long> {
    Optional<OrderAnalytics> findByWindowStartAndWindowEnd(Date windowStart, Date windowEnd);
    List<OrderAnalytics> findAllByOrderByWindowStartDesc();
}