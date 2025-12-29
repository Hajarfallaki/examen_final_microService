package ma.enset.java.commands;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
}