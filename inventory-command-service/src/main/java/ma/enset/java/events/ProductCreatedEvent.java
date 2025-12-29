package ma.enset.java.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String categoryId;
}