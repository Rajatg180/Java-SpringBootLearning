package com.example.SpringBootEcommerceBackend.api.orders;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootEcommerceBackend.application.orders.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // this is record class in Java 16+ that will be used as the request body for creating an order. It contains the user id and a list of order items, where each item contains the product id and quantity.
    record CreateOrderRequest(Long userId,List<Item> items){
        record Item(Long productId, long quantity){}
    }

    record OrderResponse(Long id,String status,long totalAmountCents){
    }


    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest req){

        var order = orderService.createOrder(req.userId(), req.items().stream().map(
            i -> new OrderService.OrderRequestItem(i.productId(), i.quantity())
        ).toList());


        return new OrderResponse(order.getId(),order.getStatus().name(), order.getTotalAmountCents());

    }
    
}
