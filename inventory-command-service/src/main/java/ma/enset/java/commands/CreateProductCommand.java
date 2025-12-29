package ma.enset.java.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String categoryId;
}