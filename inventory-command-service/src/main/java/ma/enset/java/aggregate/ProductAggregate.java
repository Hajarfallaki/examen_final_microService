package ma.enset.java.aggregate;

import ma.enset.java.inventory.command.commands.*;
import ma.enset.java.inventory.command.events.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String categoryId;
    private ProductStatus status;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        AggregateLifecycle.apply(new ProductCreatedEvent(
                command.getProductId(),
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                command.getCategoryId()
        ));
    }

    @CommandHandler
    public void handle(UpdateProductStockCommand command) {
        if (this.quantity + command.getQuantity() < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        AggregateLifecycle.apply(new ProductStockUpdatedEvent(
                command.getProductId(),
                command.getQuantity()
        ));
    }

    @CommandHandler
    public void handle(UpdateProductStatusCommand command) {
        AggregateLifecycle.apply(new ProductStatusUpdatedEvent(
                command.getProductId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.categoryId = event.getCategoryId();
        this.status = ProductStatus.AVAILABLE;
    }

    @EventSourcingHandler
    public void on(ProductStockUpdatedEvent event) {
        this.quantity += event.getQuantity();
        if (this.quantity <= 0) {
            this.status = ProductStatus.OUT_OF_STOCK;
        } else {
            this.status = ProductStatus.AVAILABLE;
        }
    }

    @EventSourcingHandler
    public void on(ProductStatusUpdatedEvent event) {
        this.status = event.getStatus();
    }
}
