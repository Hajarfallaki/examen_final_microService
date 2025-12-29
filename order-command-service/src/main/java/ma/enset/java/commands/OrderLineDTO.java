package ma.enset.java.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {
    private String productId;
    private int quantity;
    private double unitPrice;
    private double discount;
}