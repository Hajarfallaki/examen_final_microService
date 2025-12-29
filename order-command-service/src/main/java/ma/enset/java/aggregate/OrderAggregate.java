package ma.enset.java.aggregate;

import ma.enset.java.order.command.commands.*;
import ma.enset.java.order.command.events.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.List;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String customerId;
    private Date orderDate;
    private Date deliveryDate;
    private String deliveryAddress;
    private OrderStatus status;
    private List<OrderLineDTO> orderLines;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getOrderId(),
                command.getCustomerId(),
                new Date(),
                command.getDeliveryAddress(),
                command.getOrderLines()
        ));
    }

    @CommandHandler
    public void handle(ActivateOrderCommand command) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Order must be in CREATED state");
        }
        AggregateLifecycle.apply(new OrderActivatedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel delivered order");
        }
        AggregateLifecycle.apply(new OrderCancelledEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (this.status != OrderStatus.ACTIVATED) {
            throw new IllegalStateException("Order must be activated");
        }
        AggregateLifecycle.apply(new OrderShippedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(DeliverOrderCommand command) {
        if (this.status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Order must be shipped");
        }
        AggregateLifecycle.apply(new OrderDeliveredEvent(
                command.getOrderId(),
                new Date()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.orderDate = event.getOrderDate();
        this.deliveryAddress = event.getDeliveryAddress();
        this.orderLines = event.getOrderLines();
        this.status = OrderStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(OrderActivatedEvent event) {
        this.status = OrderStatus.ACTIVATED;
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = OrderStatus.CANCELED;
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
        this.status = OrderStatus.SHIPPED;
    }

    @EventSourcingHandler
    public void on(OrderDeliveredEvent event) {
        this.status = OrderStatus.DELIVERED;
        this.deliveryDate = event.getDeliveryDate();
    }
}
