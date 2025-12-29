package ma.enset.java.events;
import ma.enset.java.inventory.command.aggregate.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStatusUpdatedEvent {
    private String productId;
    private ProductStatus status;
}
