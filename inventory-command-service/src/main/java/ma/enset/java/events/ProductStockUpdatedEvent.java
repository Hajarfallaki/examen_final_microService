package ma.enset.java.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStockUpdatedEvent {
    private String productId;
    private int quantity;
}