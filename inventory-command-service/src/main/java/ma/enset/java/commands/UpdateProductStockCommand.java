package ma.enset.java.commands;

import ma.enset.java.inventory.command.aggregate.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UpdateProductStatusCommand {
    @TargetAggregateIdentifier
    private String productId;
    private ProductStatus status;
}