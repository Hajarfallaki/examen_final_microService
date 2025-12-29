package ma.enset.java.controller;


import ma.enset.java.order.command.commands.*;
import ma.enset.java.order.command.dto.CreateOrderDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<String> createOrder(@RequestBody CreateOrderDTO dto) {
        String orderId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(
                orderId,
                dto.getCustomerId(),
                dto.getDeliveryAddress(),
                dto.getOrderLines()
        ));
    }

    @PutMapping("/{orderId}/activate")
    public CompletableFuture<String> activateOrder(@PathVariable String orderId) {
        return commandGateway.send(new ActivateOrderCommand(orderId));
    }

    @PutMapping("/{orderId}/cancel")
    public CompletableFuture<String> cancelOrder(@PathVariable String orderId) {
        return commandGateway.send(new CancelOrderCommand(orderId));
    }

    @PutMapping("/{orderId}/ship")
    public CompletableFuture<String> shipOrder(@PathVariable String orderId) {
        return commandGateway.send(new ShipOrderCommand(orderId));
    }

    @PutMapping("/{orderId}/deliver")
    public CompletableFuture<String> deliverOrder(@PathVariable String orderId) {
        return commandGateway.send(new DeliverOrderCommand(orderId));
    }
}
