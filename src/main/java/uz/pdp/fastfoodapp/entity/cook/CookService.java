package uz.pdp.fastfoodapp.entity.cook;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.order.CustomOrderForCook;
import uz.pdp.fastfoodapp.entity.order.Order;
import uz.pdp.fastfoodapp.entity.order.OrderRepository;
import uz.pdp.fastfoodapp.entity.order.enums.OrderStatus;
import uz.pdp.fastfoodapp.entity.orderItem.CustomOrderItem;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CookService {
    @Autowired
    OrderRepository orderRepository;

    public HttpEntity<?> readyOrder(UUID id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse("Id not found", false));
        }
        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.READY);
        orderRepository.save(order);
        return ResponseEntity.status(200).body(new ApiResponse("Done", true));
    }

    public HttpEntity<?> receivedOrder(UUID id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse("Id not found", false));
        }
        Order order = optionalOrder.get();
        order.setStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
        return ResponseEntity.status(200).body(new ApiResponse("Done", true));
    }


    @SneakyThrows
    public HttpEntity<?> getAcceptedOrders(int page, int size, String search) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );

        Page<CustomOrderForCook> all = orderRepository.findAcceptedOrderForCook(
                pageable,
                search
        );
        Stream<CustomOrderForCook> customOrderForCookStream = all.get();
        Stream<String> orderItem = customOrderForCookStream.map(CustomOrderForCook::getOrderItems);
        System.out.println(orderItem);
        ObjectMapper objectMapper=new ObjectMapper();
        CustomOrderItem customOrderItem = objectMapper.readValue((JsonParser) orderItem, CustomOrderItem.class);
        System.out.println(customOrderItem);

        return ResponseEntity.ok(new ApiResponse("success", true, all));
    }

    public HttpEntity<?> getPreparedOrders(int page, int size, String search) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );

        Page<CustomOrderForCook> all = orderRepository.findPreparedOrderForCook(
                pageable,
                search
        );

        return ResponseEntity.ok(new ApiResponse("success", true, all));
    }
}
