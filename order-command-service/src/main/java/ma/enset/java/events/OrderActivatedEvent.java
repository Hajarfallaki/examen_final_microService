package ma.enset.java.events;


import com.example.order.command.commands.OrderLineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private String deliveryAddress;
    private List<OrderLineDTO> orderLines;
}