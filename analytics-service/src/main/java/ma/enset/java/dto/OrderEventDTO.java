package ma.enset.java.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderEventDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private String deliveryAddress;
    private List<OrderLineDTO> orderLines;
}
