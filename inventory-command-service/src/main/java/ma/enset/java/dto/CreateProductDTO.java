package ma.enset.java.dto;

import lombok.Data;

@Data
public class CreateProductDTO {
    private String name;
    private double price;
    private int quantity;
    private String categoryId;
}
