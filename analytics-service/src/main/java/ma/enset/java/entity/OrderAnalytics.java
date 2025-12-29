package ma.enset.java.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date windowStart;

    @Temporal(TemporalType.TIMESTAMP)
    private Date windowEnd;

    private int orderCount;
    private double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}