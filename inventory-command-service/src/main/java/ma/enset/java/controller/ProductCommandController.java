package ma.enset.java.controller;

import ma.enset.java.inventory.command.commands.*;
import ma.enset.java.inventory.command.dto.CreateProductDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<String> createProduct(@RequestBody CreateProductDTO dto) {
        String productId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateProductCommand(
                productId,
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getCategoryId()
        ));
    }

    @PutMapping("/{productId}/stock")
    public CompletableFuture<String> updateStock(
            @PathVariable String productId,
            @RequestParam int quantity) {
        return commandGateway.send(new UpdateProductStockCommand(productId, quantity));
    }
}