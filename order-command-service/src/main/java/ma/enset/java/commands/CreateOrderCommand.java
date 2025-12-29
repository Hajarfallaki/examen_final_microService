package ma.enset.java.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String customerId;
    private String deliveryAddress;
    private List<OrderLineDTO> orderLines;
}