package ma.enset.java.dto;

import lombok.Data;

@Data
public class OrderLineDTO {
    private String productId;
    private int quantity;
    private double unitPrice;
    private double discount;
}